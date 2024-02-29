package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.CoursesDto
import retrofit2.Call
import retrofit2.http.GET

interface ICourseService {

    @GET(Endpoints.USER_COURSES)
    fun getUserCourses(): Call<CoursesDto>

}