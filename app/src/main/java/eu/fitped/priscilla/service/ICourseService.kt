package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.AreaDto
import eu.fitped.priscilla.model.CourseCategoriesDto
import eu.fitped.priscilla.model.CourseCategoryAreasDto
import eu.fitped.priscilla.model.CourseDetailDto
import eu.fitped.priscilla.model.CoursesDto
import eu.fitped.priscilla.model.LessonDto
import eu.fitped.priscilla.model.code.SaveProgramReqDto
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.model.TaskEvalRes
import eu.fitped.priscilla.model.TasksDto
import eu.fitped.priscilla.model.code.LoadCodeReqDto
import eu.fitped.priscilla.model.code.LoadCodeResDto
import eu.fitped.priscilla.model.vpl.VPLGetResultReqDto
import eu.fitped.priscilla.model.vpl.VPLGetResultResDto
import eu.fitped.priscilla.model.vpl.VPLRunTestReqDto
import eu.fitped.priscilla.model.vpl.VPLRunTestResDto
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

    @POST(Endpoints.SAVE_PROGRAM)
    suspend fun postSaveProgram(@Body body: SaveProgramReqDto) : Response<String>

    @POST(Endpoints.LOAD_PROGRAM)
    suspend fun postLoadSavedProgram(
        @Path("programId") programId: String,
        @Body body: LoadCodeReqDto
    ) : Response<LoadCodeResDto>

    @POST(Endpoints.VPL_RUN_TEST)
    suspend fun postRunVPLTest(@Body body: VPLRunTestReqDto) : Response<VPLRunTestResDto>

    @POST(Endpoints.VPL_GET_RESULT)
    suspend fun postGetVPLResult(@Body body: VPLGetResultReqDto) : Response<VPLGetResultResDto>

    @GET(Endpoints.COURSE_CATEGORIES)
    suspend fun getCourseCategories() : Response<CourseCategoriesDto>

    @GET(Endpoints.COURSE_AREAS)
    suspend fun getCourseCategoryAreas(@Path("categoryId") categoryId: String): Response<CourseCategoryAreasDto>

    @GET(Endpoints.AREA_COURSES_ALL)
    suspend fun getAreaCourses(@Path("areaId") areaId: String): Response<AreaDto>
}