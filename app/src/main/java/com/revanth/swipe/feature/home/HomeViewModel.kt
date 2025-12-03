package com.revanth.swipe.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.common.DataState
import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _eventState = MutableSharedFlow<HomeEvent>()
    val eventState: SharedFlow<HomeEvent> = _eventState.asSharedFlow()

    init {
        loadProducts()
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
                sendEvent(HomeEvent.NavigateToAddProduct)
            }

            HomeAction.OnSyncClicked -> {
                sendEvent(HomeEvent.NavigateToSyncScreen)
            }

            HomeAction.HideDialog -> {
                _state.update {
                    it.copy(dialogState = HomeUiState.DialogState.None)
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
                        delay(500)
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
    val showNetworkNotConnectedSnackbar: Boolean = false,
    val searchQuery: String = "",
    val showPullToRefreshLoader:Boolean=false,
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
}

sealed interface HomeAction {

    data class SearchQueryChanged(val value: String) : HomeAction

    data object RefreshProducts : HomeAction

    data object OnFilterClicked : HomeAction

    data object OnAddProductClicked : HomeAction

    data object OnSyncClicked : HomeAction

    data object HideDialog : HomeAction
}
