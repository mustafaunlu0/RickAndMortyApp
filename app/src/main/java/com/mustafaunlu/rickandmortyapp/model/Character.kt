package com.mustafaunlu.rickandmortyapp.model

import androidx.compose.ui.graphics.painter.Painter


data class Character(
    var profileImage : Painter,
    var name : String,
    var gender : String
)
