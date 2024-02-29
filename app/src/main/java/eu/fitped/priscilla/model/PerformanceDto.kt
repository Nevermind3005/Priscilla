package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PerformanceDto(
    @JsonProperty("xp")
    val xp: Int,
    @JsonProperty("coins")
    val coins: Int,
    @JsonProperty("level")
    val level: Int,
    @JsonProperty("badges")
    val badges: List<String>
)