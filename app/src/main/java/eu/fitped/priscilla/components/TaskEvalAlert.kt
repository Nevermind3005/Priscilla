package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.TaskEvalRes
import eu.fitped.priscilla.util.DataStateTaskEval


@Composable
fun TaskEvalAlert(
    state: DataStateTaskEval,
    resetState: () -> Unit
) {
    when (state) {
        is DataStateTaskEval.Loading -> {
            AlertDialog(
                title = {
                    Text(text = stringResource(R.string.evaluating))
                },
                text = {
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
        is DataStateTaskEval.Success<*> -> {
            val res = (state as DataStateTaskEval.Success<TaskEvalRes>)
                AlertDialog(
                    title = {
                        Text(text = stringResource(R.string.evaluated))
                    },
                    text = {
                        StarRating(
                            score = res.data.result.rating,
                            maxScore = 100
                        )
                    },
                    onDismissRequest = {
                    },
                    confirmButton = {
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { resetState() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Oki")
                        }

                    }
                )
            }
        else -> {}
    }
}