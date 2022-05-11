package com.example.githubuserfinder.user_finder.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.githubuserfinder.core.presentation.UiConstant.HEADER_DEFAULT_HEIGHT
import com.example.githubuserfinder.core.presentation.component.TouchableScale

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.CustomAppBar(
    title: String,
    onNavigateUp: (() -> Unit)? = null,
    isTransparent: Boolean = false,
) {

    /**
     * This will be updated only when [isTransparent] changes
     * with the help of [derivedStateOf] API
     */
    val elevation by remember(isTransparent) {
        derivedStateOf {
            if (isTransparent) {
                0.dp
            } else {
                10.dp
            }
        }
    }

    val primaryColor = MaterialTheme.colors.primary

    /**
     * This will be updated only when [isTransparent] changes
     * with the help of the [derivedStateOf] API
     */
    val backgroundColor by remember(isTransparent) {
        derivedStateOf {
            if (isTransparent) {
                Color.Transparent
            } else {
                primaryColor
            }
        }
    }

    Card(
        elevation = elevation,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(HEADER_DEFAULT_HEIGHT)
            .align(Alignment.TopCenter)
            .zIndex(1f), // zIndex is added so that card's shadow would be drawn on top of the content
        contentColor = MaterialTheme.colors.onSurface,
        shape = RectangleShape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // Due to Material Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1
        ) {

            onNavigateUp?.let {
                TouchableScale(onClick = it) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .requiredWidth(24.dp)
                            .requiredHeight(48.dp)
                    )
                }

                Spacer(modifier = Modifier.requiredWidth(24.dp))
            }

            Text(
                text = title,
                style = MaterialTheme.typography.h6,
            )
        }
    }
}
