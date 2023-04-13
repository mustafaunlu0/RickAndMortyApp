package com.mustafaunlu.rickandmortyapp.screen


import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.*
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mustafaunlu.rickandmortyapp.R
import com.mustafaunlu.rickandmortyapp.data.model.character.Character
import com.mustafaunlu.rickandmortyapp.data.model.locations.Result
import com.mustafaunlu.rickandmortyapp.screen.destinations.DetailScreenDestination
import com.mustafaunlu.rickandmortyapp.ui.theme.*
import com.mustafaunlu.rickandmortyapp.ui.main.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "MutableCollectionMutableState",
    "UnrememberedMutableState"
)
@Composable
@Destination
fun MainScreen(
    navigator: DestinationsNavigator, mainViewModel: MainViewModel = hiltViewModel()
) {

    mainViewModel.setNotFirstTime()




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
                backgroundColor = Color(0xFFF5D95B),
                elevation = 2.dp,
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor)
        ) {
            item {
                DescriptionPart()
                LocationList()
                CharacterList(
                    navigator = navigator,
                )

            }

        }

    }


}


@Composable
fun LocationList(
    modifier: Modifier = Modifier,
    buttonBackgroundColor: Color = DarkerButtonBlue,
    buttonContentColor: Color = TextWhite,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    var backColor by remember {
        mutableStateOf(buttonBackgroundColor)
    }
    var selectedLocIndex by remember {
        mutableStateOf(0)
    }
    var isFirst by remember {
        mutableStateOf(true)
    }

    val ids: ArrayList<String> = arrayListOf()

    val locationsForPaging = mainViewModel.getLocations().collectAsLazyPagingItems()


    LazyRow(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        if (locationsForPaging.loadState.refresh == LoadState.Loading && locationsForPaging.itemCount == 0) {






            items(5) {
                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = backColor,
                        contentColor = buttonContentColor
                    ),
                    border = BorderStroke(
                        width = 1.dp, color = TextWhite
                    )

                ) {
                    Text(
                        text = "Laoding", fontFamily = FontFamily(
                            Font(R.font.avenir)
                        )
                    )
                }

            }


        }

        itemsIndexed(locationsForPaging) { index, element ->
            //Bringing the chars of location id 1 by default for the first load
            if(isFirst){

                mainViewModel.uploadData(locationsForPaging.itemSnapshotList.items as MutableList<Result>,ids,0)
                mainViewModel.fetchPersons(mainViewModel.convertToString(ids))
                isFirst=false
            }

            Button(
                onClick = {

                    selectedLocIndex = index

                    ids.clear()

                    mainViewModel.uploadData(
                        locationsForPaging.itemSnapshotList.items as MutableList<Result>,
                        ids,
                        index
                    )
                    mainViewModel.fetchPersons(mainViewModel.convertToString(ids))



                },
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectedLocIndex == index) SecondColor else backColor,
                    contentColor = buttonContentColor
                ),
                border = BorderStroke(
                    width = 1.dp, color = TextWhite
                )

            ) {
                Text(
                    text = locationsForPaging.itemSnapshotList[index]!!.name,
                    fontFamily = FontFamily(
                        Font(R.font.avenir)
                    )
                )
            }


        }

        if (locationsForPaging.loadState.append == LoadState.Loading) {
            item {

                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(0.3f),
                    color = Color.Green
                )


            }
        }
    }


}


@Composable
fun DescriptionPart() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .weight(3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = SecondColor,
                            fontSize = 40.sp,
                        )
                    ) {
                        append("M")
                    }
                    append("aceraya ")
                    withStyle(
                        style = SpanStyle(
                            color = SecondColor, fontSize = 40.sp
                        )
                    ) {
                        append("K")
                    }
                    append("atıl")
                },
                color = TextWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                fontFamily = FontFamily(Font(R.font.get_schwifty))
            )
            Text(
                text = "Karakterleri tanımaya başla!",
                color = DarkerButtonBlue,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
                fontFamily = FontFamily(
                    Font(R.font.avenir)
                )
            )

        }
        MortyAnimation(modifier = Modifier.weight(1f))
    }


}


@Composable
fun CharacterList(
    modifier: Modifier = Modifier, navigator: DestinationsNavigator,
    mainViewModel: MainViewModel = hiltViewModel(),

    ) {
    val persons by mainViewModel.getPersonData().observeAsState()

    val screenHeight = LocalConfiguration.current.screenHeightDp


    LazyVerticalGrid(
        columns = GridCells.Fixed(1), contentPadding = PaddingValues(


            start = 7.5.dp, end = 7.5.dp, bottom = 20.dp
        ), modifier = modifier.height((screenHeight * 0.7).dp)
    ) {
        if (persons != null) {
            items(persons!!.size) {
                CharacterItem(navigator, persons!![it])
            }
        } else {
            items(5) {
                ShimmerCharacter()
            }
        }

    }
}

@Composable
fun CharacterItem(
    navigator: DestinationsNavigator,
    person: Character,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    var episode by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(specifyColor(person.gender))
            .clickable {
                person.episode.forEachIndexed { index, item ->
                    episode += if (index == person.episode.size - 1) {
                        mainViewModel.findEpisode(item)
                    } else {
                        mainViewModel.findEpisode(item) + ", "
                    }
                }
                navigator.navigate(
                    DetailScreenDestination(
                        name = person.name,
                        status = person.status,
                        specy = person.species,
                        gender = person.gender,
                        origin = person.origin.name,
                        location = person.location.name,
                        episodes = episode,
                        apiDate = person.created,
                        imageUrl = person.image
                    )
                )
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            when (person.gender) {
                "Female" -> {
                    CharacterImage(
                        character = person, modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    CharacterName(
                        character = person, modifier = Modifier
                            .align(CenterVertically)
                            .weight(1f)
                    )
                    GenderIcon(person.gender, modifier = Modifier.align(Top))
                }
                else -> {
                    GenderIcon(person.gender, modifier = Modifier.align(Top))
                    CharacterName(
                        character = person, modifier = Modifier
                            .align(CenterVertically)
                            .weight(1f)
                    )
                    CharacterImage(character = person, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

fun specifyColor(gender: String): Color {
    return when (gender) {
        "Female" -> FemaleColor
        "Male" -> MaleColor
        else -> UnknownGenderColor
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterImage(
    character: Character, modifier: Modifier = Modifier
) {
    GlideImage(
        model = character.image,
        contentDescription = character.name,
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun CharacterName(
    character: Character, modifier: Modifier = Modifier
) {
    Text(
        text = character.name,
        color = Color.White,
        modifier = modifier.padding(horizontal = 10.dp),
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.avenir))
    )
}

@Composable
fun GenderIcon(
    gender: String, modifier: Modifier = Modifier
) {
    Icon(
        when (gender) {
            "Male" -> painterResource(id = R.drawable.ic_male)
            "Female" -> painterResource(id = R.drawable.ic_female)
            else -> painterResource(id = R.drawable.ic_circle_unknown)
        }, contentDescription = "gender", tint = Color.White, modifier = modifier.padding(10.dp)
    )
}


@Composable
fun MortyAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://assets1.lottiefiles.com/packages/lf20_qqtavvc0.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition, progress = { progress }, modifier = modifier.height(100.dp)
    )

}


@Composable
fun ShimmerCharacter(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .shimmerEffect()

    )
}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                TopBar,
                MainColor,
                DarkerButtonBlue,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}
