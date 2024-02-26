package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.LoginDto
import eu.fitped.priscilla.model.TokenDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {

    @POST(Endpoints.LOGIN)
    fun login(@Body body: LoginDto): Call<TokenDto>



}