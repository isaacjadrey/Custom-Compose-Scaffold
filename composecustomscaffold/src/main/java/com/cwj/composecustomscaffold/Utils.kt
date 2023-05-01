package com.cwj.composecustomscaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }

@Composable
fun Float.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

object Constants {
    const val ThresholdXPosition = 1.4f
    const val ThresholdYRotation = 25
    const val ThresholdCameraDistance = 8
}