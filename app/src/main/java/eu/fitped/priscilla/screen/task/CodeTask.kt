package eu.fitped.priscilla.screen.task

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.CodeTaskEvalAlert
import eu.fitped.priscilla.components.CodeTaskInfoDialog
import eu.fitped.priscilla.components.input.CodeEditorCmp
import eu.fitped.priscilla.components.input.CodeLanguage
import eu.fitped.priscilla.model.GlobalsCodeTask
import eu.fitped.priscilla.model.TaskContentCode
import eu.fitped.priscilla.model.code.CodeFile
import eu.fitped.priscilla.model.code.LoadCodeResDto
import eu.fitped.priscilla.model.code.SaveProgramReqDto
import eu.fitped.priscilla.model.vpl.VPLGetResultResDto
import eu.fitped.priscilla.screen.Loading
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.util.DataStateTaskEval
import eu.fitped.priscilla.viewModel.CodeTaskViewModel

@Composable
fun CodeTask(
    modifier: Modifier = Modifier,
    taskId: String?,
    onClick: MutableState<() -> Unit>
) {
    val mapper = jacksonObjectMapper()

    val codeTaskViewModel: CodeTaskViewModel = hiltViewModel()
    val state by codeTaskViewModel.loadedCodeDataState.collectAsState()

    var showCodeInfoDialog by remember { mutableStateOf(false) }

    val codeEvalState by codeTaskViewModel.evalDataState.collectAsState()

    var codeEvalResult by remember { mutableStateOf("") }

    var currentlyActiveEditorIndex by remember { mutableIntStateOf(0) }

    val scaffoldScrollState = rememberScrollState()

    var code by remember { mutableStateOf(listOf<String>()) }

    val currentTask = codeTaskViewModel.getTask()!!

    val taskContent: TaskContentCode = mapper.readValue(currentTask.content)
    val globals: GlobalsCodeTask = mapper.readValue(currentTask.globals)


    LaunchedEffect(key1 = Unit) {
        codeTaskViewModel.loadSavedCode(taskId!!.toLong())
    }

    LaunchedEffect(codeEvalState) {
        if (codeEvalState is DataStateTaskEval.Success<*>) {
            codeEvalResult = (codeEvalState as DataStateTaskEval.Success<VPLGetResultResDto>).data.execution + (codeEvalState as DataStateTaskEval.Success<VPLGetResultResDto>).data.compilation
        }
    }

    onClick.value = {
        val codeList = mutableListOf<CodeFile>()
        for (i in 0..<globals.files.files.size) {
            codeList.add(CodeFile(globals.files.files[i], code[i]))
        }
        val req = SaveProgramReqDto("chapter", codeList, taskId!!.toLong())
        codeTaskViewModel.evaluate(req)
    }

    println(code)
    when (state) {
        is DataState.Loading -> Loading()
        is DataState.Success<*> -> {
            if (code.isEmpty()) {
                val data = (state as DataState.Success<LoadCodeResDto>).data
                val loadedCode: List<CodeFile>? = if (data.code != null) mapper.readValue(data.code) else null
                val codeList = mutableListOf<String>()
                if (loadedCode == null) {
                    for (i in 0..<globals.files.files.size) {
                        codeList.add(taskContent.files[i].rContent)
                    }
                } else {
                    for (i in 0..<globals.files.files.size) {
                        codeList.add(loadedCode[i].filecontent)
                    }
                }
                code = codeList;
            }

            Scaffold(
                topBar = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                            .horizontalScroll(scaffoldScrollState),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { showCodeInfoDialog = true }) {
                            Icon(Icons.Outlined.Info, contentDescription = "Info")
                        }
                        globals.files.files.forEachIndexed { index, file ->
                            Button(
                                modifier = Modifier.padding(4.dp),
                                onClick = { currentlyActiveEditorIndex = index }
                            ) {
                                Text(file)
                            }
                        }
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    if (showCodeInfoDialog) {
                        CodeTaskInfoDialog(
                            title = taskContent.title,
                            assignment = taskContent.assignment,
                            evalResult = codeEvalResult,
                            onDismissRequest = { showCodeInfoDialog = false }
                        )
                    }
                    CodeTaskEvalAlert(
                        state = codeEvalState,
                        resetState = {codeTaskViewModel.resetEvalState()}
                    )

                    println(code[currentlyActiveEditorIndex])
                    key(currentlyActiveEditorIndex) {
                        CodeEditorCmp(
                            initialCode = code[currentlyActiveEditorIndex],
                            onTextChanged = { newText ->
                                println(newText)
                                code = code.toMutableList().also { it[currentlyActiveEditorIndex] = newText }
                            },
                            editorLanguage = CodeLanguage.LANG_JAVA
                        )
                    }
                }
            }

        }
        is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
    }
}