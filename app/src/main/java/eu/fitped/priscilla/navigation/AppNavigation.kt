package eu.fitped.priscilla.navigation

enum class Screen {
    LOGIN,
    HOME,
    PREFERENCES,
    DASHBOARD,
    TOPICS
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)

    object Login: NavigationItem(Screen.LOGIN.name)
    object Preferences: NavigationItem(Screen.PREFERENCES.name)
    object Topics: NavigationItem(Screen.TOPICS.name)


}