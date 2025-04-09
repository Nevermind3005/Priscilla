package eu.fitped.priscilla.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
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

data class HTMLColors(
    val backgroundColor: String,
    val textColor: String,
    val surfaceColor: String
)

@Composable
fun getHtmlColors(): HTMLColors {
    val backgroundColor = "rgba(${MaterialTheme.colorScheme.background.red * 255}," +
            "${MaterialTheme.colorScheme.background.green * 255}," +
            "${MaterialTheme.colorScheme.background.blue * 255}, " +
            "${MaterialTheme.colorScheme.background.alpha})"
    val textColor = "rgba(${MaterialTheme.colorScheme.onSurface.red * 255}," +
            "${MaterialTheme.colorScheme.onSurface.green * 255}," +
            "${MaterialTheme.colorScheme.onSurface.blue * 255}, " +
            "${MaterialTheme.colorScheme.onSurface.alpha})"
    val surfaceColor = "rgba(${MaterialTheme.colorScheme.primaryContainer.red * 255}," +
            "${MaterialTheme.colorScheme.primaryContainer.green * 255}," +
            "${MaterialTheme.colorScheme.primaryContainer.blue * 255}, " +
            "${MaterialTheme.colorScheme.primaryContainer.alpha})"
    return HTMLColors(
        backgroundColor = backgroundColor,
        textColor = textColor,
        surfaceColor = surfaceColor
    )
}

@SuppressLint("RememberReturnType", "SetJavaScriptEnabled")
@Composable
fun WebViewWithInput(
    modifier: Modifier = Modifier,
    html: String,
    onWebViewAvailable: (WebView) -> Unit
    ) {
    val backgroundColorARGB = MaterialTheme.colorScheme.background.toArgb()
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
                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(message: ConsoleMessage?): Boolean {
                        message?.let {
                            Log.d("WebViewConsole", "${it.message()} -- From line ${it.lineNumber()} of ${it.sourceId()}")
                        }
                        return super.onConsoleMessage(message)
                    }
                }
                // changed from loadData to this bc. load data didn't want to render html properly >:{
                loadDataWithBaseURL(
                    "file:///android_asset/",
                    html,
                    "text/html",
                    "UTF-8",
                    null
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
        html = handleHtmlTemplate(
            html.trimIndent(),
            getHtmlColors()
        ),
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
        html = handleHtmlTemplate(
            html.trimIndent(),
            getHtmlColors()
        ),
        modifier = modifier,
        onWebViewAvailable = onWebViewAvailable
    )
}

@Composable
fun HTMLTextWithDND(
    modifier: Modifier = Modifier,
    html: String,
    fakes: List<String>,
    onWebViewAvailable: (WebView) -> Unit
) {
    WebViewWithInput(
        html = handleHtmlTemplateDND(
            html.trimIndent(),
            fakes,
            getHtmlColors()
        ),
        onWebViewAvailable = onWebViewAvailable,
        modifier = modifier
    )
}

private fun handleHtmlTemplateDND(
    html: String,
    fakes: List<String>,
    htmlColors: HTMLColors,
): String {
    val processedHtml = html.trimIndent().replace("§§_§§", "<span class=\"blank\" data-filled=\"false\"></span>")

    val wordListHtml = fakes.joinToString("") {
        "<div class='word'>$it</div>"
    }

    val answerScript = """
        document.addEventListener("DOMContentLoaded", function() {
            const blanks = Array.from(document.querySelectorAll('.blank'));
            const answerList = document.getElementById('answerList');

            function handleWordClick(e) {
                const word = e.target;
                const firstEmptyBlank = blanks.find(b => b.dataset.filled === "false");
                if (firstEmptyBlank) {
                    firstEmptyBlank.textContent = word.textContent;
                    firstEmptyBlank.classList.add('filled');
                    firstEmptyBlank.dataset.filled = "true";
                    firstEmptyBlank.dataset.word = word.textContent;
                    word.remove();
                }
            }

            function handleBlankClick(e) {
                const blank = e.target;
                if (blank.dataset.filled === "true") {
                    const wordText = blank.dataset.word;
                    const newWord = document.createElement('div');
                    newWord.className = 'word';
                    newWord.textContent = wordText;
                    newWord.addEventListener('click', handleWordClick);
                    answerList.appendChild(newWord);
                    blank.textContent = '';
                    blank.classList.remove('filled');
                    blank.dataset.filled = "false";
                    delete blank.dataset.word;
                }
            }

            blanks.forEach(blank => blank.addEventListener('click', handleBlankClick));

            const wordElems = Array.from(document.getElementsByClassName('word'));
            wordElems.forEach(word => word.addEventListener('click', handleWordClick));

            window.getAnswers = function() {
                return blanks.map(b => b.dataset.filled === "true" ? b.dataset.word : "").filter(Boolean);
            };
        });
    """.trimIndent()

    val baseStyle = """
        * {
            max-width: 100% !important;
            box-sizing: border-box !important;
            background-color:${htmlColors.backgroundColor} !important; 
            color:${htmlColors.textColor} !important; 
            -webkit-user-select: none !important;
        }
        body {
            height:100%;
            font-family: sans-serif;
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
        .answers {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin: 16px 0;
        }
        .word {
            background-color: #cce5ff;
            border: 1px solid #3399ff;
            padding: 6px 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .blank {
            display: inline-block;
            min-width: 60px;
            border-bottom: 2px solid #999;
            padding: 2px 5px;
            margin: 0 4px;
            cursor: pointer;
        }
        .blank.filled {
            border-color: #3399ff;
            background-color: #e6f2ff;
        }
    """.trimIndent()

    val htmlDoc = """
        <!DOCTYPE html>
        <html>
            <head>
                <style>$baseStyle</style>
            </head>
            <body>
                <div class="answers" id="answerList">$wordListHtml</div>
                $processedHtml
                <script>$answerScript</script>
            </body>
        </html>
    """.trimIndent()

    Log.i("handleHtmlTemplateDND", htmlDoc)

    return htmlDoc
}

private fun handleHtmlTemplate(
    html: String,
    htmlColors: HTMLColors
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
            background-color:${htmlColors.backgroundColor} !important; 
            color:${htmlColors.textColor} !important; 
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