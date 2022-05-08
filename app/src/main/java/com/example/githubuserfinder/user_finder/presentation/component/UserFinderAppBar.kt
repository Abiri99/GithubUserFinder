package com.example.githubuserfinder.user_finder.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.githubuserfinder.core.presentation.UiConstant.HEADER_DEFAULT_HEIGHT
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import com.example.githubuserfinder.core.presentation.debugModifier

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun UserFinderAppBar(
    searchedValue: TextFieldValue,
    onSearchedValueChanged: (TextFieldValue) -> Unit,
    isSearchEnabledInitially: Boolean = false,
    onSearchDismissed: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    val (searchInputFocusRequester) = remember { FocusRequester.createRefs() }

    /**
     * This is a local state, so it doesn't need to be in
     * the [com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel]
     * */
    var isSearchEnabled by remember { mutableStateOf(isSearchEnabledInitially) }

    val onSearchIconClicked = {
        isSearchEnabled = true
    }

    val onDismissSearchClicked = {
        isSearchEnabled = false
        onSearchDismissed()
    }

    LaunchedEffect(isSearchEnabled) {
        if (isSearchEnabled) {
            searchInputFocusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Card(
        elevation = 15.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(HEADER_DEFAULT_HEIGHT)
            .zIndex(1f), // zIndex is added so that card's shadow would be drawn on top of the content
        contentColor = MaterialTheme.colors.onSurface,
        shape = RectangleShape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp) // Due to Material Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1
        ) {
            AnimatedContent(
                targetState = isSearchEnabled,
                transitionSpec = {
                    if (targetState) {
                        slideInVertically { height -> height } + scaleIn() + fadeIn() with slideOutVertically { height -> -height } + scaleOut() + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + scaleIn() + fadeIn() with slideOutVertically { height -> height } + scaleOut() + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                modifier = Modifier
                    .weight(1f),
            ) { isUserSearching ->
                if (isUserSearching) {
                    // Show TextInput
                    TextField(
                        value = searchedValue,
                        onValueChange = onSearchedValueChanged,
                        maxLines = 1,
//                        textStyle = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .requiredHeight(48.dp)
                            .focusRequester(searchInputFocusRequester),
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                        ),
                    )
                } else {
                    // Show Title
                    Text(
                        text = "Github User Finder",
                        style = MaterialTheme.typography.h6,
                    )
                }
            }

            Spacer(modifier = Modifier.requiredWidth(24.dp)) // Due to Material Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1

            AnimatedContent(
                targetState = isSearchEnabled,
                transitionSpec = {
                    if (isSearchEnabled) {
                        fadeIn() with fadeOut()
                    } else {
                        fadeIn() with fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                modifier = Modifier.debugModifier(Modifier.background(Color.White.copy(0.1f)))
            ) { isUserSearching ->
                val iconModifier = Modifier
                    .requiredSize(24.dp)
                TouchableScale(
                    onClick = if (isUserSearching) onDismissSearchClicked else onSearchIconClicked,
                    modifier = Modifier
                        .requiredSize(48.dp) // Due to Material Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1
                        .debugModifier(Modifier.background(Color.White.copy(0.1f)))
                ) {
                    if (isUserSearching) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = iconModifier,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            modifier = iconModifier,
                        )
                    }
                }
            }
        }
    }
}
