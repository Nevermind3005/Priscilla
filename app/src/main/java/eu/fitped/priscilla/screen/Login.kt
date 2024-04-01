package eu.fitped.priscilla.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import eu.fitped.priscilla.ui.theme.Typography
import eu.fitped.priscilla.util.autofill
import eu.fitped.priscilla.util.isValidEmail
import eu.fitped.priscilla.viewModel.LoginState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.collectAsState()

    val context = LocalContext.current

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

    val autofillNode = AutofillNode(
        autofillTypes = listOf(AutofillType.EmailAddress),
        onFill = { email = it }
    )
    val autofill = LocalAutofill.current

    LocalAutofillTree.current += autofillNode

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
                Column(
                    modifier = Modifier.fillMaxHeight(),
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
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                autofillNode.boundingBox = it.boundsInWindow()
                            }
                            .onFocusChanged { focusState ->
                                autofill?.run {
                                    if (focusState.isFocused) {
                                        requestAutofillForNode(autofillNode)
                                    } else {
                                        cancelAutofillForNode(autofillNode)
                                    }
                                }
                            },
                        value = email,
                        shape = MaterialTheme.shapes.extraLarge,
                        onValueChange = {
                            email = it
                            isEmailValid = it.isNotEmpty() && it.isValidEmail()
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Email,
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
                            .fillMaxWidth().autofill(
                                autofillTypes = listOf(AutofillType.Password),
                                onFill = { password = it }
                            ),
                        value = password,
                        shape = MaterialTheme.shapes.extraLarge,
                        onValueChange = {
                            password = it
                            isPasswordValid = it.isNotEmpty()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.None,
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
                            val loginDto =
                                LoginDto(email = email, password = password, username = email)
                            loginViewModel.login(loginDto)
                        }
                    ) {
                        Text(text = "Login")
                    }
//                }
                }
            }
            is LoginState.Loading -> Loading()
            is LoginState.Success -> {
                navController.popBackStack()
                navController.navigate(NavigationItem.Home.route)
            }
            is LoginState.Error -> Toast.makeText(context, "Error: ${(loginState as LoginState.Error).message}", Toast.LENGTH_SHORT).show()
            else -> println("Error idk what happened")
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