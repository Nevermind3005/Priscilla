package eu.fitped.priscilla.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.IJwtTokenManager
import eu.fitped.priscilla.JwtTokenDataStore
import eu.fitped.priscilla.model.LoginDto
import eu.fitped.priscilla.model.TokenDto
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.service.IAuthService
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.service.IUserService
import eu.fitped.priscilla.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _loginService: IAuthService,
    private val _jwtTokenDataStore: JwtTokenDataStore
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        viewModelScope.launch {
            if (_jwtTokenDataStore.getAccessToken() != null) {
                _loginState.value = LoginState.LoggedIn
            }
        }
    }

    fun login(loginDto: LoginDto) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = withContext(Dispatchers.IO) { // Switch to IO dispatcher
                    _loginService.login(loginDto).execute()
                }

                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success(response.body()!!)
                    _jwtTokenDataStore.saveAccessToken(response.body()!!.accessToken)
                    _jwtTokenDataStore.saveRefreshToken(response.body()!!.refreshToken)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login failed: ${e.message}")
            }        }
    }

}

sealed class LoginState {
    data object LoggedIn: LoginState()
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val token: TokenDto) : LoginState()
    data class Error(val message: String) : LoginState()
}
