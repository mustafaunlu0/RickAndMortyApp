package com.mustafaunlu.rickandmortyapp.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.mustafaunlu.rickandmortyapp.R


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,

    ) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ram4),
                            contentDescription = null,

                            )
                    }

                },
                backgroundColor = Color.White,
                elevation = 2.dp,
                )
        },


    ) {
        Column(
            modifier = Modifier.fillMaxSize()

        ) {
            DescriptionPart()
            /*
            LocationList(
                locations = listOf(
                    "İstanbul",
                    "Ankara",
                    "İzmir",
                    "Bursa",
                    "Diyarbakır",
                    "Mardin",
                    "Trabzon",
                    "Mersin"
                )
            )

             */
        }


    }


}


@Composable
fun MortyAnimation(
    modifier : Modifier = Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://assets1.lottiefiles.com/packages/lf20_qqtavvc0.json"))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(composition = composition  , progress ={progress}, modifier = modifier.size(100.dp) )

}


//Textte kaldık
@Composable
fun DescriptionPart() {
    var color = Color(0xFFFB8C00)

    Row(modifier=Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
        ) {

        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 40.sp,
                    )
                ){
                    append("M")
                }
                append("aceraya ")
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 40.sp
                    )
                ){
                    append("K")
                }
                append("atıl")

            }, color = Color.Black, fontSize = 25.sp, fontWeight = FontWeight.Bold,letterSpacing = 1.5.sp,
            )
            Text(
                text = "Karakterleri tanımaya başla!",
                color = Color.LightGray,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )

        }



        MortyAnimation()
    }
    



}

@Composable
fun LocationList(
    modifier: Modifier = Modifier,
    locations: List<String>,
    buttonBackgroundColor: Color = Color.LightGray,
    buttonContentColor: Color = Color.Black
) {

    var backColor by remember {
        mutableStateOf(buttonBackgroundColor)
    }
    var selectedLocIndex by remember {
        mutableStateOf(0)
    }

    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items(locations.size) {


            Button(
                onClick = {
                    selectedLocIndex = it
                },
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectedLocIndex == it) Color.White else backColor,
                    contentColor = buttonContentColor
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black
                )

            ) {
                Text(text = locations[it])
            }
        }
    }


}


@Composable
fun CharacterList() {

}