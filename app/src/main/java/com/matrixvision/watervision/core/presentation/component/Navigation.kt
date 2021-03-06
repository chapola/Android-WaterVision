package com.matrixvision.watervision.core.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.matrixvision.watervision.core.util.Screens
import com.matrixvision.watervision.feature_auth.presentation.login.LoginScreen
import com.matrixvision.watervision.feature_auth.presentation.register.RegisterScreen
import com.matrixvision.watervision.feature_auth.presentation.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
){

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ){
        composable(Screens.SplashScreen.route){
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
            )

        }
        composable(Screens.RegisterScreen.route){
            RegisterScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                onPopBackStack = navController::popBackStack
            )
        }

        composable(Screens.LoginScreen.route){
            LoginScreen(
                onNavigate = navController::navigate,
               onLogin = {
                   navController.popBackStack(
                       route = Screens.LoginScreen.route,
                       inclusive = true
                   )
                   navController.navigate(route = Screens.RegisterScreen.route)
               },
                scaffoldState = scaffoldState
            )
        }
    }

}