package eu.fitped.priscilla.screen.task

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.components.TaskEvalAlert
import eu.fitped.priscilla.model.TaskContentDraggable
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.model.TaskType
import eu.fitped.priscilla.viewModel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Collections

@Composable
fun DraggableItemsTask(
    modifier: Modifier = Modifier,
    taskId: String?,
    onClick: MutableState<() -> Unit>
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    val state by taskViewModel.dataState.collectAsState()

    val startTime = System.currentTimeMillis() / 1000

    val mapper = jacksonObjectMapper()
    val taskContent: TaskContentDraggable = mapper.readValue(taskViewModel.getTask()!!.content)

    val values = remember {
        taskContent.codes.toMutableStateList()
    }

    onClick.value = {
        val currentTime = System.currentTimeMillis() / 1000
        val taskAnswer = TaskEvalDto(
            activityType = "chapter",
            taskId = taskId!!.toLong(),
            taskTypeId = TaskType.DRAGGABLE.id,
            timeLength = currentTime - startTime,
            answerList = values
        )
        taskViewModel.evaluate(taskAnswer)
    }

    TaskEvalAlert(
        state = state,
        resetState = {taskViewModel.resetState()}
    )

    fun swapItem(a: Int, b: Int) {
        Collections.swap(values, a, b)
    }
    Column(
        modifier = modifier
    ) {
        HTMLText(html = taskContent.content)
        TaskList(
            items = values,
            onMove = { fromIndex, toIndex ->
            swapItem(fromIndex, toIndex)
            },
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

}

@Composable fun TaskList(
    items: List<String>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val overscrollJob = remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)

    LazyColumn(
        modifier = modifier.dragGestureHandler(coroutineScope, dragDropListState, overscrollJob),
        state = dragDropListState.getLazyListState()
    ) {
        itemsIndexed(items) { index, item ->
            val displacementOffset = if (index == dragDropListState.getCurrentIndexOfDraggedListItem()) {
                dragDropListState.elementDisplacement.takeIf { it != 0f }
            } else {
                null
            }
            ListItem(item, displacementOffset)
        }
    }
}

@Composable
private fun ListItem(
    item: String,
    displacementOffset: Float?
) {

    val isBeingDragged = displacementOffset != null
    val backgroundColor = if (isBeingDragged) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSecondary
    }

    Column(
        modifier = Modifier
            .graphicsLayer { translationY = displacementOffset ?: 0f }
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = item, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun rememberDragDropListState(
    lazyListState: LazyListState = rememberLazyListState(),
    onMove: (Int, Int) -> Unit,
): ItemListDragAndDropState {
    return remember { ItemListDragAndDropState(lazyListState, onMove) }
}

class ItemListDragAndDropState(
    private val lazyListState: LazyListState,
    private val onMove: (Int, Int) -> Unit
) {
    private var draggedDistance by mutableStateOf(0f)
    private var initiallyDraggedElement by mutableStateOf<LazyListItemInfo?>(null)
    private var currentIndexOfDraggedItem by mutableStateOf(-1)
    private var overscrollJob by mutableStateOf<Job?>(null)

    private val currentElement: LazyListItemInfo?
        get() = currentIndexOfDraggedItem.let {
            lazyListState.getVisibleItemInfoFor(absoluteIndex = it)
        }

    private val initialOffsets: Pair<Int, Int>?
        get() = initiallyDraggedElement?.let { Pair(it.offset, it.offsetEnd) }

    val elementDisplacement: Float?
        get() = currentIndexOfDraggedItem
            .let { lazyListState.getVisibleItemInfoFor(absoluteIndex = it) }
            ?.let { item ->
                (initiallyDraggedElement?.offset ?: 0f).toFloat() + draggedDistance - item.offset
            }

    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                currentIndexOfDraggedItem = it.index
                initiallyDraggedElement = it
            }
    }

    fun onDragInterrupted() {
        draggedDistance = 0f
        currentIndexOfDraggedItem = -1
        initiallyDraggedElement = null
        overscrollJob?.cancel()
    }

    private fun calculateOffsets(offset: Float): Pair<Float, Float> {
        val startOffset = offset + draggedDistance
        val currentElementSize = currentElement?.size ?: 0
        val endOffset = offset + draggedDistance + currentElementSize
        return startOffset to endOffset
    }

    fun onDrag(offset: Offset) {
        draggedDistance += offset.y
        val topOffset = initialOffsets?.first ?: return
        val (startOffset, endOffset) = calculateOffsets(topOffset.toFloat())

        val hoveredElement = currentElement
        if (hoveredElement != null) {
            val delta = startOffset - hoveredElement.offset
            val isDeltaPositive = delta > 0
            val isEndOffsetGreater = endOffset > hoveredElement.offsetEnd

            val validItems = lazyListState.layoutInfo.visibleItemsInfo.filter { item ->
                !(item.offsetEnd < startOffset || item.offset > endOffset || hoveredElement.index == item.index)
            }

            val targetItem = validItems.firstOrNull {
                when {
                    isDeltaPositive -> isEndOffsetGreater
                    else -> startOffset < it.offset
                }
            }

            if (targetItem != null) {
                currentIndexOfDraggedItem.let { current ->
                    onMove.invoke(current, targetItem.index)
                    currentIndexOfDraggedItem = targetItem.index
                }
            }
        }
    }

    fun checkForOverScroll(): Float {
        val draggedElement = initiallyDraggedElement
        if (draggedElement != null) {
            val (startOffset, endOffset) = calculateOffsets(draggedElement.offset.toFloat())
            val diffToEnd = endOffset - lazyListState.layoutInfo.viewportEndOffset
            val diffToStart = startOffset - lazyListState.layoutInfo.viewportStartOffset
            return when {
                draggedDistance > 0 && diffToEnd > 0 -> diffToEnd
                draggedDistance < 0 && diffToStart < 0 -> diffToStart
                else -> 0f
            }
        }
        return 0f
    }

    fun getLazyListState(): LazyListState {
        return lazyListState
    }

    fun getCurrentIndexOfDraggedListItem(): Int {
        return currentIndexOfDraggedItem
    }
}

fun LazyListState.getVisibleItemInfoFor(absoluteIndex: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(
        absoluteIndex - this.layoutInfo.visibleItemsInfo.first().index
    )
}

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

fun <T> MutableList<T>.move(
    from: Int,
    to: Int
) {
    if (from == to)
        return
    val element = this.removeAt(from) ?: return
    this.add(to, element)
}

private fun handleOverscrollJob(
    overscrollJob: MutableState<Job?>,
    scope: CoroutineScope,
    itemListDragAndDropState: ItemListDragAndDropState
) {
    if (overscrollJob.value?.isActive == true) return
    val overscrollOffset = itemListDragAndDropState.checkForOverScroll()
    if (overscrollOffset != 0f) {
        overscrollJob.value = scope.launch {
            itemListDragAndDropState.getLazyListState().scrollBy(overscrollOffset)
        }
    } else {
        overscrollJob.value?.cancel()
    }
}

fun Modifier.dragGestureHandler(
    scope: CoroutineScope,
    itemListDragAndDropState: ItemListDragAndDropState,
    overscrollJob: MutableState<Job?>
): Modifier = this.pointerInput(Unit) {
    detectDragGesturesAfterLongPress(
        onDrag = { change, offset ->
            change.consume()
            itemListDragAndDropState.onDrag(offset)
            handleOverscrollJob(overscrollJob, scope, itemListDragAndDropState)
        },
        onDragStart = { offset -> itemListDragAndDropState.onDragStart(offset) },
        onDragEnd = { itemListDragAndDropState.onDragInterrupted() },
        onDragCancel = { itemListDragAndDropState.onDragInterrupted() }
    )
}