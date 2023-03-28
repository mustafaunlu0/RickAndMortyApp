package com.mustafaunlu.rickandmortyapp.screen


import android.view.animation.OvershootInterpolator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mustafaunlu.rickandmortyapp.R
import com.mustafaunlu.rickandmortyapp.screen.destinations.MainScreenDestination
import com.mustafaunlu.rickandmortyapp.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Composable
@Destination(start = true)
fun SplashScreen(navController: NavController,
    navigator : DestinationsNavigator,
    homeViewModel: HomeViewModel= hiltViewModel()
                 ) {

    //first Entry
    val isFirst = remember{
        mutableStateOf(true)
    }

    // Entry Animation
    val scale = remember {
        Animatable(0f)
    }
    homeViewModel.isFirstTime {
        isFirst.value=it
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )

        delay(100L)
        navigator.navigate(MainScreenDestination())





    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ram),
            contentDescription = "logo",
            modifier = Modifier
                .padding(bottom = 50.dp)
                .width(300.dp)
                .height(100.dp)
                .scale(scale.value),

            )
        Text(
            text =  if (isFirst.value) "Welcome!" else "Hello!" ,
            fontSize = 27.sp,
            color = Color(0xFF12afc9),
            modifier = Modifier
                .scale(scale.value)
        )
    }


}