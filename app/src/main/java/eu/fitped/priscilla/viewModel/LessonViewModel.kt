package eu.fitped.priscilla.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.repository.ITaskRepository
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.base.ViewModelBase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val _courseService: ICourseService,
    private val _taskRepository: ITaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModelBase() {
    private val courseId: String = savedStateHandle["courseId"] ?: ""
    private val chapterId: String = savedStateHandle["chapterId"] ?: ""
    private val lessonId: String = savedStateHandle["lessonId"] ?: ""

    private val _dataFetched = MutableLiveData(false)

    init {
        if (!_dataFetched.value!!) {
            getLessonTasks()
            _dataFetched.value = true
        }
    }

    private fun getLessonTasks() {
        viewModelScope.launch {
            try {
                val response = _courseService.getLessonTasks(
                    courseId = courseId,
                    chapterId = chapterId,
                    lessonId = lessonId,
                )
                if (response.isSuccessful) {
                    _taskRepository.setTasks(response.body()!!.taskList)
                    innerDataState.value = DataState.Success(response.body()!!)
                } else {
                    innerDataState.value = DataState.Error("Request error")
                }
            } catch (e: Exception) {
                innerDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}
