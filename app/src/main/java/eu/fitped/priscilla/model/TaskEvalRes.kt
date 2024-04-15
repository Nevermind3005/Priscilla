package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskEvalRes(
    @JsonProperty("result")
    val result: Result
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Result(
    @JsonProperty("rating")
    val rating: Int
)