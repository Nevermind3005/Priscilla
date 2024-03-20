package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AnswerDto(
    @JsonProperty("answer")
    val answer: String,
    @JsonProperty("feedback")
    val feedback: String
)