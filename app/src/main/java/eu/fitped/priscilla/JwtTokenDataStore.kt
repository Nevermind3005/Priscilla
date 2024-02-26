package eu.fitped.priscilla

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JwtTokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IJwtTokenManager {

    companion object {
        val ACCESS_TOKEN_PREF_KEY = stringPreferencesKey(ACCESS_TOKEN_KEY_STRING)
        val REFRESH_TOKEN_PREF_KEY = stringPreferencesKey(REFRESH_TOKEN_KEY_STRING)
    }

    override suspend fun saveAccessToken(access: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_PREF_KEY] = access
        }
    }

    override suspend fun saveRefreshToken(refresh: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_PREF_KEY] = refresh
        }
    }

    override suspend fun getAccessToken() : String? {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_PREF_KEY]
        }.first()
    }

    override suspend fun getRefreshToken() : String? {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_PREF_KEY]
        }.first()
    }

    override suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_PREF_KEY)
            preferences.remove(REFRESH_TOKEN_PREF_KEY)
        }
    }
}