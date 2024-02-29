package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDto(
    @JsonProperty("user_id")
    val userId: String,
    @JsonProperty("lang_id")
    val langId: Int,
    @JsonProperty("country_id")
    val countryId: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("surname")
    val surname: String,
    @JsonProperty("email")
    val email: String,
    @JsonProperty("role_id")
    val roleId: String,
    @JsonProperty("content_type_id")
    val contentTypeId: Int,
    @JsonProperty("theme_id")
    val themeId: Int,
    @JsonProperty("theme_value")
    val themeValue: String,
    @JsonProperty("performance")
    val performance: PerformanceDto,
)