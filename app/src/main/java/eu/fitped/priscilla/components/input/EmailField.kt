package eu.fitped.priscilla.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.R
import eu.fitped.priscilla.util.autofill
import eu.fitped.priscilla.util.isValidEmail

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    isEmailValid: MutableState<Boolean>,
    nextFieldFocusRequester: FocusRequester = FocusRequester()
    ) {
    OutlinedTextField(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .fillMaxWidth()
            .onFocusChanged {
                isEmailValid.value = email.value.isNotEmpty() && email.value.isValidEmail()
            }
            .autofill(
                autofillTypes = listOf(AutofillType.EmailAddress),
                onFill = { email.value = it }
            ),
        value = email.value,
        shape = MaterialTheme.shapes.extraLarge,
        onValueChange = {
            email.value = it
            isEmailValid.value = email.value.isNotEmpty() && email.value.isValidEmail()
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                nextFieldFocusRequester.requestFocus();
            }
        ),
        supportingText = {
            if (!isEmailValid.value) {
                Text(text = stringResource(R.string.email_is_not_valid))
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.email))
        },
        isError = !isEmailValid.value,
    )
}