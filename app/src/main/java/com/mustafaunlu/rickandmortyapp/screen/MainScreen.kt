package com.mustafaunlu.rickandmortyapp.screen


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.mustafaunlu.rickandmortyapp.R
import com.mustafaunlu.rickandmortyapp.model.Character
import com.mustafaunlu.rickandmortyapp.ui.theme.*


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
                backgroundColor = TopBar,
                elevation = 2.dp,
            )
        },
        ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepBlue)

        ) {
            item {
                DescriptionPart()
                //philipp lackner videosu
                SearchBar(state = "deneme", modifier.padding(horizontal = 20.dp, vertical = 15.dp))
                //indexi almak için remember değişkeni göndericez
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
                            gender = "Male"
                        ),


                        )
                )

            }

        }


    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>
) {


    LazyVerticalGrid(
        cells = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 7.5.dp,
            end = 7.5.dp
        ),
        modifier = modifier.height(500.dp)
    ) {
        items(characters.size) {
            CharacterItem(character = characters[it])


        }
    }


}


@Composable
fun CharacterItem(
    character: Character
) {


    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(specifyColor(character.gender)),

        )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            when (character.gender) {
                "Female" -> {
                    CharacterImage(character = character)
                    CharacterName(
                        character = character,
                        modifier = Modifier.align(CenterVertically)
                    )
                    GenderIcon(character.gender, modifier = Modifier.align(Top))
                }
                else -> {
                    GenderIcon(character.gender, modifier = Modifier.align(Top))
                    CharacterName(
                        character = character,
                        modifier = Modifier.align(CenterVertically)
                    )
                    CharacterImage(character = character)
                }
            }
        }
    }
}

fun specifyColor(gender: String) : Color {
    return when (gender) {
        "Female" -> FemaleColor
        "Male" -> MaleColor
        else -> UnknownGenderColor
    }
}

@Composable
fun CharacterImage(
    character: Character
) {
    Image(
        painter = character.profileImage,
        contentDescription = character.name,
        modifier = Modifier
            .height(250.dp)
            .width(120.dp),
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
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun LocationList(
    modifier: Modifier = Modifier,
    locations: List<String>,
    buttonBackgroundColor: Color = DarkerButtonBlue,
    buttonContentColor: Color = TextWhite
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
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectedLocIndex == it) ButtonBlue else backColor,
                    contentColor = buttonContentColor
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = TextWhite
                )

            ) {
                Text(text = locations[it],
                    fontFamily = FontFamily(Font(R.font.firefans)
                    ))
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
            backgroundColor = ButtonBlue,
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
        modifier = modifier.size(100.dp)
    )

}


//Textte kaldık
@Composable
fun DescriptionPart() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = ButtonBlue,
                            fontSize = 40.sp,
                        )
                    ) {
                        append("M")
                    }
                    append("aceraya ")
                    withStyle(
                        style = SpanStyle(
                            color = ButtonBlue,
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
                fontFamily = FontFamily(Font(R.font.firefans)
            ))

        }



        MortyAnimation()
    }


}