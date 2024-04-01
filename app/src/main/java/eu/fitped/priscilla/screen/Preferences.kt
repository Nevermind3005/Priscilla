package eu.fitped.priscilla.screen

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.viewModel.LoginViewModel
import eu.fitped.priscilla.viewModel.PreferencesViewModel

@Composable
fun Preferences(
    navController: NavHostController,
    ) {
    val context = LocalContext.current
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.collectAsState()
    Column {
        Text(text = "Preferences")
        Button(onClick = {
            preferencesViewModel.logout()
            navController.popBackStack()
            navController.navigate(NavigationItem.Login.route)
//            loginViewModel.logOut()
        }) {
            Text(text = "Logout",)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.BottomEnd), text = getAppVersion(context = context))
    }
}

@Composable
fun getAppVersion(context: Context): String {
    val packageInfo = try {
        context.packageManager.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        return "Unknown"
    }

    return packageInfo.versionName
}