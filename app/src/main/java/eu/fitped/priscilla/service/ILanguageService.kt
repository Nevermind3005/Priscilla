package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.LanguageGetDto
import eu.fitped.priscilla.model.LanguagePostDto
import eu.fitped.priscilla.model.LanguageSetResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ILanguageService {

    @GET(Endpoints.LANGUAGES)
    suspend fun getLanguages(): Response<List<LanguageGetDto>>

    @POST(Endpoints.USER_CHANGE_LANGUAGE)
    suspend fun setUserLanguage(@Body body: LanguagePostDto): Response<LanguageSetResponseDto>

}