package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LessonDto(
    @JsonProperty("user_course_id")
    val userCourseId: String,
    @JsonProperty("chapter")
    val chapter: LessonInnerType,
    @JsonProperty("course")
    val course: LessonInnerType,
    @JsonProperty("area")
    val area: LessonInnerType,
    @JsonProperty("category")
    val category: LessonInnerType,
    @JsonProperty("lesson_list")
    val lessonDetailList: List<LessonDetail>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class LessonDetail(
    @JsonProperty("lesson_order")
    val lessonOrder: Int,
    @JsonProperty("lesson_id")
    val lessonId: Long,
    @JsonProperty("lesson_name")
    val lessonName: String,
    @JsonProperty("tasks_nonfinished")
    val tasksNotFinished: Int,
    @JsonProperty("tasks_finished")
    val tasksFinished: Int,
    @JsonProperty("programs_nonfinished")
    val programsNotFinished: Int,
    @JsonProperty("programs_finished")
    val programsFinished: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class LessonInnerType(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
)