package com.revanth.swipe.feature.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.revanth.swipe.core.ui.components.SwipeBottomSheet
import com.revanth.swipe.core.ui.components.SwipeButton
import com.revanth.swipe.core.ui.components.SwipeLoader
import com.revanth.swipe.core.ui.components.SwipeOutlinedTextField
import com.revanth.swipe.core.ui.components.SwipeTextFieldDropdown
import com.revanth.swipe.core.ui.theme.SwipeTheme
import com.revanth.swipe.feature.home.HomeAction
import com.revanth.swipe.feature.home.HomeUiState

@Composable
fun AddProductBottomSheet(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        onAction(HomeAction.OnImageSelected(uri))
    }

    SwipeBottomSheet(
        showBottomSheet = state.showAddBottomSheet,
        onDismiss = {
            onAction(HomeAction.DismissAddBottomSheet)
        },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Add Product",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(16.dp))

            when (state.addProductState) {

                HomeUiState.AddProductState.Initial -> {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .clickable { imagePicker.launch("image/*") }
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {

                        if (state.selectedImage == null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(state.selectedImage),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.extraLarge)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SwipeTextFieldDropdown(
                        value = state.productType,
                        onValueChanged = { onAction(HomeAction.OnProductTypeIndexChanged(it)) },
                        options = listOf("Product", "Service"),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    )
                }

                HomeUiState.AddProductState.Loading -> {
                    SwipeLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                        150.dp
                    )
                }

                HomeUiState.AddProductState.NoInternet -> {
                    NoInternetComponent(
                        text = "No Internet Available.\n Product Added to Local Will be synced once network is available",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    )
                }

                HomeUiState.AddProductState.Success -> {
                    SuccessComponent(
                        text = "Product Added Successfully",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AddProductBottomSheetPreview() {
    SwipeTheme {
        AddProductBottomSheet(
            state = HomeUiState(
                showAddBottomSheet = true
            ),
            onAction = {}
        )
    }
}
