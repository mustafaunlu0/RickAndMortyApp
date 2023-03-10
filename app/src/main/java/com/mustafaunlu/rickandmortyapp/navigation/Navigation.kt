package com.mustafaunlu.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen"){

        composable("splash_screen"){

        }

        composable("main_screen"){

        }

        composable("detail_screen"){

        }


    }

}