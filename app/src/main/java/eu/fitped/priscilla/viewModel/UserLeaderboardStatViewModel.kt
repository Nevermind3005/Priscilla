package eu.fitped.priscilla.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.ILeaderboardService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.base.ViewModelBase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLeaderboardStatViewModel @Inject constructor(
    private val _leaderboardService: ILeaderboardService,
    savedStateHandle: SavedStateHandle
) : ViewModelBase() {
    private val userId: String = savedStateHandle["userId"] ?: ""

    init {
        getUserLeaderboardStat()
    }

    private fun getUserLeaderboardStat() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = _leaderboardService.getLeaderboardUserStat(userId)
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
}