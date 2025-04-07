package eu.fitped.priscilla.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.util.DataStateTaskEval
import eu.fitped.priscilla.viewModel.base.ViewModelBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursePreviewViewModel @Inject constructor(
    private val _courseService: ICourseService,
    savedStateHandle: SavedStateHandle
): ViewModelBase() {
    private val courseId: String = savedStateHandle["courseId"] ?: ""

    private val _enrollDataState = MutableStateFlow<DataStateTaskEval>(DataStateTaskEval.Idle)
    val enrollDataState: StateFlow<DataStateTaskEval> get() = _enrollDataState


    init {
        getCoursePreview()
    }

    private fun getCoursePreview() {
        viewModelScope.launch {
            try {
                val response = _courseService.getCoursePreview(courseId)
                if (response.isSuccessful) {
                    innerDataState.value = DataState.Success(response.body()!!)
                } else {
                    innerDataState.value = DataState.Error("Request error")
                }
            } catch (e: Exception) {
                innerDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }

    fun postEnrollCourse() {
        viewModelScope.launch {
            try {
                val response = _courseService.postEnrollCourse(courseId)
                if (response.isSuccessful) {
                    _enrollDataState.value = DataStateTaskEval.Success(response.body()!!)
                } else {
                    _enrollDataState.value = DataStateTaskEval.Error("Request error")
                }
            } catch (e: Exception) {
                _enrollDataState.value = DataStateTaskEval.Error("Exception: ${e.message}")
            }
        }
    }

    fun resetEnrollState() {
        _enrollDataState.value = DataStateTaskEval.Idle
    }

}