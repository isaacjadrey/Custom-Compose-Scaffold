@file:OptIn(ExperimentalMaterialApi::class)

package com.cwj.composecustomscaffold

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.contentColorFor
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.cwj.composecustomscaffold.Constants.ThresholdCameraDistance
import com.cwj.composecustomscaffold.Constants.ThresholdXPosition
import com.cwj.composecustomscaffold.Constants.ThresholdYRotation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Possible values [CustomDrawerState].
 */
enum class CustomDrawerValue {
    /**
     * The state of the drawer when it is closed.
     */
    Closed,

    /**
     * The state of the drawer when it is open.
     */
    Open
}

/**
 * Create and [remember] a [CustomDrawerState].
 *
 * @param initialValue The initial value of the state.
 */
@Composable
fun rememberCustomDrawerState(initialValue: CustomDrawerValue): CustomDrawerState {
    return remember {
        CustomDrawerState(initialValue = initialValue)
    }
}

/**
 * State of the [CustomDrawer] composable.
 *
 * @param initialValue The initial value of the state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 * @param animSpec The animation that will be used to animate the state.
 */
class CustomDrawerState(
    initialValue: CustomDrawerValue,
    confirmStateChange: (CustomDrawerValue) -> Boolean = { true },
    private val animSpec: TweenSpec<Float> = TweenSpec<Float>(durationMillis = 500),
) {
    internal val drawerVelocityThreshold = 400.dp

    internal val swipeableState = SwipeableState(
        initialValue = initialValue,
        animationSpec = animSpec,
        confirmStateChange = confirmStateChange
    )

    /**
     * The current position (in pixels) of the drawer sheet, or null before the offset is
     * initialized.
     */
    val offset: State<Float> get() = swipeableState.offset

    /**
     * The current value of the state.
     * If no swipe or animation is in progress, this corresponds to the start of the drawer
     * currently in. If a swipe or an animation is in progress, this corresponds the state the
     * drawer was in before the swipe or animation started.
     */
    val currentValue: CustomDrawerValue
        get() {
            return swipeableState.currentValue
        }

    /**
     * Whether the drawer is open.
     */
    val isOpen: Boolean get() = currentValue == CustomDrawerValue.Open

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean get() = currentValue == CustomDrawerValue.Closed

    /**
     * Open the drawer with animation and suspend until it's fully opened or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is interrupted.
     *
     * @return the reason the open animation ended.
     */
    suspend fun open() = animateTo(CustomDrawerValue.Open, animSpec)

    /**
     * Close the drawer with animation and suspend until it's fully closed or the animation has been
     * cancelled. This method with throw [CancellationException] if the animation is interrupted.
     *
     * @return the reason the close animation ended.
     */
    suspend fun close() = animateTo(CustomDrawerValue.Closed, animSpec)

    /**
     * Set the state of the drawer with specific animation.
     *
     * @param targetValue The new value to animate to.
     * @param animationSpec The animation that will be used to animate to the new value.
     */

    @OptIn(ExperimentalMaterialApi::class)
    suspend fun animateTo(targetValue: CustomDrawerValue, animationSpec: AnimationSpec<Float>) =
        swipeableState.animateTo(targetValue, animationSpec)

}

/**
 * <a href="https://material.io/components/navigation-drawer#modal-drawer" class="external" target="_blank">Material Design modal navigation drawer</a>.
 *
 * Modal navigation drawers block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 * The custom drawer however pushes the app's UI to the edge of the screen's layout grid in that
 * there is a bit of interaction with part of the app's content
 *
 * @param customDrawerContent composable that represents content inside the custom drawer
 * @param modifier optional modifier for the custom drawer
 * @param customDrawerState state of the custom drawer
 * @param gesturesEnabled whether or not the custom drawer can be interacted by gestures
 * @param drawerBackgroundColor background color to be used for the drawer sheet
 * @param drawerContentColor color pf the content tp used inside the drawer sheet. Defaults to
 * either the matching content color for [drawerBackgroundColor], or, if it is not a color from
 * the theme, this will keep the same value set above this surface.
 * @param contentCornerSize size of shape of the content when the drawer is open
 * @param contentBackgroundColor background color to be used for the content
 * @param content content of the rest of the UI
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 *
 * @Notes :
 *  We are not using [drawerShape], [drawerElevation], [scrimColor] in our custom drawer
 *  because the [content] will be drawn over into the drawer.
 */
@Composable
fun CustomDrawer(
    customDrawerContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    customDrawerState: CustomDrawerState = rememberCustomDrawerState(CustomDrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    contentCornerSize: Dp = 0.dp,
    contentBackgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.toPx()
    val layoutDirection = LocalLayoutDirection.current
    val endContentXPosition = screenWidth / ThresholdXPosition

    BoxWithConstraints(modifier = modifier.background(drawerBackgroundColor)) {
        val modalDrawerConstraints = constraints
        // TODO : think about Infinite max bounds case
        if (!modalDrawerConstraints.hasBoundedWidth) {
            throw IllegalStateException("Drawer shouldn't have infinite width")
        }

        val minValue = -modalDrawerConstraints.maxWidth.toFloat()
        val maxValue = 0f
        val anchors =
            mapOf(minValue to CustomDrawerValue.Closed, maxValue to CustomDrawerValue.Open)
        val isRtl = layoutDirection == LayoutDirection.Rtl

        Box(
            modifier = Modifier.swipeable(
                state = customDrawerState.swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                enabled = gesturesEnabled,
                reverseDirection = isRtl,
                velocityThreshold = customDrawerState.drawerVelocityThreshold,
                resistance = null
            )
        ) {
            val navigationMenu = "0"
            Surface(modifier = Modifier
                .fillMaxHeight()
                .offset {
                    // IntOffset(drawerState.offset.value.roundToInt(), 0)
                    val x = (customDrawerState.offset.value.roundToInt())
                    if (x <= (screenWidth / ThresholdXPosition).roundToInt()) {
                        IntOffset(x, 0)
                    } else {
                        IntOffset((screenWidth / ThresholdXPosition).roundToInt(), 0)
                    }
                }
                .width(endContentXPosition.toDp())
                .widthIn(0.dp, endContentXPosition.toDp())
                .semantics {
                    paneTitle = navigationMenu
                    if (customDrawerState.isOpen) {
                        dismiss {
                            if (
                                customDrawerState.currentValue == CustomDrawerValue.Closed
                            ) {
                                scope.launch { customDrawerState.close() }
                            }; true
                        }
                    }
                },
                color = drawerBackgroundColor,
                contentColor = drawerContentColor
            ) {
                Column(content = customDrawerContent)
            }

            Surface(
                Modifier
                    .fillMaxSize()
                    .offset {
                        val x =
                            (customDrawerState.offset.value.roundToInt() + screenWidth.roundToInt())
                        if (x <= (screenWidth / ThresholdXPosition).roundToInt()) {
                            IntOffset(x, 0)
                        } else {
                            IntOffset((screenWidth / ThresholdXPosition).roundToInt(), 0)
                        }
                    }
                    .graphicsLayer {
                        rotationY =
                            (-((ThresholdYRotation * (customDrawerState.offset.value / screenWidth)) + ThresholdYRotation))
                        cameraDistance = ThresholdCameraDistance * density
                    }
                        // Elevates a shadow below the [content] on top of the drawer content
                    .shadow(elevation = 50.dp)
                ,
                color = contentBackgroundColor,
                shape = RoundedCornerShape((((contentCornerSize.value * (customDrawerState.offset.value / screenWidth)) + contentCornerSize.value).roundToInt().dp))
            ) { content() }
        }
    }
}