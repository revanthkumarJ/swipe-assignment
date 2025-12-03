package com.revanth.swipe.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.common.DataState
import com.revanth.swipe.core.data.repos.ConnectivityRepository
import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class HomeViewModel(
    private val repo: ProductRepository,
    val networkStatus: ConnectivityRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _eventState = MutableSharedFlow<HomeEvent>()
    val eventState: SharedFlow<HomeEvent> = _eventState.asSharedFlow()

    init {
        loadProducts()
        observeNetwork()
    }

    fun onAction(action: HomeAction) {
        when (action) {

            is HomeAction.SearchQueryChanged -> {
                _state.update { it.copy(searchQuery = action.value) }
                applyFilters()
            }

            HomeAction.RefreshProducts -> {
                _state.update { it.copy(showPullToRefreshLoader = true) }
                loadProducts()
            }

            HomeAction.OnFilterClicked -> {
                sendEvent(HomeEvent.OpenFilterDrawer)
            }

            HomeAction.OnAddProductClicked -> {
                _state.update { it.copy(
                    showAddBottomSheet = true,
                    addProductState = HomeUiState.AddProductState.Initial
                ) }
            }

            HomeAction.OnSyncClicked -> {
                sendEvent(HomeEvent.NavigateToSyncScreen)
            }

            HomeAction.HideDialog -> {
                _state.update {
                    it.copy(dialogState = HomeUiState.DialogState.None)
                }
            }

            HomeAction.DismissAddBottomSheet -> {
                _state.update {
                    it.copy(showAddBottomSheet = false)
                }
            }

            is HomeAction.OnPriceChanged -> {
                _state.update {
                    it.copy(
                        price = action.price,
                        priceError = null
                    )
                }
            }

            is HomeAction.OnProductNameChanged -> {
                _state.update {
                    it.copy(
                        productName = action.name,
                        productNameError = null
                    )
                }
            }
            is HomeAction.OnProductTypeIndexChanged -> {
                _state.update {
                    it.copy(
                        productType = action.productType,
                        productTypeError = null
                    )
                }
            }
            is HomeAction.OnTaxChanged -> {
                _state.update {
                    it.copy(tax = action.tax)
                }
            }

            HomeAction.OnAddProductSubmitClick -> verifyAddTextFields()

            HomeAction.AddProductTryAgain -> {
                _state.update {
                    it.copy(addProductState = HomeUiState.AddProductState.Initial)
                }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repo.getProducts().collect { dataState ->

                when (dataState) {
                    is DataState.Loading -> {
                        _state.update { it.copy(homeState = HomeUiState.HomeState.Loading) }
                    }

                    is DataState.Success -> {
                        val products = dataState.data
                        _state.update {
                            it.copy(
                                homeState = HomeUiState.HomeState.Success(
                                    allProducts = products,
                                    filteredProducts = products
                                ),
                                showPullToRefreshLoader = false
                            )
                        }
                    }

                    is DataState.Empty -> {
                        _state.update {
                            it.copy(
                                homeState = HomeUiState.HomeState.Empty,
                                showPullToRefreshLoader = false
                            )
                        }
                    }

                    is DataState.Error -> {
                        _state.update {
                            it.copy(
                                dialogState = HomeUiState.DialogState.Show(
                                    dataState.message
                                ),
                                showPullToRefreshLoader = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeNetwork(){
        viewModelScope.launch {
            networkStatus.isConnected.collect {
                _state.update {
                    it.copy(networkAvailable = it.networkAvailable)
                }
            }
        }
    }

    private fun verifyAddTextFields() {
        val currentState = state.value

        val productTypeError = if (currentState.productType.isBlank()) {
            "Please select a product type"
        } else null

        val productNameError = if (currentState.productName.isBlank()) {
            "Product Name should not be empty"
        } else null

        val priceError = when {
            currentState.price.isBlank() -> "Price should not be empty"
            currentState.price.toDoubleOrNull() == null -> "Price must be a valid number"
            currentState.price.toDouble() < 0 -> "Price cannot be negative"
            else -> null
        }

        val taxError = when {
            currentState.tax.isBlank() -> "Tax should not be empty"
            currentState.tax.toDoubleOrNull() == null -> "Tax must be a valid number"
            currentState.tax.toDouble() < 0 -> "Tax cannot be negative"
            else -> null
        }

        _state.update {it.copy(
            productTypeError = productTypeError,
            productNameError = productNameError,
            priceError = priceError,
            taxError = taxError
        )}

        if(productTypeError==null && productNameError==null && priceError==null && taxError==null){
            if(state.value.networkAvailable){
                addProduct()
            }
            else{
                viewModelScope.launch {
                    _state.update {
                        it.copy(addProductState = HomeUiState.AddProductState.NoInternet)
                    }
                    delay(3000)
                    _state.update {
                        it.copy(
                            showAddBottomSheet = false,
                            productType = "",
                            productName = "",
                            price = "",
                            tax = "",
                            productTypeError = null,
                            productNameError = null,
                            priceError = null,
                            taxError = null
                        )
                    }
                }
            }

        }
    }

    private fun addProduct(){
        viewModelScope.launch {
            val productType = state.value.productType
            val productName = state.value.productName
            val price = state.value.price
            val tax = state.value.tax

            val productNameBody = productName.toRequestBody("text/plain".toMediaType())
            val productTypeBody = productType.toRequestBody("text/plain".toMediaType())
            val priceBody = price.toRequestBody("text/plain".toMediaType())
            val taxBody = tax.toRequestBody("text/plain".toMediaType())

            val res= repo.addProduct(
                productName=productNameBody,
                productType=productTypeBody,
                price=priceBody,
                tax=taxBody,
                image = null
            )
            when(res){

                is DataState.Error -> {
                    _state.update {
                        it.copy(addProductState = HomeUiState.AddProductState.Error(res.message))
                    }
                }

                is DataState.Loading -> {
                    _state.update {
                        it.copy(addProductState = HomeUiState.AddProductState.Loading)
                    }
                }

                is DataState.Success -> {
                    _state.update {
                        it.copy(addProductState = HomeUiState.AddProductState.Success)
                    }
                    if(state.value.homeState is HomeUiState.HomeState.Success){
                        val newlyAddedProduct= Product(
                            productName = productName,
                            productType = productType,
                            price = price.toDouble(),
                            tax = tax.toDouble(),
                            image =""
                        )
                        val existingProducts= (state.value.homeState as HomeUiState.HomeState.Success).allProducts
                        val updatedProducts= existingProducts.toMutableList().apply {
                            addFirst(newlyAddedProduct)
                        }
                        _state.update {
                            it.copy(
                                homeState = HomeUiState.HomeState.Success(
                                    allProducts = updatedProducts,
                                    filteredProducts = updatedProducts
                                )
                            )
                        }
                    }
                    delay(2000)
                    _state.update {
                        it.copy(
                            showAddBottomSheet = false,
                            productType = "",
                            productName = "",
                            price = "",
                            tax = "",
                            productTypeError = null,
                            productNameError = null,
                            priceError = null,
                            taxError = null
                        )
                    }
                }

                else -> {}
            }
        }
    }


    private fun applyFilters() {
        val stateValue = _state.value.homeState
        if (stateValue !is HomeUiState.HomeState.Success) return

        val query = _state.value.searchQuery.trim()

        val filtered = stateValue.allProducts.filter { product ->
            product.productName.contains(query, ignoreCase = true)
        }

        _state.update {
            it.copy(
                homeState = stateValue.copy(filteredProducts = filtered)
            )
        }
    }

    private fun sendEvent(event: HomeEvent) {
        viewModelScope.launch {
            _eventState.emit(event)
        }
    }
}

sealed interface HomeEvent {
    data object NavigateToAddProduct : HomeEvent
    data object NavigateToSyncScreen : HomeEvent
    data object OpenFilterDrawer : HomeEvent
}

data class HomeUiState(
    val dialogState: DialogState = DialogState.None,
    val homeState: HomeState = HomeState.Loading,
    val addProductState: AddProductState = AddProductState.Initial,
    val showNetworkNotConnectedSnackbar: Boolean = false,
    val searchQuery: String = "",
    val showPullToRefreshLoader: Boolean = false,
    val showAddBottomSheet: Boolean = false,
    val networkAvailable:Boolean = false,

    //Fields for Add Product
    val productType :String="",
    val productName:String="",
    val price:String="",
    val tax:String="",
    val productTypeError:String?=null,
    val productNameError:String?=null,
    val priceError:String?=null,
    val taxError:String?=null,
) {
    sealed interface DialogState {
        object None : DialogState
        data class Show(val message: String) : DialogState
    }

    sealed interface HomeState {
        data object Loading : HomeState
        data class Success(
            val allProducts: List<Product>,
            val filteredProducts: List<Product>
        ) : HomeState

        data object Empty : HomeState
    }

    sealed interface AddProductState {
        data object Initial: AddProductState
        data object Loading : AddProductState
        data object Success : AddProductState
        data object NoInternet: AddProductState
        data class Error(val message: String) : AddProductState
    }

    val showAddProductSubmitButton:Boolean
        get() = productNameError==null && priceError==null && taxError==null && productTypeError==null
}

sealed interface HomeAction {

    data class SearchQueryChanged(val value: String) : HomeAction

    data object RefreshProducts : HomeAction

    data object OnFilterClicked : HomeAction

    data object OnAddProductClicked : HomeAction

    data object OnSyncClicked : HomeAction

    data object HideDialog : HomeAction

    data object DismissAddBottomSheet : HomeAction

    data class OnProductTypeIndexChanged(val productType: String) : HomeAction

    data class OnProductNameChanged(val name:String) : HomeAction

    data class OnPriceChanged(val price:String) : HomeAction

    data class OnTaxChanged(val tax:String) : HomeAction

    data object OnAddProductSubmitClick: HomeAction

    data object AddProductTryAgain: HomeAction
}
