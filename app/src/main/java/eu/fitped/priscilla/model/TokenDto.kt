package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenDto(
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)