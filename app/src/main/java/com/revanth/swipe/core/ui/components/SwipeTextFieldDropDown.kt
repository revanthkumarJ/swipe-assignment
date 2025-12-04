package com.revanth.swipe.core.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revanth.swipe.core.ui.theme.SwipeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeTextFieldDropdown(
    enabled: Boolean = true,
    value: String,
    onValueChanged: (String) -> Unit,
    onOptionSelected: (Int, String) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .fillMaxWidth(),
    label: String? = null,
    readOnly: Boolean = true,
    errorMessage: String? = null,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded && enabled,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = enabled,
            modifier = modifier
                .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = enabled)
                .clickable(enabled = readOnly && enabled) { isExpanded = true },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            label = {
                label?.let {
                    Text(
                        text = it,
                    )
                }
            },
            isError = !errorMessage.isNullOrEmpty(),
            supportingText = {
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            maxLines = 1,
            readOnly = readOnly
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        ) {
            options.forEachIndexed { index, item ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        isExpanded = false
                        onOptionSelected(index, item)
                        onValueChanged(item)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                if (index < options.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun SwipeTextFieldDropdownPreview() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var selectedValue by remember { mutableStateOf("") }

    SwipeTheme {
        SwipeTextFieldDropdown(
            value = selectedValue,
            onValueChanged = { selectedValue = it },
            onOptionSelected = { _, option -> selectedValue = option },
            options = options,
            label = "Select Option",
        )
    }
}