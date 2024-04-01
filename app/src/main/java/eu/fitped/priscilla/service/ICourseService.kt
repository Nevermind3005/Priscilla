package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.CourseDetailDto
import eu.fitped.priscilla.model.CoursesDto
import eu.fitped.priscilla.model.LessonDto
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.model.TasksDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ICourseService {

    @GET(Endpoints.USER_COURSES)
    fun getUserCourses(): Call<CoursesDto>

    @GET(Endpoints.COURSE_CHAPTERS)
    fun getCourseChapters(@Path("courseId") courseId: String): Call<CourseDetailDto>

    @GET(Endpoints.CHAPTER_LESSONS)
    fun getChapterLessons(@Path("chapterId") chapterId: String): Call<LessonDto>

    @GET(Endpoints.LESSON_TASKS)
    suspend fun getLessonTasks(
        @Path("courseId") courseId: String,
        @Path("chapterId") chapterId: String,
        @Path("lessonId") lessonId: String,
    ): Response<TasksDto>

    @POST(Endpoints.TASK_EVAL)
    suspend fun postEvalTask(@Body body: TaskEvalDto): Response<String>
}