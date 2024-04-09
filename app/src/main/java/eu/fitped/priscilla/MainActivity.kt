package eu.fitped.priscilla

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.PriscillaTheme
import dagger.hilt.android.AndroidEntryPoint
import eu.fitped.priscilla.navigation.AppNavHost
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.viewModel.LoginState
import eu.fitped.priscilla.viewModel.LoginViewModel
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val userState by viewModels<UserStateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val loginState by loginViewModel.loginState.collectAsState()
            val startDestination = if (loginState == LoginState.LoggedIn) NavigationItem.Home.route else NavigationItem.Login.route
            println(loginState)
            val navController = rememberNavController()

            PriscillaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(UserState provides userState) {
                        AppNavHost(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}

class UserStateViewModel : ViewModel() {

    var isLoggedIn by mutableStateOf(false)

    var isBusy by mutableStateOf(false)


    suspend fun signIn(email: String, password: String) {

        isBusy = true

        delay(2000)

        isLoggedIn = true

        isBusy = false

    }


    suspend fun signOut() {

        isBusy = true

        delay(2000)

        isLoggedIn = false

        isBusy = false

    }

}
val UserState = compositionLocalOf<UserStateViewModel> { error("User State Context Not Found!") }