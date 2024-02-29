package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.model.CoursesDto
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val courseService: ICourseService,
    ) : ViewModel() {
    private val _dataState = MutableStateFlow<DataState>(DataState.Loading)
    val dataState: StateFlow<DataState> get() = _dataState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            Thread {
                try {
                    val response = courseService.getUserCourses().execute()
                    if (response.isSuccessful) {
                        _dataState.value = DataState.Success(response.body()!!)
                    } else {
                        _dataState.value = DataState.Error("Request error")
                    }
                } catch (e: Exception) {
                    _dataState.value = DataState.Error("Exception: ${e.message}")
                }
            }.start()
        }
    }
}