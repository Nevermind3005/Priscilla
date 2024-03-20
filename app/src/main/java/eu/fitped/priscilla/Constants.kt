package eu.fitped.priscilla

const val BASE_URL = "https://app.priscilla.fitped.eu/"
const val DATA_STORE_PREFERENCES = "user_preferences"

object Endpoints {
    const val LOGIN = "oauth/token"
    const val LOGOUT = "write-to-log2"
    const val USER = "get-full-user-parameters"
    const val USER_COURSES = "get-active-user-courses2"
    const val COURSE_CHAPTERS = "get-active-chapters2/{courseId}"
    const val CHAPTER_LESSONS = "get-active-lessons2/{chapterId}"
    const val LESSON_TASKS = "get-active-tasks2/{courseId}/{chapterId}/{lessonId}"
    const val TASK_EVAL = "task-evaluate2"
    const val USER_CHANGE_LANGUAGE = "change-user-language"
    const val LANGUAGES = "languages"
    const val COURSE_CATEGORIES = "get-categories2"
    const val COURSE_AREA = "get-areas"
    const val AREA_COURSED = "get-areas"
}

const val ACCESS_TOKEN_KEY_STRING = "a_jwt"
const val REFRESH_TOKEN_KEY_STRING = "r_jwt"

const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"