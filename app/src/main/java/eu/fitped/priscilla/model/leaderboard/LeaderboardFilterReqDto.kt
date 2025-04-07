package eu.fitped.priscilla.model.leaderboard

import com.fasterxml.jackson.annotation.JsonProperty

data class LeaderboardFilterReqDto (
    @JsonProperty("areas")
    val areas: String = "",
    @JsonProperty("countries")
    val countries: String = "",
    @JsonProperty("courses")
    val courses: String = "",
    @JsonProperty("groups")
    val groups: String = "",
    @JsonProperty("start")
    val start: Long,
    @JsonProperty("end")
    val end: Long
)