package eu.fitped.priscilla.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.model.LanguageGetDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelect(
    modifier: Modifier = Modifier,
    languages: List<LanguageGetDto>,
    currentLanguageId: Long = 0,
    onLanguageItemClick: (LanguageGetDto) -> Unit = {}
) {
    val currentLanguage = languages.find { it.id == currentLanguageId } ?: languages.first()
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(currentLanguage) }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText.getFormattedName(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                languages.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.getFormattedName()) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onLanguageItemClick(item)
                        }
                    )
                }
            }
        }
    }}

@Preview
@Composable
fun LanguageSelectPreview() {
    LanguageSelect(languages = listOf(
        LanguageGetDto(0, "slovensky", "SK"),
        LanguageGetDto(1, "english", "EN"),
        LanguageGetDto(2, "český", "CS")
    ))
}