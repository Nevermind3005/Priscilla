package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseDetailDto(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("chapter_list")
    val chapterList: List<ChapterDto>,
    @JsonProperty("chapters_count")
    val chapterCount: Int,
    @JsonProperty("competitions_count")
    val competitionsCount: Int,
    @JsonProperty("revisions_count")
    val revisionsCount: Int,
    @JsonProperty("exercises_count")
    val exercisesCount: Int,
    @JsonProperty("shortcuts_count")
    val shortcutsCount: Int,

)