package com.mustafaunlu.rickandmortyapp.screen


import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mustafaunlu.rickandmortyapp.R
import com.mustafaunlu.rickandmortyapp.screen.destinations.MainScreenDestination
import com.mustafaunlu.rickandmortyapp.ui.theme.*
import com.mustafaunlu.rickandmortyapp.ui.detail.DetailViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun DetailScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    name: String,
    status: String,
    specy: String,
    gender: String,
    origin: String,
    location: String,
    episodes: String,
    apiDate: String,
    imageUrl: String,
) {

    val color = remember { Animatable(MainColor) }
    LaunchedEffect(Unit) {
        color.animateTo(Color(0xFFF5D95B), animationSpec = tween(200))
        color.animateTo(MainColor, animationSpec = tween(3000))
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = name,
                            color = SecondColor,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.avenir))
                        )
                    }

                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = SecondColor,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(25.dp)
                            .clickable {
                                navigator.navigate(MainScreenDestination())
                            }
                    )
                },

                backgroundColor = TopBar,
                elevation = 0.dp

            )
        }

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color.value)

        ) {
            item {
                Spacer(modifier = modifier.height(20.dp))
                CharImageSection(imageUrl = imageUrl)
                CharInfoSection(
                    status = status,
                    specy = specy,
                    gender = gender,
                    origin = origin,
                    location = location,
                    episodes = episodes,
                    apiDate = apiDate
                )
            }
        }
    }
}

@Composable
fun CharInfoSection(
    modifier: Modifier = Modifier,
    status: String,
    specy: String,
    gender: String,
    origin: String,
    location: String,
    episodes: String,
    apiDate: String,
    detailViewModel: DetailViewModel = viewModel()
    ) {



    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoSection(info = "Status", value = status)
        InfoSection(info = "Specy", value = specy)
        InfoSection(info = "Gender", value = gender)
        InfoSection(info = "Origin", value = origin)
        InfoSection(info = "Location", value = location)
        InfoSection(info = "Episodes", value = episodes)
        InfoSection(info = "Created at (in API)", value = detailViewModel.dateConvert(apiDate))

    }

}

@Composable
fun InfoSection(
    modifier: Modifier = Modifier,
    info: String,
    value: String
) {
    Row(
        modifier
            .padding(horizontal = 20.dp,vertical = 5.dp)
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically, content = {
            Text(
                text = "$info:",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.avenir)),
                modifier = Modifier
                    .padding(horizontal = 20.dp,vertical = 5.dp)
                    .weight(1.4f)
            )

            Text(
                text = value,
                color = SecondColor,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.avenir)),
                modifier = Modifier
                    .padding(horizontal = 20.dp,vertical = 5.dp)
                    .weight(2f)

            )


        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String
) {

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(275.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray),

            ) {

            GlideImage(
                model = imageUrl,
                contentDescription = "diane",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,

                )


        }

    }

}
