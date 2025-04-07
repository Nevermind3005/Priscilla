package eu.fitped.priscilla.model.leaderboard

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LeaderboardUserStatResDto(
    @JsonProperty("list")
    val entries: List<LeaderboardUserStatEntry>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class LeaderboardUserStatEntry (
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("score")
    val score: String,
    @JsonProperty("max")
    val maxScore: String,
    @JsonProperty("progress")
    val progress: Int
)