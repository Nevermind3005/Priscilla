package eu.fitped.priscilla.viewModel

import android.util.Log
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
class TopicsViewModel @Inject constructor(
    private val _courseService: ICourseService
): ViewModel() {
    private val _dataState = MutableStateFlow<DataState>(DataState.Loading)
    val dataState: StateFlow<DataState> get() = _dataState

    init {
        getTopics()
    }

    private fun getTopics() {
        viewModelScope.launch {
            try {
                val response = _courseService.getCourseCategories()
                if (response.isSuccessful) {
                    _dataState.value = DataState.Success(response.body()!!)
                } else {
                    _dataState.value = DataState.Error("Request error")
                }
                Log.i("Data", _dataState.value.toString())
            } catch (e: Exception) {
                _dataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }

}