package eu.fitped.priscilla.navigation

enum class Screen {
    LOGIN,
    HOME
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Login: NavigationItem(Screen.LOGIN.name)
}