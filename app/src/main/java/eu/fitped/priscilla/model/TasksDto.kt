package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TasksDto(
    @JsonProperty("course_id")
    val courseId: String,
    @JsonProperty("user_course_id")
    val userCourseId: Long,
    @JsonProperty("chapter_id")
    val chapterId: String,
    @JsonProperty("lesson_id")
    val lessonId: String,
    @JsonProperty("task_list")
    val taskList: List<TaskDto>
)