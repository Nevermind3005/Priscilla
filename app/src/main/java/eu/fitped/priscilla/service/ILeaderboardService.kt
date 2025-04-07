package eu.fitped.priscilla.service

import eu.fitped.priscilla.Endpoints
import eu.fitped.priscilla.model.leaderboard.LeaderboardFilterReqDto
import eu.fitped.priscilla.model.leaderboard.LeaderboardResDto
import eu.fitped.priscilla.model.leaderboard.LeaderboardUserStatResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ILeaderboardService {

    @POST(Endpoints.GET_LEADERBOARD)
    suspend fun getLeaderboard(@Body body: LeaderboardFilterReqDto) : Response<LeaderboardResDto>

    @GET(Endpoints.GET_LEADERBOARD_USER_STAT)
    suspend fun getLeaderboardUserStat(@Path("userId") userId: String) : Response<LeaderboardUserStatResDto>

}