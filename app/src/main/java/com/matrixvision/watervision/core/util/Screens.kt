package com.matrixvision.watervision.core.util

sealed class Screens(val route: String){
    object SplashScreen: Screens(route = "splash_screen")
    object LoginScreen: Screens(route = "login_screen")
    object RegisterScreen: Screens(route = "register_screen")
}
