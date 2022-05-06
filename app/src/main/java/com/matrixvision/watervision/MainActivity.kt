package com.matrixvision.watervision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.matrixvision.watervision.core.presentation.component.Navigation

import com.matrixvision.watervision.ui.theme.WaterVisionTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterVisionTheme() {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                    ) {

                    val navHostController = rememberNavController()
                    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                    Navigation(navController = navHostController, imageLoader = imageLoader)
                    
                }


            }
        }
    }
}

