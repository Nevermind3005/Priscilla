package eu.fitped.priscilla.viewModel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.IJwtTokenManager
import eu.fitped.priscilla.service.ILanguageService
import eu.fitped.priscilla.service.IUserService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val _languageService: ILanguageService,
    private val _userService: IUserService,
    private val _jwtTokenManager: IJwtTokenManager
) : ViewModelBase() {

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
                    innerDataState.value = DataState.Success("OK")
                } else {
                    if (langResponse.code() == 401 || userResponse.code() == 401) {
                        _jwtTokenManager.clearTokens()
                        innerDataState.value = DataState.Error("401")
                    } else {
                        innerDataState.value = DataState.Error("Request error")
                    }
                }
            } catch (e: Exception) {
                innerDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}
