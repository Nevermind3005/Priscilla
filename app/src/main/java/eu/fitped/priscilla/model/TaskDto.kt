package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskDto(
    @JsonProperty("task_id")
    val taskId: Long,
    @JsonProperty("task_type_id")
    val taskTypeId: Long,
    @JsonProperty("score")
    val score: Int,
    @JsonProperty("max_score")
    val maxScore: Int,
    @JsonProperty("first_time")
    val firstTime: Int,
    @JsonProperty("passed")
    val passed: Int,
    @JsonProperty("help_showed")
    val helpShowed: Int,
    @JsonProperty("answer_showed")
    val answerShowed: Int,
    @JsonProperty("task_order")
    val taskOrder: Int,
    @JsonProperty("discuss_count")
    val discussCount: Int,
    @JsonProperty("globals")
    val globals: String,
    @JsonProperty("content")
    val content: String,
    @JsonProperty("start_time")
    val startTime: Long?,
    @JsonProperty("end_time")
    val endTime: Long?,
    @JsonProperty("answer")
    val answer: String,
    @JsonProperty("comment")
    val comment: String?,
    @JsonProperty("clarity")
    val clarity: Int,
    @JsonProperty("difficulty")
    val difficulty: Int,
)