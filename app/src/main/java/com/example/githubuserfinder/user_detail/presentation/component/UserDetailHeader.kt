package com.example.githubuserfinder.user_detail.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.githubuserfinder.core.presentation.CustomTextStyle
import com.example.githubuserfinder.core.presentation.UiConstant
import com.example.githubuserfinder.core.presentation.component.TouchableScale

@Composable
fun UserDetailHeader(
    onNavigatedBack: () -> Unit,
) {
    Card(
        modifier = Modifier
            .requiredHeight(UiConstant.HEADER_DEFAULT_HEIGHT)
            .fillMaxWidth(),
        elevation = 15.dp,
        shape = RectangleShape,
        contentColor = Color.White,
        backgroundColor = Color.Black,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TouchableScale(
                onClick = onNavigatedBack,
                modifier = Modifier
                    .requiredHeight(48.dp)
                    .width(52.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                )
            }

            Text(
                text = "Detail",
                style = CustomTextStyle.header,
            )
        }
    }
}
