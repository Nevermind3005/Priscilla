package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class LoginDto (
    @JsonProperty("client_id")
    val clientId: String = "2",
    @JsonProperty("client_secret")
    val clientSecret: String = "iQuGUAzqc187j7IKQ94tTVJAywHCAzYBGAMTxEtr",
    @JsonProperty("email")
    val email: String,
    @JsonProperty("grant_type")
    val grantType: String = "password",
    @JsonProperty("password")
    val password: String,
    @JsonProperty("username")
    val username: String
)