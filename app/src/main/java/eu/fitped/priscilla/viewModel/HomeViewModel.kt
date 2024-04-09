package eu.fitped.priscilla.viewModel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.IJwtTokenManager
import eu.fitped.priscilla.service.ILanguageService
import eu.fitped.priscilla.service.IUserService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val _languageService: ILanguageService,
    private val _userService: IUserService,
    private val _jwtTokenManager: IJwtTokenManager
) : ViewModel() {

    private val _dataState = MutableStateFlow<DataState>(DataState.Loading)
    val dataState: StateFlow<DataState> get() = _dataState

    init {
        setLanguage()
    }

    private fun setLanguage() {
        viewModelScope.launch {
            try {
                val langResponse = _languageService.getLanguages()
                val userResponse = _userService.getUser()
                if (langResponse.isSuccessful && userResponse.isSuccessful) {
                    val user = userResponse.body()
                    val lang = langResponse.body()
                    val langCode = lang?.find { it.id == user?.langId }?.shortcut
                    if (langCode != null) {
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langCode.lowercase()))
                    }
                    _dataState.value = DataState.Success("OK")
                } else {
                    if (langResponse.code() == 401 || userResponse.code() == 401) {
                        _jwtTokenManager.clearTokens()
                        _dataState.value = DataState.Error("401")
                    } else {
                        _dataState.value = DataState.Error("Request error")
                    }
                }
            } catch (e: Exception) {
                _dataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}
