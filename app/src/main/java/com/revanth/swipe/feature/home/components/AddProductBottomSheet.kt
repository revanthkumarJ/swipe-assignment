package com.revanth.swipe.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.revanth.swipe.core.ui.components.SwipeBottomSheet
import com.revanth.swipe.core.ui.components.SwipeButton
import com.revanth.swipe.core.ui.components.SwipeLoader
import com.revanth.swipe.core.ui.components.SwipeOutlinedTextField
import com.revanth.swipe.core.ui.components.SwipeTextFieldDropdown
import com.revanth.swipe.feature.home.HomeAction
import com.revanth.swipe.feature.home.HomeUiState

@Composable
fun AddProductBottomSheet(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
){

    SwipeBottomSheet(
        showBottomSheet = state.showAddBottomSheet,
        onDismiss = {
            onAction(HomeAction.DismissAddBottomSheet)
        },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text="Add Product",
                style = MaterialTheme.typography.titleLarge,
                color= MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(16.dp))

            when(state.addProductState){

                HomeUiState.AddProductState.Initial -> {
                    SwipeTextFieldDropdown(
                        value = state.productType,
                        onValueChanged = { onAction(HomeAction.OnProductTypeIndexChanged(it)) },
                        options = listOf("Product","Service"),
                        onOptionSelected = { _, _ -> },
                        label = "Product Type",
                        errorMessage = state.productTypeError
                    )

                    SwipeOutlinedTextField(
                        value = state.productName,
                        onValueChange = { onAction(HomeAction.OnProductNameChanged(it)) },
                        placeholder = "Your Name",
                        labelString = "Product Name",
                        errorMessage = state.productNameError,
                    )

                    SwipeOutlinedTextField(
                        value = state.price,
                        onValueChange = { onAction(HomeAction.OnPriceChanged(it)) },
                        placeholder = "200",
                        labelString = "Price",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        errorMessage = state.priceError,
                    )

                    SwipeOutlinedTextField(
                        value = state.tax,
                        onValueChange = { onAction(HomeAction.OnTaxChanged(it)) },
                        placeholder = "35",
                        labelString = "Tax",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        errorMessage = state.taxError,
                    )

                    SwipeButton(
                        text = "Add Product",
                        onClick = {
                            onAction(HomeAction.OnAddProductSubmitClick)
                        },
                        enabled = state.showAddProductSubmitButton
                    )
                }

                is HomeUiState.AddProductState.Error -> {
                    FailureComponent(
                        text = state.addProductState.message,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                    )
                }

                HomeUiState.AddProductState.Loading -> {
                    SwipeLoader(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
                        150.dp
                    )
                }
                HomeUiState.AddProductState.NoInternet -> {
                    Text("No Internet , so it is added to sync later")
                }
                HomeUiState.AddProductState.Success -> {
                    SuccessComponent(
                        text = "Product Added Successfully",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                    )
                }
            }


        }
    }

}