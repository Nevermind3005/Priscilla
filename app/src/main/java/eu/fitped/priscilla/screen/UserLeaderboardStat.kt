package eu.fitped.priscilla.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.components.core.GenericList
import eu.fitped.priscilla.components.core.SimpleCard
import eu.fitped.priscilla.model.leaderboard.LeaderboardUserStatResDto
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.UserLeaderboardStatViewModel

@Composable
fun UserLeaderboardStat(
    userId: String?,
    nickName: String?
) {
    val userLeaderboardStateViewModel: UserLeaderboardStatViewModel = hiltViewModel();
    val state by userLeaderboardStateViewModel.dataState.collectAsState()

    if (userId != null) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> {
                val data = (state as DataState.Success<LeaderboardUserStatResDto>).data
                GenericList(
                    headerText = nickName ?: "UNKNOWN",
                    items = data.entries,
                    itemKey = { it.id }
                ) {
                    SimpleCard(cardText = it.name) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            LinearProgressIndicator(
                                progress = it.progress / 100.0f
                            )
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Text("${it.score}/${it.maxScore}")
                            }
                        }
                    }
                }
            }
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}