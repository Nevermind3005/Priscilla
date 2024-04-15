package eu.fitped.priscilla.util

sealed class DataStateTaskEval {
    data object Idle : DataStateTaskEval()
    data object Loading : DataStateTaskEval()
    data class Success<T>(val data: T) : DataStateTaskEval()
    data class Error(val message: String) : DataStateTaskEval()
}