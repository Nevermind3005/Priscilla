package eu.fitped.priscilla.viewModel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.JwtTokenDataStore
import eu.fitped.priscilla.model.LanguagePostDto
import eu.fitped.priscilla.service.ILanguageService
import eu.fitped.priscilla.service.IUserService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val _jwtTokenDataStore: JwtTokenDataStore,
    private val _languageService: ILanguageService,
    private val _userService: IUserService,
) : ViewModel() {

    private val _dataState = MutableStateFlow<DataState>(DataState.Loading)
    val dataState: StateFlow<DataState> get() = _dataState

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
        _dataState.value = DataState.Loading
        println("Get Data")
        viewModelScope.launch {
            try {
                val langResponse = _languageService.getLanguages()
                val userResponse = _userService.getUser()
                if (langResponse.isSuccessful && userResponse.isSuccessful) {
                    _dataState.value = DataState.Success(Pair(langResponse.body()!!, userResponse.body()!!))
                } else {
                    _dataState.value = DataState.Error("Request error")
                }
            } catch (e: Exception) {
                _dataState.value = DataState.Error("Exception: ${e.message}")
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
                _dataState.value = DataState.Error("Exception: ${e.message}")
            }
        }
    }
}