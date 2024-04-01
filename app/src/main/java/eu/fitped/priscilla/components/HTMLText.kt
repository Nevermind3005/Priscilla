package eu.fitped.priscilla.components

import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import eu.fitped.priscilla.util.GlideImageGetter

@Composable
fun HTMLText(
    modifier: Modifier = Modifier,
    html: String
) {
    val contentColor = LocalContentColor.current
    AndroidView(
        factory = { context ->
            val textView = TextView(context).apply {
                setTextColor(contentColor.toArgb())
            }
            val imageGetter = GlideImageGetter(textView)
            val htmlWithLineBreaks = html.replace("\n", "<br/>")
            val spannedHtml = HtmlCompat.fromHtml(htmlWithLineBreaks, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)
            textView.text = spannedHtml
            textView
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun HTMLTextWithEditText(
    modifier: Modifier = Modifier,
    html: String,
    editTextValue: String,
    onEditTextValueChanged: (String) -> Unit
) {
    val contentColor = LocalContentColor.current
    AndroidView(
        factory = { context ->
            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
            }

            val textView = TextView(context).apply {
                setTextColor(contentColor.toArgb())
            }
            val imageGetter = GlideImageGetter(textView)

            val placeholder = "§§_§§"
            val placeholderIndex = html.indexOf(placeholder)
            val modifiedHtml = html.replace(placeholder, "")

            val beforePlaceholder = html.substring(0, placeholderIndex)
            val afterPlaceholder = html.substring(placeholderIndex + placeholder.length)

            val spannedBeforePlaceholder = HtmlCompat.fromHtml(beforePlaceholder, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)
            val spannedAfterPlaceholder = HtmlCompat.fromHtml(afterPlaceholder, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)

            textView.text = spannedBeforePlaceholder

            val editText = EditText(context).apply {
                setText(editTextValue)
                addTextChangedListener { editable ->
                    onEditTextValueChanged(editable.toString())
                }
            }

            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 16.dp.value.toInt()
            editText.layoutParams = layoutParams

            layout.addView(textView)
            layout.addView(editText)
            layout.addView(TextView(context).apply {
                text = spannedAfterPlaceholder
                setTextColor(contentColor.toArgb())
            })

            layout
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}




@Preview(showBackground = true)
@Composable
fun HTMLTextPreview() {
    HTMLText(html = "<p>Hello <em>there</em> and <em>more</em></p>")
}
