package eu.fitped.priscilla.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.components.core.GenericList
import eu.fitped.priscilla.components.core.SimpleCard
import eu.fitped.priscilla.model.CoursePreviewDto
import eu.fitped.priscilla.model.EnrollCourseResDto
import eu.fitped.priscilla.navigation.Screen
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.util.DataStateTaskEval
import eu.fitped.priscilla.viewModel.CoursePreviewViewModel

@Composable
fun CoursePreview(
    navController: NavHostController,
    courseId: String?
) {
    val coursePreviewViewModel: CoursePreviewViewModel = hiltViewModel()
    val state by coursePreviewViewModel.dataState.collectAsState()
    val enrollState by coursePreviewViewModel.enrollDataState.collectAsState();

    val context = LocalContext.current

    when (enrollState) {
        is DataStateTaskEval.Success<*> -> {
            val data = (enrollState as DataStateTaskEval.Success<EnrollCourseResDto>).data
            navController.navigate("${Screen.COURSE_DETAIL.name}/${data.courseId}") {
                popUpTo(Screen.HOME.name) {
                    inclusive = false
                }
            }
        }
        is DataStateTaskEval.Error -> {
            Toast.makeText(
                context,
                "Error: ${(enrollState as DataStateTaskEval.Error).message}",
                Toast.LENGTH_LONG
            ).show()
            coursePreviewViewModel.resetEnrollState()
        }
        DataStateTaskEval.Idle -> {
        }
        DataStateTaskEval.Loading -> {
        }
    }

    if (courseId != null) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> {
                val data = (state as DataState.Success<CoursePreviewDto>).data
                GenericList(
                    headerText = data.title,
                    items = data.chapterList,
                    itemKey = { it.chapterId }
                ) {
                    SimpleCard(
                        cardText = it.name,
                        disabled = false,
                        onClick = {
                        }
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            coursePreviewViewModel.postEnrollCourse()
                        },
                        text = {
                            Text(text = stringResource(R.string.enroll))
                        },
                        icon = {
                            Icon(painter = painterResource(id = R.drawable.app_registration_24px), contentDescription = null)
                        },
                        modifier = Modifier
                            .offset(x = (-16).dp, y = (-16).dp)
                            .align(Alignment.BottomEnd),
                    )
                }
            }
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}