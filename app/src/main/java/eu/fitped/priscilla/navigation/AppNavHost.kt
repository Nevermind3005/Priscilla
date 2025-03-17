package eu.fitped.priscilla.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import eu.fitped.priscilla.UserState
import eu.fitped.priscilla.components.LessonTasks
import eu.fitped.priscilla.screen.ChapterDetail
import eu.fitped.priscilla.screen.CourseCategoryDetail
import eu.fitped.priscilla.screen.CourseDetail
import eu.fitped.priscilla.screen.Home
import eu.fitped.priscilla.screen.Login

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    val vm = UserState.current
    println(vm.isLoggedIn)
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavigationItem.Login.route) {
            Login(navController = navController)
        }
        composable(NavigationItem.Home.route) {
            Home(navController = navController)
        }
        composable(
            NavigationItem.CourseDetail.route,
            arguments = listOf(navArgument("courseId") { type = NavType.StringType })
        ) { backStackEntry ->
            CourseDetail(
                courseId =  backStackEntry.arguments?.getString("courseId"),
                navController = navController
            )
        }
        composable(
            NavigationItem.ChapterDetail.route,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType })
        ) { backStackEntry ->
            ChapterDetail(
                chapterId =  backStackEntry.arguments?.getString("chapterId"),
                navController = navController
            )
        }
        composable(
            NavigationItem.LessonTasks.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType },
                navArgument("chapterId") { type = NavType.StringType },
                navArgument("lessonId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            LessonTasks(
                courseId = backStackEntry.arguments?.getString("courseId"),
                chapterId = backStackEntry.arguments?.getString("chapterId"),
                lessonId = backStackEntry.arguments?.getString("lessonId"),
                navController = navController
            )
        }
        composable(
            NavigationItem.CourseCategoryAreas.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            CourseCategoryDetail(
                categoryId = backStackEntry.arguments?.getString("categoryId"),
                navController = navController
            )
        }
    }
}