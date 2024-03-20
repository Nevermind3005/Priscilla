package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.model.TaskContent

@Composable
fun CheckboxTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContent
) {
//    val (selectedOptions, onOptionsSelected) = remember {
//        mutableListOf<Boolean>().apply {
//            repeat(taskContent.answerList.size){ false }
//        }
//    }
    listOf<Boolean>()
    Column(
        modifier = modifier
    ) {
        HTMLText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            html = taskContent.content
        )
        taskContent.answerList.forEachIndexed { index, it ->
            val isChecked = remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (isChecked.value),
                        onClick = {
                            isChecked.value = !isChecked.value
                        }
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = (isChecked.value),
                    onCheckedChange = {
                        isChecked.value = it
                    }
                )
                Text(
                    text = it.answer,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }

}