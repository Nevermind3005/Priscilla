package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskEvalDto(
    @JsonProperty("activity_type")
    val activityType: String,
    @JsonProperty("answer_list")
    val answerList: List<String>,
    @JsonProperty("task_id")
    val taskId: Long,
    @JsonProperty("task_type_id")
    val taskTypeId: Long,
    @JsonProperty("time_length")
    val timeLength: Int,
)