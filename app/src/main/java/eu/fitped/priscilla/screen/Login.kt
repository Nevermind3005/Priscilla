package eu.fitped.priscilla.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.PriscillaTheme
import eu.fitped.priscilla.viewModel.LoginViewModel
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.LoginDto
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.viewModel.LoginState

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.collectAsState()

    val context = LocalContext.current

    val password = remember {
        mutableStateOf("")
    }

    val isPasswordValid = remember {
        mutableStateOf(true)
    }

    val email = remember {
        mutableStateOf("")
    }

    val isEmailValid = remember {
        mutableStateOf(true)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 48.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (loginState) {
            is LoginState.LoggedIn -> {
                navController.popBackStack()
                navController.navigate(NavigationItem.Home.route)
            }
            is LoginState.Idle -> {
                Spacer(modifier = Modifier.size(64.dp))
                Icon(
                    modifier = Modifier.size(128.dp),
                    painter = painterResource(id = R.drawable.priscilla_logo_letter),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.size(64.dp))
                eu.fitped.priscilla.components.Login(
                    password = password,
                    email = email,
                    isPasswordValid = isPasswordValid,
                    isEmailValid = isEmailValid,
                    onLoginClick = {
                        val loginDto =
                            LoginDto(email = email.value, password = password.value, username = email.value)
                        loginViewModel.login(loginDto)
                    }
                )
            }
            is LoginState.Loading -> Loading()
            is LoginState.Success -> {
                navController.popBackStack()
                navController.navigate(NavigationItem.Home.route)
            }
            is LoginState.Error -> {
                Toast.makeText(
                    context,
                    "Error: ${(loginState as LoginState.Error).message}",
                    Toast.LENGTH_LONG
                ).show()
                loginViewModel.resetState();
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun LoginPreview() {
    PriscillaTheme {
        Surface {
            Login(navController = rememberNavController())
        }
    }
}