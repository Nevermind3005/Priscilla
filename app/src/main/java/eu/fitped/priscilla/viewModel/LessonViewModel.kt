package eu.fitped.priscilla.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val _courseService: ICourseService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val courseId: String = savedStateHandle["courseId"] ?: ""
    private val chapterId: String = savedStateHandle["chapterId"] ?: ""
    private val lessonId: String = savedStateHandle["lessonId"] ?: ""

    private val _dataState = MutableStateFlow<DataState>(DataState.Loading)
    val dataState: StateFlow<DataState> get() = _dataState

    private val _dataFetched = MutableLiveData(false) // Use MutableLiveData for controlled updates
    val dataFetched: LiveData<Boolean> get() = _dataFetched

    // ... other parts of your ViewModel ...

    init {
        if (!_dataFetched.value!!) {  // Access the LiveData value
            getLessonTasks()
            _dataFetched.value = true // Update livedata
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
                    _dataState.value = DataState.Success(response.body()!!)
                } else {
                    _dataState.value = DataState.Error("Request error")
                }
            } catch (e: Exception) {
                _dataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}
