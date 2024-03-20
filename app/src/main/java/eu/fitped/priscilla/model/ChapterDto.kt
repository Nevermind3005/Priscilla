package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChapterDto(
    @JsonProperty("chapter_order")
    val chapterOrder: Int,
    @JsonProperty("chapter_id")
    val chapterId: Long,
    @JsonProperty("chapter_name")
    val chapterName: String,
    @JsonProperty("chapter_icon")
    val chapterIcon: String,
    @JsonProperty("chapter_icon2")
    val chapterIconSecondary: String?,
    @JsonProperty("tasks_nonfinished")
    val tasksNotFinished: Int,
    @JsonProperty("tasks_finished")
    val tasksFinished: Int,
    @JsonProperty("programs_nonfinished")
    val programsNotFinished: Int,
    @JsonProperty("programs_finished")
    val programsFinished: Int,
)