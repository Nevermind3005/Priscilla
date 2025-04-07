package eu.fitped.priscilla.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.core.EndlessScrollList
import eu.fitped.priscilla.components.core.SimpleCard
import eu.fitped.priscilla.navigation.Screen
import eu.fitped.priscilla.viewModel.LeaderboardViewModel

@Composable
fun Leaderboard(
    navController: NavHostController
) {
    val leaderViewModel: LeaderboardViewModel = hiltViewModel()
    val uiState by leaderViewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        uiState.error?.let { errorMessage ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        } ?: run {
            EndlessScrollList(
                items = uiState.entries,
                itemContent = {
                    SimpleCard(
                        cardText = it.nickName,
                        onClick = {
                            navController.navigate("${Screen.USER_LEADERBOARD_STAT.name}/${it.id}/${it.nickName}")
                        }
                    )
                },
                onLoadMore = { leaderViewModel.loadMoreLeaderboardEntries() },
                isLoading = uiState.isLoading,
                hasMoreItems = uiState.hasMoreItems,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}