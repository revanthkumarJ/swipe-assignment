package com.revanth.swipe.feature.home

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.common.DataState
import com.revanth.swipe.core.common.utils.NotificationRepository
import com.revanth.swipe.core.data.repos.ConnectivityRepository
import com.revanth.swipe.core.data.repos.ProductLocalRepository
import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.database.enitities.UnsyncedProductEntity
import com.revanth.swipe.core.database.mappers.toProduct
import com.revanth.swipe.core.database.mappers.toProductEntity
import com.revanth.swipe.core.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


class HomeViewModel(
    private val context: Context,
    private val repo: ProductRepository,
    val networkStatus: ConnectivityRepository,
    val localRepo: ProductLocalRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _eventState = MutableSharedFlow<HomeEvent>()
    val eventState: SharedFlow<HomeEvent> = _eventState.asSharedFlow()

    init {
        loadProducts()
        observeNetwork()
    }

    val unsyncedProducts = localRepo.getAllUnsyncedProductsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

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

            HomeAction.OnSettingsClicked -> {
                viewModelScope.launch {
                    _eventState.emit(HomeEvent.NavigateToSettings)
                }
            }

            HomeAction.OnAddProductClicked -> {
                _state.update {
                    it.copy(
                        showAddBottomSheet = true,
                        addProductState = HomeUiState.AddProductState.Initial
                    )
                }
            }

            HomeAction.OnSyncClicked -> {
                _state.update {
                    it.copy(showSyncBottomSheet = true)
                }
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

            HomeAction.DismissSyncBottomSheet -> {
                _state.update {
                    it.copy(showSyncBottomSheet = false)
                }
            }

            is HomeAction.OnImageSelected -> {
                _state.update {
                    it.copy(
                        selectedImage = action.uri,
                    )
                }
            }

            is HomeAction.OnProductClicked -> {
                sendEvent(HomeEvent.NavigateToDetailsScreen(action.product))
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
                        localRepo.updateProducts(products.map { it.toProductEntity() })
                    }

                    is DataState.Empty -> {
                        _state.update {
                            it.copy(
                                homeState = HomeUiState.HomeState.Empty,
                                showPullToRefreshLoader = false
                            )
                        }
                    }

                    is DataState.Error -> fetchFromRoom()
                }
            }
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkStatus.isConnected.collect { connected ->
                _state.update {
                    it.copy(networkAvailable = connected)
                }
                if (connected) {
                    syncProducts()
                }
            }
        }
    }

    fun sendNotification(
        title: String
    ) {
        try {
            notificationRepository.sendNotification(
                title = "New Product Added",
                message = "$title has been added to your list!"
            )
        }catch (e: Exception){ }

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

        _state.update {
            it.copy(
                productTypeError = productTypeError,
                productNameError = productNameError,
                priceError = priceError,
                taxError = taxError
            )
        }

        if (productTypeError == null && productNameError == null && priceError == null && taxError == null) {
            if (state.value.networkAvailable) {
                addProduct()
            } else {
                viewModelScope.launch {
                    _state.update {
                        it.copy(addProductState = HomeUiState.AddProductState.NoInternet)
                    }
                    localRepo.addUnsyncedProduct(
                        UnsyncedProductEntity(
                            productName = state.value.productName,
                            productType = state.value.productType,
                            price = state.value.price,
                            tax = state.value.tax
                        )
                    )
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
            }

        }
    }

    private fun addProduct() {
        viewModelScope.launch {
            val productType = state.value.productType
            val productName = state.value.productName
            val price = state.value.price
            val tax = state.value.tax

            val productNameBody = productName.toRequestBody("text/plain".toMediaType())
            val productTypeBody = productType.toRequestBody("text/plain".toMediaType())
            val priceBody = price.toRequestBody("text/plain".toMediaType())
            val taxBody = tax.toRequestBody("text/plain".toMediaType())
            val imagePart = state.value.selectedImage?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val bytes = inputStream?.readBytes()

                val requestFile = bytes?.toRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(
                    name = "files",
                    filename = "product_${System.currentTimeMillis()}.jpg",
                    body = requestFile!!
                )
            }
            val res = repo.addProduct(
                productName = productNameBody,
                productType = productTypeBody,
                price = priceBody,
                tax = taxBody,
                image = imagePart
            )
            when (res) {

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
                    sendNotification(productName)
                    if (state.value.homeState is HomeUiState.HomeState.Success) {
                        val newlyAddedProduct = Product(
                            productName = productName,
                            productType = productType,
                            price = price.toDouble(),
                            tax = tax.toDouble(),
                            image = ""
                        )
                        val existingProducts =
                            (state.value.homeState as HomeUiState.HomeState.Success).allProducts
                        val updatedProducts = mutableListOf(newlyAddedProduct)
                        updatedProducts.addAll(existingProducts)
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

    private suspend fun fetchFromRoom() {
        try {
            val localProducts = localRepo.getAllProductsFlow().first()
            val mapped = localProducts.map { it.toProduct() }

            if (mapped.isEmpty()) {
                _state.update {
                    it.copy(
                        homeState = HomeUiState.HomeState.Empty,
                        showPullToRefreshLoader = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        homeState = HomeUiState.HomeState.Success(
                            allProducts = mapped,
                            filteredProducts = mapped
                        ),
                        showPullToRefreshLoader = false
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    homeState = HomeUiState.HomeState.Empty,
                    showPullToRefreshLoader = false
                )
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

    private fun syncProducts() {
        viewModelScope.launch {

            if (!state.value.networkAvailable) return@launch

            val unsynced = localRepo.getAllUnsyncedProductsFlow().first()

            unsynced.forEach { item ->

                val productNameBody = item.productName
                    .toRequestBody("text/plain".toMediaType())

                val productTypeBody = item.productType
                    .toRequestBody("text/plain".toMediaType())

                val priceBody = item.price
                    .toRequestBody("text/plain".toMediaType())

                val taxBody = item.tax
                    .toRequestBody("text/plain".toMediaType())

                when (repo.addProduct(
                    productName = productNameBody,
                    productType = productTypeBody,
                    price = priceBody,
                    tax = taxBody,
                    image = null
                )) {

                    is DataState.Success -> {
                        sendNotification(item.productName)
                        localRepo.deleteUnsyncedProduct(item)
                    }

                    is DataState.Error -> {
                        return@launch
                    }

                    else -> {}
                }
            }
        }
    }

}

sealed interface HomeEvent {
    data object NavigateToSettings : HomeEvent
    data class NavigateToDetailsScreen(val product: Product) : HomeEvent
}

data class HomeUiState(
    val dialogState: DialogState = DialogState.None,
    val homeState: HomeState = HomeState.Loading,
    val addProductState: AddProductState = AddProductState.Initial,
    val showNetworkNotConnectedSnackbar: Boolean = false,
    val searchQuery: String = "",
    val showPullToRefreshLoader: Boolean = false,
    val showAddBottomSheet: Boolean = false,
    val networkAvailable: Boolean = false,
    val showSyncBottomSheet: Boolean = false,


    //Fields for Add Product
    val productType: String = "",
    val productName: String = "",
    val price: String = "",
    val tax: String = "",
    val productTypeError: String? = null,
    val productNameError: String? = null,
    val priceError: String? = null,
    val taxError: String? = null,
    val selectedImage: Uri? = null,
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
        data object Initial : AddProductState
        data object Loading : AddProductState
        data object Success : AddProductState
        data object NoInternet : AddProductState
        data class Error(val message: String) : AddProductState
    }

    val showAddProductSubmitButton: Boolean
        get() = productNameError == null && priceError == null && taxError == null && productTypeError == null
}

sealed interface HomeAction {

    data class SearchQueryChanged(val value: String) : HomeAction

    data object RefreshProducts : HomeAction

    data object OnSettingsClicked : HomeAction

    data object OnAddProductClicked : HomeAction

    data object OnSyncClicked : HomeAction

    data object HideDialog : HomeAction

    data object DismissAddBottomSheet : HomeAction

    data class OnProductTypeIndexChanged(val productType: String) : HomeAction

    data class OnProductNameChanged(val name: String) : HomeAction

    data class OnPriceChanged(val price: String) : HomeAction

    data class OnTaxChanged(val tax: String) : HomeAction

    data object OnAddProductSubmitClick : HomeAction

    data object AddProductTryAgain : HomeAction

    data object DismissSyncBottomSheet : HomeAction

    data class OnImageSelected(val uri: Uri?) : HomeAction

    data class OnProductClicked(val product: Product) : HomeAction
}
