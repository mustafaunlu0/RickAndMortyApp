package com.mustafaunlu.rickandmortyapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafaunlu.rickandmortyapp.constants.Constants
import com.mustafaunlu.rickandmortyapp.screen.SplashScreen


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Constants.SPLASH_SCREEN){

        composable(Constants.SPLASH_SCREEN){
            SplashScreen(navController = navController)
        }

        composable(Constants.MAIN_SCREEN){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = "Main  Screen", color = Color.Black)
            }
        }

        composable(Constants.DETAIL_SCREEN){

        }


    }

}