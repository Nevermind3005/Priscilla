package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.model.leaderboard.LeaderboardEntry
import eu.fitped.priscilla.model.leaderboard.LeaderboardFilterReqDto
import eu.fitped.priscilla.service.ILeaderboardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val _leaderboardService: ILeaderboardService
) : ViewModel() {

    data class UiState(
        val entries: List<LeaderboardEntry> = emptyList(),
        val isLoading: Boolean = false,
        val hasMoreItems: Boolean = true,
        val error: String? = null,
        val currentPage: Long = 1
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _pageSize: Long = 25
    private var _offset: Long = 0

    init {
        loadMoreLeaderboardEntries()
    }

    fun loadMoreLeaderboardEntries() {
        if (_uiState.value.isLoading || !_uiState.value.hasMoreItems) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val filter = LeaderboardFilterReqDto(start = _offset, end = _pageSize)
                println(filter)
                val newEntriesRes = _leaderboardService.getLeaderboard(filter)
                if (newEntriesRes.isSuccessful) {
                    val newEntries = newEntriesRes.body()!!

                    _offset += newEntries.entries.size
                    println(_offset)
                    _uiState.update { currentState ->
                        currentState.copy(
                            entries = currentState.entries + newEntries.entries,
                            isLoading = false,
                            hasMoreItems = _pageSize.toInt() == newEntries.entries.size,
                            currentPage = currentState.currentPage + 1,
                            error = null
                        )
                    }
                } else {
                    throw Exception("Error loading more leaderboard entries")
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Error: ${e.localizedMessage}"
                )}
            }
        }
    }
}