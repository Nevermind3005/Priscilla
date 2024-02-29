package eu.fitped.priscilla.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import eu.fitped.priscilla.IJwtTokenManager
import eu.fitped.priscilla.JwtTokenDataStore
import eu.fitped.priscilla.api.AccessTokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Component determines the scope
object NetworkModule {

    @Singleton
    @Provides
    fun providesTokenManager(dataStore: DataStore<Preferences>): IJwtTokenManager {
        return JwtTokenDataStore(dataStore)
    }
}
