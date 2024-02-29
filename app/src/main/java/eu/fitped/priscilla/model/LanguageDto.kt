package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LanguageDto(
    @JsonProperty("lang")
    val language: String
)