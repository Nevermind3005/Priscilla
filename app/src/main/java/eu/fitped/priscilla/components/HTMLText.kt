package eu.fitped.priscilla.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.HtmlCompat
import com.aghajari.compose.text.asAnnotatedString

// TODO add support for images
@Composable
fun HTMLText(
    modifier: Modifier = Modifier,
    html: String
) {
    val spannedHtml = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val annotatedHtmlString = spannedHtml.asAnnotatedString().annotatedString
    Text(
        modifier = modifier,
        text = annotatedHtmlString,
    )
}

@Preview(showBackground = true)
@Composable
fun HTMLTextPreview() {
    HTMLText(html = "<p>Hello <em>there</em> and <em>more</em></p>")
}