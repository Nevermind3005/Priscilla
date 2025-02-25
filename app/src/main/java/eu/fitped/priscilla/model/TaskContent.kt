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

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentDraggable(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("help")
    val help: String,
    @JsonProperty("code")
    val code: String,
    @JsonProperty("codes")
    val codes: List<String>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentDND(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("fakes")
    val fakes: List<String>,
)