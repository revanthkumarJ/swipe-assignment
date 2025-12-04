package com.revanth.swipe.feature.home

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.revanth.swipe.core.models.Product
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import com.revanth.swipe.R
import com.revanth.swipe.core.ui.components.SwipeLoader
import com.revanth.swipe.core.ui.components.SwipeLottieAnimation
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.feature.home.components.AddProductBottomSheet
import com.revanth.swipe.feature.home.components.AskNotificationPermission
import com.revanth.swipe.feature.home.components.EmptyContent
import com.revanth.swipe.feature.home.components.ProductCard
import com.revanth.swipe.feature.home.components.SyncBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToSettings:()->Unit,
    navigateToDetailsScreen:(product:Product)-> Unit
) {
    val state by viewModel.state.collectAsState()

    val unsyncedList by viewModel.unsyncedProducts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventState.collectLatest { event ->
            when(event){
                is HomeEvent.NavigateToSettings -> navigateToSettings()
                is HomeEvent.NavigateToDetailsScreen -> navigateToDetailsScreen(event.product)
            }
        }
    }

    AskNotificationPermission()

    HomeScreenDialogs(
        dialogState = state.dialogState,
        onAction = viewModel::onAction
    )

    HomeScreenContent(
        state = state,
        onAction = viewModel::onAction
    )

    AddProductBottomSheet(
        state = state,
        onAction = viewModel::onAction
    )

    SyncBottomSheet(
        networkAvailable = state.networkAvailable,
        size=unsyncedList.size,
        showSyncBottomSheet = state.showSyncBottomSheet,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    val pullState = rememberPullToRefreshState()

    SwipeScaffold(
        topBarTitle = "Swipe",
        showNavigationIcon = false,
        brandIcon = if (
            isSystemInDarkTheme()
        ) {
            null
        } else {
            R.drawable.swipe
        },
        actions = {
            IconButton(onClick = { onAction(HomeAction.OnAddProductClicked) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
            IconButton(onClick = { onAction(HomeAction.OnSyncClicked) }) {
                Icon(Icons.Default.Sync, contentDescription = "Sync")
            }
            IconButton(onClick = { onAction(HomeAction.OnSettingsClicked) }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    ) {

        PullToRefreshBox(
            state = pullState,
            isRefreshing = state.showPullToRefreshLoader,
            onRefresh = { onAction(HomeAction.RefreshProducts) }
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                if (state.homeState is HomeUiState.HomeState.Success) {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { onAction(HomeAction.SearchQueryChanged(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        placeholder = { Text("Search products...") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when (val home = state.homeState) {

                        is HomeUiState.HomeState.Loading -> SwipeLoader(Modifier.fillMaxSize(),250.dp)

                        is HomeUiState.HomeState.Empty -> EmptyContent(text = "No Products Available")

                        is HomeUiState.HomeState.Success -> {
                            if (home.filteredProducts.isEmpty()) {
                                EmptyContent("No Products Found for the search query")
                            } else {
                                ProductList(
                                    onClick = {
                                        onAction(HomeAction.OnProductClicked(it))
                                    },
                                    products = home.filteredProducts
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenDialogs(
    dialogState: HomeUiState.DialogState,
    onAction: (HomeAction) -> Unit
) {

    if (dialogState is HomeUiState.DialogState.Show) {
        val message = dialogState.message
        AlertDialog(
            onDismissRequest = { onAction(HomeAction.HideDialog) },
            title = { Text("Error") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { onAction(HomeAction.HideDialog) }) {
                    Text("OK")
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductList(
    products: List<Product>,
    onClick:(product:Product)-> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                modifier = Modifier.clickable{
                    onClick(product)
                }
            )
        }
    }
}

