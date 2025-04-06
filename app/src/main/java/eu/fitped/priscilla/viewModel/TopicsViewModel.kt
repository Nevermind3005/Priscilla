package eu.fitped.priscilla.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.base.ViewModelBase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val _courseService: ICourseService
) : ViewModelBase() {

    init {
        getTopics()
    }

    private fun getTopics() {
        viewModelScope.launch {
            try {
                val response = _courseService.getCourseCategories()
                if (response.isSuccessful) {
                    innerDataState.value = DataState.Success(response.body()!!)
                } else {
                    innerDataState.value = DataState.Error("Request error")
                }
                Log.i("Data", innerDataState.value.toString())
            } catch (e: Exception) {
                innerDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}