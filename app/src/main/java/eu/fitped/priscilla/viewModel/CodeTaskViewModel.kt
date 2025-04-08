package eu.fitped.priscilla.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.CODE_EVAL_MONITOR_URL_WSS
import eu.fitped.priscilla.model.TaskDto
import eu.fitped.priscilla.model.code.LoadCodeReqDto
import eu.fitped.priscilla.model.code.SaveProgramReqDto
import eu.fitped.priscilla.model.vpl.VPLGetResultReqDto
import eu.fitped.priscilla.model.vpl.VPLRunTestReqDto
import eu.fitped.priscilla.repository.ITaskRepository
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.service.websocket.ConnectionState
import eu.fitped.priscilla.service.websocket.IWebSocketListener
import eu.fitped.priscilla.service.websocket.IWebSocketService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.util.DataStateTaskEval
import eu.fitped.priscilla.viewModel.base.TaskViewModelBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import javax.inject.Inject

@HiltViewModel
class CodeTaskViewModel @Inject constructor(
    private val _courseService: ICourseService,
    private val _webSocketService: IWebSocketService,
    private val _taskRepository: ITaskRepository,
    savedStateHandle: SavedStateHandle
) : TaskViewModelBase(), IWebSocketListener {
    private val taskId: String = savedStateHandle["taskId"] ?: ""

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    private val _loadedCodeDataState = MutableStateFlow<DataState>(DataState.Loading)
    val loadedCodeDataState: StateFlow<DataState> get() = _loadedCodeDataState

    init {
        _webSocketService.addListener(this)
    }

    var adminTicket = ""

    init {
        println("CodeTaskViewInit")
    }

    fun getTask() : TaskDto? {
        return _taskRepository.getTaskById(taskId)
    }

    fun loadSavedCode(taskId: Long) {
        viewModelScope.launch {
            try {
                val response = _courseService.postLoadSavedProgram(taskId.toString(), LoadCodeReqDto("chapter"))
                if (response.isSuccessful) {
                    _loadedCodeDataState.value = DataState.Success(response.body()!!)
                } else {
                    _loadedCodeDataState.value = DataState.Error("Request error")
                }
            } catch (e: Exception) {
                _loadedCodeDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }

    fun evaluate(saveProgramReqDto: SaveProgramReqDto) {
        viewModelScope.launch {
            innerEvalDataState.value = DataStateTaskEval.Loading
            try {
                val responseSaveResponse = _courseService.postSaveProgram(saveProgramReqDto)
                if (!responseSaveResponse.isSuccessful) {
                    throw Exception("Failed to save the program")
                }
                val req = VPLRunTestReqDto(0, saveProgramReqDto.files, saveProgramReqDto.taskId)
                val runTestResponse = _courseService.postRunVPLTest(req)
                if (!runTestResponse.isSuccessful) {
                    throw Exception("Failed to run VPL test")
                }
                adminTicket = runTestResponse.body()?.adminTicket ?: ""
                runTestResponse.body()?.let { connectMonitorWSS(it.monitorTicket) }
            } catch (e: Exception) {
                innerEvalDataState.value = DataStateTaskEval.Error("Exception: ${e.message}")
            }
        }
    }

    fun getResult(adminTicket: String, taskId: Long, taskTypeId: Long) {
        viewModelScope.launch {
            try {
                val req = VPLGetResultReqDto("chapter", adminTicket, taskId, taskTypeId, true, 7)
                val resultResponse = _courseService.postGetVPLResult(req)
                if (resultResponse.isSuccessful)
                {
                    println(resultResponse.body())
                    innerEvalDataState.value = DataStateTaskEval.Success(resultResponse.body()!!)
                } else {
                    innerEvalDataState.value = DataStateTaskEval.Error("Request error")
                    println("Error")
                }
            } catch (e: Exception) {
                innerEvalDataState.value = DataStateTaskEval.Error("Request error")
            }
        }
    }

    fun connectMonitorWSS(monitorTicket: String) {
        connect(CODE_EVAL_MONITOR_URL_WSS.replace("{monitorTicket}", monitorTicket))
    }

    fun connect(url: String) {
        _connectionState.value = ConnectionState.CONNECTING
        println("CONNECTING")
        _webSocketService.connect(url)
    }

    fun disconnect() {
        _webSocketService.close()
        println("DISCONNECTED")
        _connectionState.value = ConnectionState.DISCONNECTED
    }

    override fun onCleared() {
        _webSocketService.removeListener(this)
        disconnect()
        println("onCleared")
        super.onCleared()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        _connectionState.value = ConnectionState.CONNECTED
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println(text)
        if (text == "retrieve:") {
            if (adminTicket.isNotEmpty()) {
                getResult(adminTicket, 2534, 8)
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        _connectionState.value = ConnectionState.CLOSING
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        _connectionState.value = ConnectionState.DISCONNECTED
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        _connectionState.value = ConnectionState.ERROR
    }

}