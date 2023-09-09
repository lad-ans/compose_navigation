package io.ladans.composenavigation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.ladans.composenavigation.screens.CustomScreen
import io.ladans.composenavigation.screens.HomeScreen
import io.ladans.composenavigation.screens.LoginScreen
import io.ladans.composenavigation.ui.theme.ComposeNavigationTheme
import io.ladans.composenavigation.viewmodels.SharedViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "auth",
                ) {
//                    composable("login") {
//                        LoginScreen2(navController)
//                    }
//                    composable(
//                        "home?email={email}&password={password}",
//                        listOf(
//                            navArgument("email") {
//                                type = NavType.StringType
//                                defaultValue = ""
//                            },
//                            navArgument("password") {
//                                type = NavType.StringType
//                                defaultValue = ""
//                            }
//                        )
//                    ) {
//                        val email = it.arguments?.getString("email") ?: ""
//                        val password = it.arguments?.getString("password") ?: ""
//
//                        HomeScreen(
//                            loginData = DataLogin(email = email, password = password),
//                            navController = navController,
//                        )
//                    }

//                    Sub graps - sub navigations
                    navigation(
                        startDestination = "login",
                        route = "auth",
                    ) {
                        composable("login") {
                            val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                            LoginScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("custom") {
                            CustomScreen(navController)
                        }
                        composable("home") {
                            val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                            val state by viewModel.sharedState.collectAsState()

                            HomeScreen(loginData = state, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(parentEntry)
}