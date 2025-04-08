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
    COURSE_PREVIEW,
    LEADERBOARD,
    USER_LEADERBOARD_STAT,
    // Navigation for tasks
    TASK_TEXT_ONLY,
    TASK_TEXT_INPUT,
    TASK_RADIO_INPUT,
    TASK_CHECKBOX_INPUT,
    TASK_INLINE_TEXT_INPUT,
    TASK_DND,
    TASK_DRAGGABLE,
    TASK_CODE_JAVA,
    TASK_UNDEFINED,
    TASK_NOT_IMPLEMENTED
}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object Login: NavigationItem(Screen.LOGIN.name)
    object Preferences: NavigationItem(Screen.PREFERENCES.name)
    object Topics: NavigationItem(Screen.TOPICS.name)
    object Leaderboard: NavigationItem(Screen.LEADERBOARD.name)
    object CourseDetail: NavigationItem("${Screen.COURSE_DETAIL.name}/{courseId}")
    object ChapterDetail: NavigationItem("${Screen.CHAPTER_DETAIL.name}/{chapterId}")
    object LessonTasks: NavigationItem("${Screen.LESSON_TASKS.name}/{courseId}/{chapterId}/{lessonId}")
    object CourseCategoryAreas: NavigationItem("${Screen.COURSE_CATEGORY_AREAS.name}/{categoryId}")
    object AreaDetail: NavigationItem("${Screen.AREA_DETAIL.name}/{areaId}")
    object CoursePreview: NavigationItem("${Screen.COURSE_PREVIEW}/{courseId}")
    object UserLeaderboardStat: NavigationItem("${Screen.USER_LEADERBOARD_STAT}/{userId}/{nickName}")
    // Navigation for tasks
    object TaskTextOnly: NavigationItem("${Screen.TASK_TEXT_ONLY}/{taskId}")
    object TaskTextInput: NavigationItem("${Screen.TASK_TEXT_INPUT}/{taskId}")
    object TaskRadioInput: NavigationItem("${Screen.TASK_RADIO_INPUT}/{taskId}")
    object TaskCheckboxInput: NavigationItem("${Screen.TASK_CHECKBOX_INPUT}/{taskId}")
    object TaskInlineTextInput: NavigationItem("${Screen.TASK_INLINE_TEXT_INPUT}/{taskId}")
    object TaskDND: NavigationItem("${Screen.TASK_DND}/{taskId}")
    object TaskDraggable: NavigationItem("${Screen.TASK_DRAGGABLE}/{taskId}")
    object TaskCodeJava: NavigationItem("${Screen.TASK_CODE_JAVA}/{taskId}")
    object TaskUndefined: NavigationItem("${Screen.TASK_UNDEFINED}")
    object TaskNotImplemented: NavigationItem("${Screen.TASK_NOT_IMPLEMENTED}")
}