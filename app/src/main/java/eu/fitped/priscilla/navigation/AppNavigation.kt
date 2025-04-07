package eu.fitped.priscilla.navigation

enum class Screen {
    SPLASH,
    LOGIN,
    HOME,
    PREFERENCES,
    DASHBOARD,
    TOPICS,
    COURSE_DETAIL,
    CHAPTER_DETAIL,
    LESSON_TASKS,
    COURSE_CATEGORY_AREAS,
    AREA_DETAIL,
    COURSE_PREVIEW
}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object Login: NavigationItem(Screen.LOGIN.name)
    object Preferences: NavigationItem(Screen.PREFERENCES.name)
    object Topics: NavigationItem(Screen.TOPICS.name)
    object CourseDetail: NavigationItem("${Screen.COURSE_DETAIL.name}/{courseId}")
    object ChapterDetail: NavigationItem("${Screen.CHAPTER_DETAIL.name}/{chapterId}")
    object LessonTasks: NavigationItem("${Screen.LESSON_TASKS.name}/{courseId}/{chapterId}/{lessonId}")
    object CourseCategoryAreas: NavigationItem("${Screen.COURSE_CATEGORY_AREAS.name}/{categoryId}")
    object AreaDetail: NavigationItem("${Screen.AREA_DETAIL.name}/{areaId}")
    object CoursePreview: NavigationItem("${Screen.COURSE_PREVIEW}/{courseId}")
}