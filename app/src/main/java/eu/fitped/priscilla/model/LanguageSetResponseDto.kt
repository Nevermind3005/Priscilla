package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LanguageSetResponseDto(
    @JsonProperty("lang_id")
    val languageId: Long,
    @JsonProperty("message")
    val message: String,
)