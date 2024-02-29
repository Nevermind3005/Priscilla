package eu.fitped.priscilla.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.fitped.priscilla.components.BottomNavigationBar
import eu.fitped.priscilla.screen.Dashboard
import eu.fitped.priscilla.screen.Login

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavigationItem.Login.route) {
            Login(navController = navController)
        }
        composable(NavigationItem.Home.route) {
            BottomNavigationBar()
        }
    }
}