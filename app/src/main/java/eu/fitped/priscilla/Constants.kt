package eu.fitped.priscilla

const val BASE_URL = "https://app.priscilla.fitped.eu/"
const val CODE_EVAL_MONITOR_URL_WSS = "wss://vpl.ki.fpvai.ukf.sk/{monitorTicket}/monitor"
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
    const val COURSE_AREAS = "get-areas/{categoryId}"
    const val AREA_COURSES_ALL = "area-all-courses/{areaId}"
    const val COURSE_PREVIEW = "course-preview/{courseId}"
    const val SAVE_PROGRAM = "save-program3"
    const val LOAD_PROGRAM = "load-program3/{programId}"
    const val VPL_RUN_TEST = "vpl-run-test3"
    const val VPL_GET_RESULT = "vpl-get-result33"
    const val ENROLL_COURSE = "write-user-course/{courseId}"
    const val GET_LEADERBOARD = "get-leaders2"
    const val GET_LEADERBOARD_USER_STAT = "get-leader-course/{userId}"
}

const val ACCESS_TOKEN_KEY_STRING = "a_jwt"
const val REFRESH_TOKEN_KEY_STRING = "r_jwt"

const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"