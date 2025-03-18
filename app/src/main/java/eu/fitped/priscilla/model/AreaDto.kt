package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AreaDto(
    @JsonProperty("area")
    val area: Area,
    @JsonProperty("category")
    val category: Category,
    @JsonProperty("list")
    val courses: List<CourseInfo>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Area(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("area_color")
    val areaColor: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Category(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseInfo(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("course_order")
    val courseOrder: Int,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("course_status")
    val courseStatus: String?,
    @JsonProperty("task_finished")
    val taskFinished: Int,
    @JsonProperty("program_finished")
    val programFinished: Int,
    @JsonProperty("content_all")
    val contentAll: Int,
    @JsonProperty("task_all")
    val taskAll: Int
)