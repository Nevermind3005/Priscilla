package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CoursePreviewDto(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("course")
    val course: PreviewCourse,
    @JsonProperty("area")
    val area: Area,
    @JsonProperty("category")
    val category: Category,
    @JsonProperty("course_id")
    val courseId: Int,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("description_detail")
    val descriptionDetail: String,
    @JsonProperty("chapterlist")
    val chapterList: List<PreviewChapter>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PreviewCourse(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("description_detail")
    val descriptionDetail: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PreviewChapter(
    @JsonProperty("chapter_id")
    val chapterId: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("chapter_order")
    val chapterOrder: Int,
    @JsonProperty("chapter_icon")
    val chapterIcon: String?,
    @JsonProperty("chapter_icon2")
    val chapterIcon2: String?,
    @JsonProperty("chapter_type")
    val chapterType: Int,
    @JsonProperty("lessons")
    val lessons: List<PreviewLesson>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PreviewLesson(
    @JsonProperty("lesson_id")
    val lessonId: Int,
    @JsonProperty("lesson_order")
    val lessonOrder: Int,
    @JsonProperty("lesson")
    val lesson: String,
    @JsonProperty("tasks")
    val tasks: Int,
    @JsonProperty("tasks_score")
    val tasksScore: Int,
    @JsonProperty("programs")
    val programs: Int,
    @JsonProperty("programs_score")
    val programsScore: Int
)