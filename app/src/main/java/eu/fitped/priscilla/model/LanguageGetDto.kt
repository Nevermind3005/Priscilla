package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LanguageGetDto(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("shortcut")
    val shortcut: String,
) {
    companion object {
        private val flag = mapOf(
            Pair("SK", "\uD83C\uDDF8\uD83C\uDDF0"),
            Pair("CS", "\uD83C\uDDE8\uD83C\uDDFF"),
            Pair("EN", "\uD83C\uDDFA\uD83C\uDDF8"),
            Pair("PL", "\uD83C\uDDF5\uD83C\uDDF1"),
            Pair("ES", "\uD83C\uDDED\uD83C\uDDFA"),
            Pair("HU", "\uD83C\uDDED\uD83C\uDDFA"),
            Pair("NL", "\uD83C\uDDF3\uD83C\uDDF1"),
            Pair("RU", "\uD83C\uDDF7\uD83C\uDDFA"),
        )
    }

    private fun getFlag(): String {
        return flag.getOrDefault(shortcut, "\uD83C\uDFC1")
    }

    fun getFormattedName(): String {
        return "${getFlag()} - ${name.replaceFirstChar(Char::titlecase)}"
    }
}