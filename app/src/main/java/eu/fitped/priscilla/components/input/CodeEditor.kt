package eu.fitped.priscilla.components.input

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.langs.java.JavaLanguage

enum class CodeLanguage {
    LANG_JAVA
}

@Composable
fun CodeEditorCmp(
    modifier: Modifier = Modifier,
    initialCode: String = "",
    editorLanguage: CodeLanguage,
    onTextChanged: (String) -> Unit = {}
) {
    var editor by remember { mutableStateOf<CodeEditor?>(null) }

    val initialCodeRef = remember(initialCode) { initialCode }

    var isInitialized by remember { mutableStateOf(false) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            LinearLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                val codeEditor = makeCodeEditor(context, editorLanguage)
                codeEditor.setText(initialCodeRef)
                isInitialized = true

                addView(codeEditor, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                ))

                editor = codeEditor
            }
        },
        update = { view ->
            if (!isInitialized && editor != null) {
                editor?.setText(initialCodeRef)
                isInitialized = true
            }
        },
        onRelease = {
            println("Release Sora")
            editor?.release()
        }
    )

    DisposableEffect(editor) {
        val currentEditor = editor
        val contentListener = object : io.github.rosemoe.sora.text.ContentListener {
            override fun afterDelete(content: io.github.rosemoe.sora.text.Content, startLine: Int, startColumn: Int, endLine: Int, endColumn: Int, deletedContent: CharSequence) {
                onTextChanged(currentEditor?.text?.toString() ?: "")
            }

            override fun afterInsert(content: io.github.rosemoe.sora.text.Content, startLine: Int, startColumn: Int, endLine: Int, endColumn: Int, insertedContent: CharSequence) {
                onTextChanged(currentEditor?.text?.toString() ?: "")
            }

            override fun beforeReplace(content: io.github.rosemoe.sora.text.Content) {
            }
        }

        currentEditor?.text?.addContentListener(contentListener)

        onDispose {
            currentEditor?.text?.removeContentListener(contentListener)
        }
    }
}

private fun makeCodeEditor(context: Context, editorLanguage: CodeLanguage) : CodeEditor {
    return CodeEditor(context).apply {
        typefaceText = android.graphics.Typeface.MONOSPACE
        nonPrintablePaintingFlags =
            CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR or CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION
        inputType = android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        when(editorLanguage) {
            CodeLanguage.LANG_JAVA -> setEditorLanguage(JavaLanguage())
        }
    }
}