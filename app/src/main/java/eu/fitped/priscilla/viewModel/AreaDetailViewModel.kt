package eu.fitped.priscilla.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AreaDetailViewModel @Inject constructor(
    private val _courseService: ICourseService,
    savedStateHandle: SavedStateHandle
): ViewModelBase() {
    private val areaId: String = savedStateHandle["areaId"] ?: ""

    init {
        getAreaCourses()
    }

    private fun getAreaCourses() {
        viewModelScope.launch {
            try {
                val response = _courseService.getAreaCourses(areaId)
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

}