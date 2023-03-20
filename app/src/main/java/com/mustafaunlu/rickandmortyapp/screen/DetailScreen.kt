package com.mustafaunlu.rickandmortyapp.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import com.mustafaunlu.rickandmortyapp.R
import com.mustafaunlu.rickandmortyapp.screen.destinations.MainScreenDestination
import com.mustafaunlu.rickandmortyapp.ui.theme.MainColor
import com.mustafaunlu.rickandmortyapp.ui.theme.SecondColor
import com.mustafaunlu.rickandmortyapp.ui.theme.TextWhite
import com.mustafaunlu.rickandmortyapp.ui.theme.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    name: String = "Beth Smith",
    navigator: DestinationsNavigator
) {

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
                            fontSize = 22.sp
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
                .background(MainColor)

        ) {
            item {
                Spacer(modifier = modifier.height(20.dp))
                CharImageSection()
                Spacer(modifier = modifier.height(20.dp))
                CharInfoSection()

            }

        }


    }

}

@Composable
fun CharInfoSection(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoSection(info = "Status", value = "Alive")
        InfoSection(info = "Specy", value = "Human")
        InfoSection(info = "Gender", value = "Female")
        InfoSection(info = "Origin", value = "Earth (C-137)")
        InfoSection(info = "Location", value = "Earth (C-137)")
        InfoSection(info = "Episodes", value = "1, 2, 3, 4, 6, 22, 51")
        InfoSection(info = "Created at (in API)", value = "5 May 2017, 09:46:44")

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
            .padding(start = 20.dp)
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically, content = {
            Text(
                text = "$info:",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.firefans)),
                modifier = Modifier
                    .padding(start = 30.dp)
                    .weight(1f)
            )

            Text(
                text = value,
                color = SecondColor,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(2f)

            )


        }
    )
}

//character yollanacak
@Composable
fun CharImageSection(
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(250.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray),

            ) {

            Image(
                painter = painterResource(id = R.drawable.diane),
                contentDescription = "diane",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,

                )


        }

    }

}
