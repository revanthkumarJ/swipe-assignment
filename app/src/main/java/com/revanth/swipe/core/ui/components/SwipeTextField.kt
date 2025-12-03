package com.revanth.swipe.core.ui.components

import androidx.compose.ui.tooling.preview.Preview
import com.revanth.swipe.core.ui.theme.SwipeTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SwipeOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: String? = null,
    labelString: String? = null,
    supportingText: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next
    ),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        leadingIcon = leadingIcon,
        placeholder = {
            if (!placeholder.isNullOrEmpty()) {
                Text(placeholder)
            }
        },
        label = {
            if (!labelString.isNullOrEmpty()) {
                Text(labelString)
            }
        },
        isError = !errorMessage.isNullOrEmpty(),
        supportingText = {
            when {
                !errorMessage.isNullOrEmpty() -> Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )

                !supportingText.isNullOrEmpty() -> Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        keyboardOptions = keyboardOptions
    )
}


@Preview
@Composable
fun SwipeOutlinedTextFieldPreview() {
    SwipeTheme {
        var value by remember {
            mutableStateOf("")
        }
        SwipeOutlinedTextField(
            value = value,
            onValueChange = {
                value = it
            },
            leadingIcon = null,
            placeholder = "Company Code",
            modifier = Modifier,
            labelString = "Enter Company Name",
            supportingText = null,
            errorMessage = "Supporting Text"
        )
    }
}