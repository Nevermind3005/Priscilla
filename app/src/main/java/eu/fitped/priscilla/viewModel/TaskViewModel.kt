package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataStateTaskEval
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val _courseService: ICourseService
) : ViewModel() {

    private val _dataState = MutableStateFlow<DataStateTaskEval>(DataStateTaskEval.Idle)
    val dataState: StateFlow<DataStateTaskEval> get() = _dataState

    init {
        println("ViewModelInit")
    }

    fun evaluate(taskEvalDto: TaskEvalDto) {
        viewModelScope.launch {
            _dataState.value = DataStateTaskEval.Loading
            try {
                val response = _courseService.postEvalTask(taskEvalDto)
                if (response.isSuccessful) {
                    _dataState.value = DataStateTaskEval.Success(response.body()!!)
                    println(response.body()!!)
                } else {
                    _dataState.value = DataStateTaskEval.Error("Request error")
                    println("Error")
                }
            } catch (e: Exception) {
                _dataState.value = DataStateTaskEval.Error("Exception: ${e.message}")
            }
        }
    }

    fun resetState() {
        _dataState.value = DataStateTaskEval.Idle
    }
}