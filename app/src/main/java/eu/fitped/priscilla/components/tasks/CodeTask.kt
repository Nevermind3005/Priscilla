package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import eu.fitped.priscilla.components.input.CodeEditorCmp
import eu.fitped.priscilla.components.input.CodeLanguage
import eu.fitped.priscilla.model.TaskContentCode

@Composable
fun CodeTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContentCode
) {
    var code by remember { mutableStateOf("") }
    println(code)
    Column {
        CodeEditorCmp(
            initialCode = taskContent.files.first().rContent,
            onTextChanged = { newText -> code = newText },
            editorLanguage = CodeLanguage.LANG_JAVA
        )
    }
}