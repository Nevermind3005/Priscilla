package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val _courseService: ICourseService
) : ViewModel() {

    private val _dataState = MutableStateFlow<DataState>(DataState.Loading)

    init {
        println("ViewModelInit")
    }

    fun evaluate(taskEvalDto: TaskEvalDto) {
        viewModelScope.launch {
            try {
                val response = _courseService.postEvalTask(taskEvalDto)
                if (response.isSuccessful) {
                    _dataState.value = DataState.Success(response.body()!!)
                    println(response.body()!!)
                } else {
                    _dataState.value = DataState.Error("Request error")
                    println("Error")
                }
            } catch (e: Exception) {
                _dataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}