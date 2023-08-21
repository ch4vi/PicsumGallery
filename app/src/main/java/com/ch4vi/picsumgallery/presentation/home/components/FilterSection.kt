package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    selectedOption: String? = null,
    options: List<String>,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String) -> Unit,
    onClear: () -> Unit
) {
    val value = selectedOption ?: stringResource(id = R.string.all)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(2f),
            expanded = isExpanded,
            onExpandedChange = onExpandedChange
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = value,
                onValueChange = {},
                label = { Text(stringResource(id = R.string.author)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onOptionSelected(selectionOption)
                            // expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = onClear
        ) {
            Text(text = stringResource(R.string.clear))
        }
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
fun FilterSectionPreview() {
    PicsumGalleryTheme {
        FilterSection(
            selectedOption = null,
            options = optionsPreview,
            isExpanded = true,
            onExpandedChange = {},
            onOptionSelected = {},
            onClear = {}
        )
    }
}

private val optionsPreview = listOf("foo", "bar", "baz")
