package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.model.TaskContent

@Composable
fun TextTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContent
) {
    var answer by remember { mutableStateOf(taskContent.answerList.first().answer) }
    Column(
        modifier = modifier
    ) {
        HTMLText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            html = taskContent.content
        )
        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text(text = "Answer") }
        )
    }
}