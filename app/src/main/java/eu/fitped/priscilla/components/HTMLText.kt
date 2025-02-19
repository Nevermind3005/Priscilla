package eu.fitped.priscilla.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView


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

                setPadding(0, 0, 0, 0);

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
    val processedHtml = html.trimIndent().replace("§§_§§", "<input type=\"text\" style='fit-content' class=\"answer\" size=\"1\"/>")

    // Script to get answers from web view
    val answerScript = """
        const getAnswers = () => {
            const inputs = document.getElementsByClassName('answer');
            const output = [...inputs].map((i) => i.value);
            return output;
        }
    """.trimIndent()

    val baseStyle = """
        * {
            max-width: 100% !important;
            box-sizing: border-box !important;
            background-color:${backgroundColor} !important; 
            color:${textColor} !important; 
            -webkit-user-select: none !important;
        }
        body {
            height:100%;
        }
        img {
            max-width: 100%;
        }
        html, body {
            margin: 0 !important;
            padding: 0 !important;
            width: 100% !important;
            overflow-x: hidden !important;
        }
        pre {
            white-space: pre-wrap !important;
            margin: 0 !important;
            padding: 8px !important;
            overflow-x: hidden !important;
        }
    """.trimIndent()

    val htmlDoc = """
        <!DOCTYPE html>
        <html>
            <head>
                <style>
                    $baseStyle
                </style>
            </head>
            <body>
                $processedHtml
                <script>
                    $answerScript
                </script
            </body>
        </html>
    """.trimIndent()

    Log.i("HandleHtmlTemplate", htmlDoc)

    return htmlDoc
}