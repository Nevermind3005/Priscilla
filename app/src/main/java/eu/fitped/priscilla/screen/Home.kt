package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.BottomNavigationBar
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.HomeViewModel

@Composable
fun Home(
    navController: NavHostController
) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    val state by homeViewModel.dataState.collectAsState()

    when (state) {
        is DataState.Loading -> Loading()
        is DataState.Success<*> -> {
            BottomNavigationBar(navController = navController)
        }
        is DataState.Error -> {
            val resError = (state as DataState.Error).message
            println(resError)
            if (resError == "401") {
                println("Error")
                navController.navigate(NavigationItem.Login.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            } else {
                Text("Error: ${(state as DataState.Error).message}")
            }
        }
    }
}