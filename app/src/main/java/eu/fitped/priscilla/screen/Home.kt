package eu.fitped.priscilla.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.BottomNavigationBar

@Composable
fun Home(
    navController: NavHostController,
    ) {
    BottomNavigationBar(navController = navController)
}