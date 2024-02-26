package eu.fitped.priscilla.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.PriscillaTheme
import eu.fitped.priscilla.LoginViewModel
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.LoginDto
import eu.fitped.priscilla.model.TokenDto
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.ui.theme.Typography
import eu.fitped.priscilla.util.isValidEmail
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val jwtCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (loginViewModel.jwtTokenDataStore.getAccessToken() != null)
        navController.navigate(NavigationItem.Home.route)
    }

    var password by remember {
        mutableStateOf("")
    }

    var isPasswordValid by remember {
        mutableStateOf(true)
    }

    var email by remember {
        mutableStateOf("")
    }

    var isEmailValid by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 48.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Login",
                style = Typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            value = email,
            shape = MaterialTheme.shapes.extraLarge,
            onValueChange = {
                email = it
                isEmailValid = it.isNotEmpty() && it.isValidEmail()
                println(isEmailValid)
                            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            supportingText = {
                if (!isEmailValid) {
                    Text(text = "Email is not valid")
                }
                             },
            placeholder = {
                Text(text = stringResource(R.string.email))
            },
            isError = !isEmailValid,
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            value = password,
            shape = MaterialTheme.shapes.extraLarge,
            onValueChange = {
                password = it
                isPasswordValid = it.isNotEmpty()
                            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            supportingText = {
                if (!isPasswordValid) {
                    Text(text = "Password is not valid")
                }
            },
            placeholder = {
                Text(text = stringResource(R.string.password))
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPasswordValid,
            )
        Button(
            modifier = Modifier.padding(vertical = 8.dp),
            onClick = {
                val loginDto = LoginDto(email = email, password = password, username = email)
                loginViewModel.loginService.login(loginDto).enqueue(object : Callback<TokenDto> {
                    override fun onResponse(call: Call<TokenDto>, response: Response<TokenDto>) {
                        if (response.code() == 200) {
                            val token = response.body()
                            if (token != null) {
                                jwtCoroutineScope.launch {
                                    loginViewModel.jwtTokenDataStore.saveAccessToken(token.accessToken)
                                    loginViewModel.jwtTokenDataStore.saveRefreshToken(token.refreshToken)
                                }
                            }
                            navController.popBackStack(NavigationItem.Login.route, true)
                            navController.navigate(NavigationItem.Home.route)
                        }
                    }
                    override fun onFailure(call: Call<TokenDto>, t: Throwable) {
                    }
                })
            }
        ) {
            Text(text = "Login")
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