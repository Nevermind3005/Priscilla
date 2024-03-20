package eu.fitped.priscilla.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.CourseDto
import eu.fitped.priscilla.navigation.NavigationItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseList(
    modifier: Modifier = Modifier,
    courseList: List<CourseDto>,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    val isCompactHeader = remember { derivedStateOf { listState.firstVisibleItemScrollOffset > 5 || listState.firstVisibleItemIndex > 0 } }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)),
            ) {
                AnimatedVisibility(visible = !isCompactHeader.value) {
                    FullHeader(text = "Home")
                }
                AnimatedVisibility(visible = isCompactHeader.value) {
                    CompactHeader(text = "Home")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(padding)
                .fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(
                count = courseList.size,
                key = {
                    courseList[it].courseId
                },
                itemContent = { index ->
                    CourseCard(
                        courseDto = courseList[index],
                        onClick = {
                            navController.navigate("COURSE_DETAIL/${courseList[index].courseId}")
                        }
                    )
                }
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun CourseListPreview() {
    val courseDto = CourseDto(
        "#EF5350",
        1,
        "Java - základy jazyka",
        "Základné údajové a programové štruktúry jazyka",
        "5278",
        "9590",
        "535",
        609,
        "170",
        "245",
        "194",
        "170",
        "171",
        "194",
        "1934",
        "3344",
        "1940",
        "7650",
        88
    )
    val courses = listOf<CourseDto>(courseDto, courseDto, courseDto)
    CourseList(courseList = courses, navController = rememberNavController())
}

@Composable
fun FullHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun CompactHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}