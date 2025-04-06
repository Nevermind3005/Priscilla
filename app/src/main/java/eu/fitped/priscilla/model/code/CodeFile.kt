package eu.fitped.priscilla.model.code

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CodeFile(
    @JsonProperty("filename")
    val filename: String,
    @JsonProperty("filecontent")
    var filecontent: String
)