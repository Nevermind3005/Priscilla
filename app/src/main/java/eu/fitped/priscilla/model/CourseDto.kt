package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseDto(
    @JsonProperty("area_color")
    val areaColor: String,
    @JsonProperty("course_id")
    val courseId: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("score")
    val score: String,
    @JsonProperty("max_score")
    val maxScore: String,
    @JsonProperty("passed")
    val passed: String,
    @JsonProperty("all")
    val all: Int,
    @JsonProperty("content_count")
    val contentCount: String,
    @JsonProperty("program_count")
    val programCount: String,
    @JsonProperty("task_count")
    val taskCount: String,
    @JsonProperty("content_passed")
    val contentPassed: String,
    @JsonProperty("program_passed")
    val programPassed: String,
    @JsonProperty("task_passed")
    val taskPassed: String,
    @JsonProperty("score_task")
    val scoreTask: String,
    @JsonProperty("score_program")
    val scoreProgram: String,
    @JsonProperty("max_score_task")
    val maxScoreTask: String,
    @JsonProperty("max_score_program")
    val maxScoreProgram: String,
    @JsonProperty("progress")
    val progress: Int
)