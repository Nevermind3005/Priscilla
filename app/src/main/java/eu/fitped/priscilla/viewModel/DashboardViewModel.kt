package eu.fitped.priscilla.viewModel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.base.ViewModelBase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val courseService: ICourseService,
    ) : ViewModelBase() {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = courseService.getUserCourses()
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