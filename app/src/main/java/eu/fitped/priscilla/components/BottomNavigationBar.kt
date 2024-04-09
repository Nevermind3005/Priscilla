package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.screen.Dashboard
import eu.fitped.priscilla.screen.Preferences
import eu.fitped.priscilla.screen.Topics

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val localNavController = rememberNavController()

    localNavController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            NavigationItem.Dashboard.route -> {
                navigationSelectedItem = 0
            }
            NavigationItem.Topics.route -> {
                navigationSelectedItem = 1
            }
            NavigationItem.Preferences.route -> {
                navigationSelectedItem = 2
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ) {
                BottomNavigationItem(icon = painterResource(id = R.drawable.home_24px)).bottomNavigationItems().forEachIndexed {index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        onClick = {
                            localNavController.popBackStack()
                            localNavController.navigate(bottomNavigationItem.route) {
                                popUpTo(localNavController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                bottomNavigationItem.icon,
                                contentDescription = bottomNavigationItem.label
                            )
                        },
                        label = { Text(text = bottomNavigationItem.label) },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = localNavController,
            startDestination = NavigationItem.Dashboard.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(NavigationItem.Dashboard.route) {
                Dashboard(navController = navController)
            }
            composable(NavigationItem.Topics.route) {
                Topics()
            }
            composable(NavigationItem.Preferences.route) {
                Preferences(navController = navController)
            }
        }
    }
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : Painter,
    val route : String = ""
) {

    @Composable
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = stringResource(R.string.home),
                icon = painterResource(id = R.drawable.home_24px),
                route = NavigationItem.Dashboard.route
            ),
            BottomNavigationItem(
                label = stringResource(R.string.topics),
                icon = painterResource(id = R.drawable.dashboard_customize_24px),
                route = NavigationItem.Topics.route
            ),
            BottomNavigationItem(
                label = stringResource(R.string.preferences),
                icon = painterResource(id = R.drawable.tune_24px),
                route = NavigationItem.Preferences.route
            ),
        )
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController())
}