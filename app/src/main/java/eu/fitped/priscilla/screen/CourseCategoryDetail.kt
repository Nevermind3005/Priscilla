package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.core.GenericList
import eu.fitped.priscilla.components.core.SimpleCard
import eu.fitped.priscilla.model.CourseCategoryAreasDto
import eu.fitped.priscilla.navigation.Screen
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.CourseCategoryDetailViewModel

@Composable
fun CourseCategoryDetail(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categoryId: String?
) {
    val courseCategoryDetail: CourseCategoryDetailViewModel = hiltViewModel()
    val state by courseCategoryDetail.dataState.collectAsState()

    if (categoryId != null)
    {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> ->  {
                val data = (state as DataState.Success<CourseCategoryAreasDto>).data
                GenericList(
                    headerText = data.category.name,
                    items = data.areas,
                    itemKey = { it.id }
                ) {
                    SimpleCard(
                        cardText = it.areaName,
                        onClick = {
                            navController.navigate("${Screen.AREA_DETAIL.name}/${it.id}")
                        }
                    )
                }
            }
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}