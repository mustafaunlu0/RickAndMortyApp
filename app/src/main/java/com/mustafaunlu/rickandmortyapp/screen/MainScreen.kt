package com.mustafaunlu.rickandmortyapp.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mustafaunlu.rickandmortyapp.R
import com.mustafaunlu.rickandmortyapp.model.Character
import com.mustafaunlu.rickandmortyapp.model.character.Person
import com.mustafaunlu.rickandmortyapp.model.character.PersonItem
import com.mustafaunlu.rickandmortyapp.model.locations.Result
import com.mustafaunlu.rickandmortyapp.screen.destinations.DetailScreenDestination
import com.mustafaunlu.rickandmortyapp.ui.theme.*
import com.mustafaunlu.rickandmortyapp.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter",
    "MutableCollectionMutableState", "UnrememberedMutableState"
)
@Composable
@Destination
fun MainScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()


) {

    var locations: MutableList<Result> by mutableStateOf(mutableListOf())
    val ids : MutableList<String> by mutableStateOf(mutableListOf())

    val persons  by homeViewModel.getPersonData().observeAsState()
    val data = homeViewModel.getLocationData().observeAsState()

    var id by remember{
        mutableStateOf("")
    }


    LaunchedEffect(Unit){
        homeViewModel.loadLocations()

    }

    if(data.value != null){
        locations= data.value!!.results as MutableList<Result>
        homeViewModel.uploadData(locations,ids,0)
        LaunchedEffect(Unit){
            homeViewModel.fetchPersons(homeViewModel.convertToString(ids))

        }
    }
    LaunchedEffect(id){
        homeViewModel.fetchPersons(id)

    }


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
                LocationList(
                    locations = locations,
                    ids = ids as ArrayList<String>,
                    ){
                    id=it

                }
                CharacterList(
                    persons = persons,
                    navigator = navigator,
                )

            }

        }


    }

}
@SuppressLint("MutableCollectionMutableState")
@Composable
fun LocationList(
    modifier: Modifier = Modifier,
    locations: List<Result>,
    ids: ArrayList<String>,
    buttonBackgroundColor: Color = DarkerButtonBlue,
    buttonContentColor: Color = TextWhite,
    homeViewModel: HomeViewModel = hiltViewModel(),
    idChanged : (String) -> Unit,

) {

    var backColor by remember {
        mutableStateOf(buttonBackgroundColor)
    }
    var selectedLocIndex by remember {
        mutableStateOf(0)
    }


    LazyRow(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        items(locations.size) {


            Button(
                onClick = {
                    selectedLocIndex = it
                    ids.clear()
                    homeViewModel.uploadData(locations as MutableList<Result>,ids,it)
                    idChanged(homeViewModel.convertToString(ids))


                },
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectedLocIndex == it) SecondColor else backColor,
                    contentColor = buttonContentColor
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = TextWhite
                )

            ) {
                Text(
                    text = locations[it].name,
                    fontFamily = FontFamily(
                        Font(R.font.firefans)
                    )
                )
            }
        }
    }


}


@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    persons :ArrayList<PersonItem>?
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 7.5.dp,
            end = 7.5.dp
        ),
        modifier = modifier.height(500.dp)
    ) {
        if(persons != null){
            items(persons.size) {
                CharacterItem( navigator,persons[it])

            }
        }

    }


}


@Composable
fun CharacterItem(
    navigator: DestinationsNavigator,
    person: PersonItem,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    var episode by remember{
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
                        homeViewModel.findEpisode(item)
                    } else {
                        homeViewModel.findEpisode(item) + ", "
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

        )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),

            ) {
            when (person.gender) {
                "Female" -> {
                    CharacterImage(character = person, modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f))
                    CharacterName(
                        character = person,
                        modifier = Modifier
                            .align(CenterVertically)
                            .weight(1f)
                    )
                    GenderIcon(person.gender, modifier = Modifier.align(Top))
                }
                else -> {
                    GenderIcon(person.gender, modifier = Modifier.align(Top))
                    CharacterName(
                        character = person,
                        modifier = Modifier
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
    character: PersonItem,
    modifier: Modifier = Modifier
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
    character: PersonItem,
    modifier: Modifier = Modifier
) {
    Text(
        text = character.name,
        color = Color.White,
        modifier = modifier
            .padding(horizontal = 10.dp),
        textAlign = TextAlign.Center,
        fontSize = 26.sp,
        fontFamily = FontFamily(Font(R.font.firefans))
    )
}

@Composable
fun GenderIcon(
    gender: String,
    modifier: Modifier = Modifier
) {
    Icon(
        when (gender) {
            "Male" -> painterResource(id = R.drawable.ic_male)
            "Female" -> painterResource(id = R.drawable.ic_female)
            else -> painterResource(id = R.drawable.ic_unknown)
        },
        contentDescription = "gender",
        tint = Color.White,
        modifier = modifier.padding(10.dp)
    )
}



@Composable
fun MortyAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://assets1.lottiefiles.com/packages/lf20_qqtavvc0.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.height(100.dp)
    )

}


//Textte kaldık
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
                            color = SecondColor,
                            fontSize = 40.sp
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
                    Font(R.font.firefans)
                )
            )

        }

        MortyAnimation(modifier = Modifier.weight(1f))
    }


}
