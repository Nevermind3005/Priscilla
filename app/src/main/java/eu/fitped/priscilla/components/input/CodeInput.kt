package eu.fitped.priscilla.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeTheme
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString

@Composable
fun CodeInput(
    modifier: Modifier = Modifier,
    theme: CodeTheme = CodeThemeType.Default.theme(),
    code: String = "",
    language: CodeLang,
    ) {
    val parser = PrettifyParser()

    fun parse(code: String): AnnotatedString {
        return parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = language,
            code = code
        )
    }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(parse(code))) }

    Column {

        Row {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it.copy(
                        parse(it.text)
                    )
                }
            )
        }
    }
}