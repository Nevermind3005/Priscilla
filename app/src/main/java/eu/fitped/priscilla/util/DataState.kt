package eu.fitped.priscilla.util

sealed class DataState {
    data object Loading : DataState()
    data class Success<T>(val data: T) : DataState()
    data class Error(val message: String) : DataState()
}
