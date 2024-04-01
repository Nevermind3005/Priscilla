package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContent(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("help")
    val help: String,
    @JsonProperty("answer_list")
    val answerList: List<AnswerDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentText(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("help")
    val help: String,
)