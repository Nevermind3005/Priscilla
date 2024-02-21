package eu.fitped.priscilla

interface IJwtTokenManager {
    suspend fun saveAccessToken(access: String)
    suspend fun saveRefreshToken(refresh: String)
    suspend fun getAccessToken() : String?
    suspend fun getRefreshToken() : String?
    suspend fun clearTokens()
}