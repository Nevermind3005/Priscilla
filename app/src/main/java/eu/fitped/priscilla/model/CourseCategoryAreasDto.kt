package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseCategoryAreasDto (
    @JsonProperty("category")
    val category: CourseCategoryInfo,
    @JsonProperty("areas")
    val areas: List<CourseCategoryAreaInfo>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseCategoryInfo (
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CourseCategoryAreaInfo (
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("area_name")
    val areaName: String,
    @JsonProperty("area_color")
    val areaColor: String,
    @JsonProperty("category_id")
    val categoryId: Int,
    @JsonProperty("area_order")
    val areaOrder: Int,
    @JsonProperty("number_of_courses")
    val numberOfCourses: Int
)