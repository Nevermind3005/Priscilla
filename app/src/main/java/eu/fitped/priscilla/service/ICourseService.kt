package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.CourseCategoriesDto
import eu.fitped.priscilla.model.CourseDetailDto
import eu.fitped.priscilla.model.CoursesDto
import eu.fitped.priscilla.model.LessonDto
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.model.TaskEvalRes
import eu.fitped.priscilla.model.TasksDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ICourseService {

    @GET(Endpoints.USER_COURSES)
    suspend fun getUserCourses(): Response<CoursesDto>

    @GET(Endpoints.COURSE_CHAPTERS)
    suspend fun getCourseChapters(@Path("courseId") courseId: String): Response<CourseDetailDto>

    @GET(Endpoints.CHAPTER_LESSONS)
    suspend fun getChapterLessons(@Path("chapterId") chapterId: String): Response<LessonDto>

    @GET(Endpoints.LESSON_TASKS)
    suspend fun getLessonTasks(
        @Path("courseId") courseId: String,
        @Path("chapterId") chapterId: String,
        @Path("lessonId") lessonId: String,
    ): Response<TasksDto>

    @POST(Endpoints.TASK_EVAL)
    suspend fun postEvalTask(@Body body: TaskEvalDto): Response<TaskEvalRes>

    @GET(Endpoints.COURSE_CATEGORIES)
    suspend fun getCourseCategories() : Response<CourseCategoriesDto>
}