package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.screen.Dashboard
import eu.fitped.priscilla.screen.Preferences
import eu.fitped.priscilla.screen.Topics

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier
) {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ) {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        onClick = { 
                            navigationSelectedItem = index
                            navController.navigate(bottomNavigationItem.route) { 
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            } 
                                  },
                        icon = {
                            Icon(
                                bottomNavigationItem.icon ,
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
            navController = navController,
            startDestination = NavigationItem.Dashboard.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(NavigationItem.Dashboard.route) {
                Dashboard()
            }
            composable(NavigationItem.Topics.route) {
                Topics()
            }
            composable(NavigationItem.Preferences.route) {
                Preferences()
            }

        }

    }
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = NavigationItem.Dashboard.route
            ),
            BottomNavigationItem(
                label = "Topics",
                icon = Icons.Filled.Search,
                route = NavigationItem.Topics.route
            ),
            BottomNavigationItem(
                label = "Preferences",
                icon = Icons.Filled.AccountCircle,
                route = NavigationItem.Preferences.route
            ),
        )
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}