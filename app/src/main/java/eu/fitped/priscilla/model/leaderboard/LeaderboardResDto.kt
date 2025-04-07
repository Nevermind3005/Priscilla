package eu.fitped.priscilla.model.leaderboard

import com.fasterxml.jackson.annotation.JsonProperty

data class LeaderboardResDto (
    @JsonProperty("count")
    val count: Long,
    @JsonProperty("list")
    val entries: List<LeaderboardEntry>
)

data class LeaderboardEntry (
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("nickname")
    val nickName: String,
    @JsonProperty("groups")
    val groups: String,
    @JsonProperty("xp")
    val xp: String,
    @JsonProperty("level_id")
    val levelId: Int,
    @JsonProperty("country")
    val country: String
)