package eu.fitped.priscilla.components

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewWithInput(
    modifier: Modifier = Modifier,
    html: String
) {
    val backgroundColorARGB = MaterialTheme.colorScheme.background.toArgb()
    val backgroundColor = "rgba(${MaterialTheme.colorScheme.background.red * 255}," +
            "${MaterialTheme.colorScheme.background.green * 255}," +
            "${MaterialTheme.colorScheme.background.blue * 255}, " +
            "${MaterialTheme.colorScheme.background.alpha})"
    val textColor = "rgba(${MaterialTheme.colorScheme.onSurface.red * 255}," +
            "${MaterialTheme.colorScheme.onSurface.green * 255}," +
            "${MaterialTheme.colorScheme.onSurface.blue * 255}, " +
            "${MaterialTheme.colorScheme.onSurface.alpha})"

    AndroidView(
        modifier = modifier
            .fillMaxWidth(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                setBackgroundColor(backgroundColorARGB)

                settings.apply {
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }

                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
//                addJavascriptInterface()
                evaluateJavascript("enable();", null);
                loadData(
                    handleHtmlTemplate(html, backgroundColor, textColor),
                    "text/html",
                    "UTF-8"
                )
            }
        }
    )
}

//const getAnswers = () => {
//    const inputs = document.getElementsByClassName('answer');
//    const output = [...inputs].map((i) => i.value);
//    return output;
//};

@Composable
fun HTMLText(
    modifier: Modifier = Modifier,
    html: String
) {
    WebViewWithInput(
        html = html.trimIndent(),
        modifier = modifier
    )
}


@Composable
fun HTMLTextWithEditText(
    modifier: Modifier = Modifier,
    html: String,
    editTextValue: String,
    onEditTextValueChanged: (String) -> Unit
) {
    WebViewWithInput(html = html.trimIndent())
}

private fun handleHtmlTemplate(
    html: String,
    backgroundColor: String,
    textColor: String,
): String {
    html.trimIndent()
    var newHtml = "<!DOCTYPE html><html><head><style>*{ background-color: ${backgroundColor}; color:${textColor}; }body{height:100%;word-wrap:break-word;}img{max-width: 100%;}</style></head><body>"
    newHtml += html

    while (newHtml.contains("§§_§§")) {
        newHtml = newHtml.replaceFirst("§§_§§", "<input type=\"text\" class=\"answer\" size=\"1\"/>")
    }
    newHtml += "</body></html>"
    Log.i("HandleHtmlTemplate", newHtml)
    return newHtml
}

interface IGetData {
    fun getData(): List<String>
}
