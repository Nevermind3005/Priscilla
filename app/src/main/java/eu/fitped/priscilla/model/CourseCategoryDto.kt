package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseCategoryDto(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("category_id")
    val categoryId: Int,
    @JsonProperty("sort_order")
    val sortOrder: Int,
    @JsonProperty("color")
    val color: String,
    @JsonProperty("areas")
    val area: CourseCategoryProps,
    @JsonProperty("courses")
    val course: CourseCategoryProps,
    @JsonProperty("chapters")
    val chapter: CourseCategoryProps,
    @JsonProperty("lessons")
    val lesson: CourseCategoryProps,
    @JsonProperty("codes")
    val code: CourseCategoryProps
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseCategoryProps(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("value")
    val value: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseCategoriesDto(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("list")
    val list: List<CourseCategoryDto>
)