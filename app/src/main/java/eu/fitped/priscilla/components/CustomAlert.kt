package eu.fitped.priscilla.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun CustomAlert(
    modifier: Modifier = Modifier,
    dismissible: Boolean,
    title: @Composable() (() -> Unit)?,
    dismissButton: @Composable() (() -> Unit)?,
    content: @Composable() (() -> Unit)?
) {
    val shouldDismiss = remember {
        mutableStateOf(false)
    }
    if (dismissible) {
        if (!shouldDismiss.value) {
            AlertDialog(
                title = {
                    title?.invoke()
                },
                text = {
                    content?.invoke()
                },
                onDismissRequest = {
                    shouldDismiss.value = true
                },
                confirmButton = {
                },
                dismissButton = {
                    TextButton(
                        onClick = { shouldDismiss.value = true },
                    ) {
                        dismissButton?.invoke()
                    }
                }
            )
        }
    } else {
        AlertDialog(
            title = {
                title?.invoke()
            },
            text = {
                content?.invoke()
                LinearProgressIndicator()
            },
            onDismissRequest = {
            },
            confirmButton = {
            },
            dismissButton = {
            }
        )
    }
}