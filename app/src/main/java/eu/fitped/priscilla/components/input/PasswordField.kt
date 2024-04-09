package eu.fitped.priscilla.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.R
import eu.fitped.priscilla.util.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    password: MutableState<String>,
    isPasswordValid: MutableState<Boolean>
) {
    var showPassword by remember { mutableStateOf(value = false) }
    OutlinedTextField(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .onFocusChanged {
                isPasswordValid.value = password.value.isNotEmpty()
            }
            .autofill(
                autofillTypes = listOf(AutofillType.Password),
                onFill = { password.value = it }
            ),
        value = password.value,
        shape = MaterialTheme.shapes.extraLarge,
        onValueChange = {
            password.value = it
            isPasswordValid.value = password.value.isNotEmpty()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            capitalization = KeyboardCapitalization.None,
        ),
        supportingText = {
            if (!isPasswordValid.value) {
                Text(text = stringResource(R.string.password_is_not_valid))
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.password))
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = !isPasswordValid.value,
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(painter = painterResource(id = R.drawable.visibility_24px), contentDescription = "Show password button")
                }
            } else {
                IconButton(onClick = { showPassword = true }) {
                    Icon(painter = painterResource(id = R.drawable.visibility_off_24px), contentDescription = "Hide password button")
                }
            }
        }
    )
}