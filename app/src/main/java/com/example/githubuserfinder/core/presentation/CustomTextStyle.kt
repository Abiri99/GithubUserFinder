package com.example.githubuserfinder.core.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CustomTextStyle {

    val header = TextStyle(
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )

    val contentLargeSemiBold = TextStyle(
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val content = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    )
}
