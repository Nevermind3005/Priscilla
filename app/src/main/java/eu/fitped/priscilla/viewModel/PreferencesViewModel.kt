package eu.fitped.priscilla.viewModel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.JwtTokenDataStore
import eu.fitped.priscilla.model.LanguagePostDto
import eu.fitped.priscilla.service.ILanguageService
import eu.fitped.priscilla.service.IUserService
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.base.ViewModelBase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val _jwtTokenDataStore: JwtTokenDataStore,
    private val _languageService: ILanguageService,
    private val _userService: IUserService,
) : ViewModelBase() {

    init {
        println("Init prefs")
        getData()
    }

    fun logout() {
        viewModelScope.launch {
            _jwtTokenDataStore.clearTokens()
        }
    }

    private fun getData() {
        innerDataState.value = DataState.Loading
        println("Get Data")
        viewModelScope.launch {
            try {
                val langResponse = _languageService.getLanguages()
                val userResponse = _userService.getUser()
                if (langResponse.isSuccessful && userResponse.isSuccessful) {
                    innerDataState.value = DataState.Success(Pair(langResponse.body()!!, userResponse.body()!!))
                } else {
                    innerDataState.value = DataState.Error("Request error")
                }
            } catch (e: Exception) {
                innerDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }

    public fun setUserLanguage(newLanguage: LanguagePostDto) {
        viewModelScope.launch {
            try {
                val langSetResponse = _languageService.setUserLanguage(newLanguage)
                if (langSetResponse.isSuccessful) {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLanguage.language.lowercase()))
                    getData()
                }
            } catch (e: Exception) {
                innerDataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}