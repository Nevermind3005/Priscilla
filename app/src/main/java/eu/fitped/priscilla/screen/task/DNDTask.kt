package eu.fitped.priscilla.screen.task

import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.HTMLTextWithDND
import eu.fitped.priscilla.components.TaskEvalAlert
import eu.fitped.priscilla.model.TaskContentDND
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.model.TaskType
import eu.fitped.priscilla.viewModel.TaskViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray

@Composable
fun DNDTask(
    modifier: Modifier = Modifier,
    taskId: String?,
    onClick: MutableState<() -> Unit>
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    val state by taskViewModel.dataState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var webView by remember { mutableStateOf<WebView?>(null) }

    val result = remember {
        mutableListOf<String>()
    }

    val mapper = jacksonObjectMapper()
    val taskContent: TaskContentDND = mapper.readValue(taskViewModel.getTask()!!.content)

    val startTime = System.currentTimeMillis() / 1000

    val evaluateJavaScript: (String) -> Unit = { script ->
        val currentTime = System.currentTimeMillis() / 1000
        coroutineScope.launch {
            webView?.evaluateJavascript(script) { value ->
                result.clear()
                val jsonArray = JSONArray(value)
                for (i in 0 until jsonArray.length()) {
                    result.add(jsonArray.getString(i))
                    Log.i("DND Task", jsonArray.getString(i))
                }
                val taskAnswer = TaskEvalDto(
                    activityType = "chapter",
                    taskId = taskId!!.toLong(),
                    taskTypeId = TaskType.DND.id,
                    timeLength = currentTime - startTime,
                    answerList = result
                )
                taskViewModel.evaluate(taskAnswer)
            }
        }
    }

    onClick.value = {
        evaluateJavaScript("getAnswers()")
    }
    TaskEvalAlert(
        state = state,
        resetState = {taskViewModel.resetState()}
    )
    Column(
        modifier = modifier
    ) {
        HTMLTextWithDND(
            html = taskContent.content,
            fakes = taskContent.fakes,
            onWebViewAvailable = {webView = it}
        )
    }
}