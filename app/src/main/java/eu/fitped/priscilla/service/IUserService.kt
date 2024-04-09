package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.UserDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface IUserService {

    @GET(Endpoints.USER)
    suspend fun getUser(): Response<UserDto>
}