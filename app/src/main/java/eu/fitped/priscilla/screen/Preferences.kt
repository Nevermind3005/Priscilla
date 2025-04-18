package eu.fitped.priscilla.screen

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
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
import eu.fitped.priscilla.components.Avatar
import eu.fitped.priscilla.components.FullHeader
import eu.fitped.priscilla.components.input.LanguageSelect
import eu.fitped.priscilla.model.LanguageGetDto
import eu.fitped.priscilla.model.LanguagePostDto
import eu.fitped.priscilla.model.UserDto
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.PreferencesViewModel

@Composable
fun Preferences(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ) {
    val context = LocalContext.current
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()

    val state by preferencesViewModel.dataState.collectAsState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)),
            ) {
                FullHeader(stringResource(R.string.preferences))
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(padding)
        ) {
            when (state) {
                is DataState.Loading -> Loading()
                is DataState.Success<*> -> {
                    val response = (state as DataState.Success<Pair<List<LanguageGetDto>, UserDto>>).data
                    Column(
                        modifier = modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Avatar("${response.second.name.first().uppercaseChar()}${response.second.surname.first().uppercaseChar()}")
                            }
                            Column {
                                Text(
                                    "${response.second.name} ${response.second.surname}",
                                    style = MaterialTheme.typography.headlineLarge,
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    "${response.second.performance.xp} ${stringResource(R.string.xp)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(2.dp)
                                )
                                Text(
                                    "${response.second.performance.coins} ${stringResource(R.string.coins)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(2.dp)
                                )
                                Text(
                                    "${response.second.performance.level}. ${stringResource(R.string.level)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = stringResource(R.string.language),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LanguageSelect(
                                languages = response.first,
                                currentLanguageId = response.second.langId,
                                modifier = Modifier.padding(16.dp),
                                onLanguageItemClick = {preferencesViewModel.setUserLanguage(LanguagePostDto(it.shortcut))}
                            )
                        }
                    }
                }
                is DataState.Error -> {
                    Text("Error: ${(state as DataState.Error).message}")
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            onClick = {
                preferencesViewModel.logout()
                navController.navigate(NavigationItem.Login.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            },
            text = {
                Text(text = stringResource(R.string.logout))
            },
            icon = {
                Icon(painter = painterResource(id = R.drawable.logout_24px), contentDescription = null)
            },
            modifier = Modifier
                .offset(x = (-16).dp, y = (-16).dp)
                .align(Alignment.BottomEnd),
        )
        Text(modifier = Modifier.align(Alignment.BottomStart), text = getAppVersion(context = context))
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