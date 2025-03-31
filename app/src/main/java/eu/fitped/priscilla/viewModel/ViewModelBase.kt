package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class ViewModelBase : ViewModel() {
    protected val innerDataState = MutableStateFlow<DataState>(DataState.Loading)
    val dataState: StateFlow<DataState> get() = innerDataState
}