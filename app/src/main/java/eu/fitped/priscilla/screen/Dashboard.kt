package eu.fitped.priscilla.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun Dashboard(
    modifier: Modifier = Modifier
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val jwtCoroutineScope = rememberCoroutineScope()

    var token by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        token = loginViewModel.jwtTokenDataStore.getAccessToken().toString()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = token)
        Button(onClick = {
            jwtCoroutineScope.launch {
                loginViewModel.jwtTokenDataStore.clearTokens()
            }
        }) {
            Text(text = "Logout")
        }
    }

}