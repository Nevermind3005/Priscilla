package eu.fitped.priscilla.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import org.json.JSONArray

@SuppressLint("RememberReturnType", "SetJavaScriptEnabled")
@Composable
fun WebViewWithInput(
    modifier: Modifier = Modifier,
    html: String,
    onWebViewAvailable: (WebView) -> Unit
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
                loadData(
                    handleHtmlTemplate(html, backgroundColor, textColor),
                    "text/html",
                    "UTF-8"
                )
                onWebViewAvailable(this)
            }
        }
    )
}


@Composable
fun HTMLText(
    modifier: Modifier = Modifier,
    html: String
) {
    var webView by remember { mutableStateOf<WebView?>(null) }
    WebViewWithInput(
        html = html.trimIndent(),
        modifier = modifier,
        onWebViewAvailable = {
            webView = it
        },
    )
}


@Composable
fun HTMLTextWithEditText(
    modifier: Modifier = Modifier,
    html: String,
    onWebViewAvailable: (WebView) -> Unit
) {
    WebViewWithInput(
        html = html.trimIndent(),
        onWebViewAvailable = onWebViewAvailable
    )
}

private fun handleHtmlTemplate(
    html: String,
    backgroundColor: String,
    textColor: String,
): String {
    html.trimIndent()
    var newHtml = "<!DOCTYPE html><html><head><style>*{ background-color:${backgroundColor} !important; color:${textColor} !important; -webkit-user-select: none !important; }body{height:100%;word-wrap:break-word;}img{max-width: 100%;}</style></head><body>"
    newHtml += html

    while (newHtml.contains("§§_§§")) {
        newHtml = newHtml.replaceFirst("§§_§§", "<input type=\"text\" class=\"answer\" size=\"1\"/>")
    }
    newHtml += "<script>const getAnswers = () => {\n" +
            "    const inputs = document.getElementsByClassName('answer');\n" +
            "    const output = [...inputs].map((i) => i.value);\n" +
            "    return output;\n" +
            "};</script></body></html>"
    Log.i("HandleHtmlTemplate", newHtml)
    return newHtml
}