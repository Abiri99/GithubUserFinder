package com.example.githubuserfinder.user_finder.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.githubuserfinder.core.TextStyles
import com.example.githubuserfinder.core.TouchableScale
import com.example.githubuserfinder.core.debugModifier

private val HEADER_HEIGHT = 72.dp

@Composable
fun UserFinderAppBar() {

    val searchIconSize = 24.dp

    /**
     * This field is going to set the search icon's start padding relative to the [HEADER_HEIGHT]
     * */
    val searchIconTouchableSpacePadding = HEADER_HEIGHT - searchIconSize

    /**
     * This field is going to set the search icon's vertical padding relative to the [HEADER_HEIGHT]
     * */
    val searchIconTouchableSpaceVerticalPadding = searchIconTouchableSpacePadding.div(2)

    Card(
        elevation = 100.dp,
        backgroundColor = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(HEADER_HEIGHT)
            .zIndex(1f), // zIndex is added so that card's shadow would be drawn on top of the content
        contentColor = Color.White,
        shape = RectangleShape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Find User",
                style = TextStyles.header,
            )

            TouchableScale(
                onClick = {
                    // TODO: Implement
                },
                modifier = Modifier
                    .requiredSize(HEADER_HEIGHT)
                    .debugModifier(Modifier.background(Color.White.copy(0.1f)))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = searchIconTouchableSpacePadding,
                            top = searchIconTouchableSpaceVerticalPadding,
                            bottom = searchIconTouchableSpaceVerticalPadding,
                        ),
                )
            }
        }
    }
}
