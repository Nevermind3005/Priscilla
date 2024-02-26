package eu.fitped.priscilla

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.fitped.priscilla.service.IAuthService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginService: IAuthService,
    val jwtTokenDataStore: JwtTokenDataStore
) : ViewModel()