package com.example.core.presentation.component

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This class indicates the states a [TouchableScale] could be in
 * */
private enum class TouchableScaleState {
    Pressed,
    Idle,
}

/**
 * TouchableScale is a composable which animates
 * the content scale when it is pressed or touched
 *
 * @param onClick is a lambda called after user presses the composable
 * @param modifier is used to change the composable behaviour and decoration
 * @param enabled is to handle if the component should be animated and clicked in case of touch events
 * @param content is a composable to be drawn with TouchableScale's attributes
 *
 * */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TouchableScale(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val currentState = remember { MutableTransitionState(TouchableScaleState.Idle) }
    val transition = updateTransition(currentState, label = "transition")

    val contentScale by transition.animateFloat(
        label = "opacity",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        }
    ) { state ->
        when (state) {
            TouchableScaleState.Pressed -> 0.9f
            TouchableScaleState.Idle -> 1f
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .graphicsLayer {
                scaleX = contentScale
                scaleY = contentScale
                clip = false
            }
            .pointerInput(enabled) {
                detectTapGestures(
                    onPress = {
                        if (enabled) {
                            coroutineScope.launch {
                                try {
                                    currentState.targetState = TouchableScaleState.Pressed
                                    awaitRelease()
                                    currentState.targetState = TouchableScaleState.Idle
                                    delay(200)
                                    onClick()
                                } catch (e: CancellationException) {
                                    currentState.targetState = TouchableScaleState.Idle
                                }
                            }
                        }
                    },
                )
            },
    ) {
        content()
    }
}
