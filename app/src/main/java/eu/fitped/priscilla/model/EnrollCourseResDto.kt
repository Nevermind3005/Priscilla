package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EnrollCourseResDto (
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("user_id")
    val userId: Int,
    @JsonProperty("course_id")
    val courseId: String,
    @JsonProperty("tasks_finished")
    val tasksFinished: Int,
    @JsonProperty("programs_finished")
    val programsFinished: Int,
    @JsonProperty("max_score")
    val maxScore: Int,
    @JsonProperty("course_status")
    val courseStatus: String
)