package eu.fitped.priscilla.navigation

enum class Screen {
    LOGIN,
    HOME,
    PREFERENCES,
    DASHBOARD,
    TOPICS,
    COURSE_DETAIL,
    CHAPTER_DETAIL,
    LESSON_TASKS
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)

    object Login: NavigationItem(Screen.LOGIN.name)
    object Preferences: NavigationItem(Screen.PREFERENCES.name)
    object Topics: NavigationItem(Screen.TOPICS.name)
    object CourseDetail: NavigationItem("${Screen.COURSE_DETAIL.name}/{courseId}")
    object ChapterDetail: NavigationItem("${Screen.CHAPTER_DETAIL.name}/{chapterId}")
    object LessonTasks: NavigationItem("${Screen.LESSON_TASKS.name}/{courseId}/{chapterId}/{lessonId}")
}