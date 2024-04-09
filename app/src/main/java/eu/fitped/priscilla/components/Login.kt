package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.R
import eu.fitped.priscilla.components.input.EmailField
import eu.fitped.priscilla.components.input.PasswordField
import eu.fitped.priscilla.ui.theme.Typography
//TODO fix invalid credentials
@Composable
fun Login(
    modifier: Modifier = Modifier,
    password: MutableState<String>,
    email: MutableState<String>,
    isPasswordValid: MutableState<Boolean>,
    isEmailValid: MutableState<Boolean>,
    onLoginClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.login),
                style = Typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        EmailField(email = email, isEmailValid = isEmailValid)
        PasswordField(password = password, isPasswordValid = isPasswordValid)
        Button(
            modifier = Modifier.padding(vertical = 8.dp),
            onClick = onLoginClick,
            enabled = isEmailValid.value && isPasswordValid.value
        ) {
            Text(text = stringResource(R.string.login))
        }
    }
}