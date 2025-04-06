package eu.fitped.priscilla.model.vpl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import eu.fitped.priscilla.model.Result

@JsonIgnoreProperties(ignoreUnknown = true)
data class VPLGetResultResDto (
    @JsonProperty("result")
    val result: Result,
    @JsonProperty("compilation")
    val compilation: String,
    @JsonProperty("execution")
    val execution: String
)

data class VPLGetResultReqDto (
    @JsonProperty("activity_type")
    val activityType: String,
    @JsonProperty("adminticket")
    val adminTicket: String,
    @JsonProperty("task_id")
    val taskId: Long,
    @JsonProperty("task_type_id")
    val taskTypeId: Long,
    @JsonProperty("tests_description")
    val taskDescription: Boolean,
    @JsonProperty("time_length")
    val timeLength: Int
)