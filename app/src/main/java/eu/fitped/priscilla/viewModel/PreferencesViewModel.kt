package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.JwtTokenDataStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val _jwtTokenDataStore: JwtTokenDataStore
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            _jwtTokenDataStore.clearTokens()
        }
    }

}