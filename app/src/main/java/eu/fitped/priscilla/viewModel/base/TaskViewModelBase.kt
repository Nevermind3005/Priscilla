package eu.fitped.priscilla.viewModel.base

import androidx.lifecycle.ViewModel
import eu.fitped.priscilla.util.DataStateTaskEval
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class TaskViewModelBase : ViewModel() {
    protected val innerEvalDataState = MutableStateFlow<DataStateTaskEval>(DataStateTaskEval.Idle)
    val evalDataState: StateFlow<DataStateTaskEval> get() = innerEvalDataState

    fun resetEvalState(){
        innerEvalDataState.value = DataStateTaskEval.Idle
    }
}
