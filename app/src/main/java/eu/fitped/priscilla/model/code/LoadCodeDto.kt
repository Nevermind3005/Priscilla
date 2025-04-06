package eu.fitped.priscilla.model.code

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class LoadCodeReqDto (
    @JsonProperty("activity_type")
    val activityType: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class LoadCodeResDto (
    @JsonProperty("code")
    val code: String?
)