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

@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState",
    "MutableCollectionMutableState"
)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()


) {

    var locations: MutableList<Result> by mutableStateOf(mutableListOf())
    var persons: MutableList<Person> by mutableStateOf(mutableListOf())

    var ids : MutableList<String> by mutableStateOf(mutableListOf())



    homeViewModel.loadLocations()

    val data = homeViewModel.getLocationData().observeAsState()

    if(data.value != null){
        locations= data.value!!.results as MutableList<Result>
        locations.forEach{
            it.residents.forEach { url ->
                ids.add(findId(url))
            }
        }

    }
    homeViewModel.loadPersons(convertToString(ids))
    //İdleri temiz bir şekilde aldık sıra aşağıda diğer verileri çekmemeye uğraşıyoruz
    val charData=homeViewModel.getPerson().observeAsState()
    if(charData.value != null){
        //personların verisi çekilecek burada kaldık
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
                //philipp lackner videosu
                SearchBar(state = "deneme", modifier.padding(horizontal = 20.dp, vertical = 15.dp))
                //indexi almak için remember değişkeni göndericez
                LocationList(
                    locations = locations
                    /*listOf(
                        "İstanbul",
                        "Ankara",
                        "İzmir",
                        "Bursa",
                        "Diyarbakır",
                        "Mardin",
                        "Trabzon",
                        "Mersin"
                    )
                    */
                )
                CharacterList(
                    characters = listOf(
                        Character(
                            profileImage = painterResource(id = R.drawable.rick),
                            name = "Rick",
                            gender = "Male"
                        ),
                        Character(
                            profileImage = painterResource(id = R.drawable.diane),
                            name = "Diane",
                            gender = "Female"
                        ),
                        Character(
                            profileImage = painterResource(id = R.drawable.unkown),
                            name = "Doctor Bob",
                            gender = "Unknown"
                        ),
                        Character(
                            profileImage = painterResource(id = R.drawable.morty),
                            name = "Morty",
                            gender = "Male",

                            )
                    ),
                    navigator = navigator
                )

            }

        }


    }


}

fun findId(url : String) : String{
   return  url.substring((url.indexOf("character")+10),url.length)
}
fun convertToString(ids : MutableList<String>): String {
    val string = ids.joinToString()
    return string.replace(" ","")
}
@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 7.5.dp,
            end = 7.5.dp
        ),
        modifier = modifier.height(500.dp)
    ) {
        items(characters.size) {
            CharacterItem(character = characters[it], navigator)


        }
    }


}


@Composable
fun CharacterItem(
    character: Character,
    navigator: DestinationsNavigator
) {


    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(specifyColor(character.gender))
            .clickable {
                navigator.navigate(DetailScreenDestination(name = character.name))
            },

        )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),

        ) {
            when (character.gender) {
                "Female" -> {
                    CharacterImage(character = character, modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f))
                    CharacterName(
                        character = character,
                        modifier = Modifier
                            .align(CenterVertically)
                            .weight(1f)
                    )
                    GenderIcon(character.gender, modifier = Modifier.align(Top))
                }
                else -> {
                    GenderIcon(character.gender, modifier = Modifier.align(Top))
                    CharacterName(
                        character = character,
                        modifier = Modifier
                            .align(CenterVertically)
                            .weight(1f)
                    )
                    CharacterImage(character = character, modifier = Modifier.weight(1f))
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

@Composable
fun CharacterImage(
    character: Character,
    modifier: Modifier = Modifier
) {
    Image(
        painter = character.profileImage,
        contentDescription = character.name,
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun CharacterName(
    character: Character,
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
fun LocationList(
    modifier: Modifier = Modifier,
    locations: List<Result>,
    buttonBackgroundColor: Color = DarkerButtonBlue,
    buttonContentColor: Color = TextWhite,
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
                println(locations[it].name)
                    selectedLocIndex = it
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


// philipp lackner video
@Composable
fun SearchBar(state: String, modifier: Modifier = Modifier) {
    var deneme by remember {
        mutableStateOf(TextFieldValue("deneme"))
    }
    TextField(
        value = state,
        onValueChange = {
            /*value ->
        var deneme : String = value.text

             */

        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.firefans))
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state != "") {
                IconButton(onClick = {
                    /*
                    state.value = TextFieldValue("")

                     */
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = SecondColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
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
                .padding(30.dp)
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
