package com.cwj.customcomposescaffold.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.cwj.composecustomscaffold.CustomScaffold
import com.cwj.composecustomscaffold.rememberCustomScaffoldState
import com.cwj.customcomposescaffold.presentation.navigation.AppNavGraph
import com.cwj.customcomposescaffold.presentation.navigation.DrawerContent
import com.cwj.customcomposescaffold.presentation.navigation.TopBar

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val customScaffoldState = rememberCustomScaffoldState()

    CustomScaffold(
        customScaffoldState = customScaffoldState,
        topBar = { TopBar(scope = scope, customScaffoldState = customScaffoldState) },
        drawerBackgroundColor = Color.White,
        /**
         * set gesturesEnabled to [customScaffoldState.customDrawerState.isOpen] to enable
         * drawer interaction by using gestures only when the drawer is open
         */

        // drawerGesturesEnabled = customScaffoldState.customDrawerState.isOpen,
        customDrawerContent = {
            DrawerContent(
                scope = scope,
                customScaffoldState = customScaffoldState,
                navController = navController
            )
        },
        /**
         * set [contentCornerSize] to the desired size of the Rounded corner for
         * the rest of the app's UI when the drawer is open.
         */
        contentCornerSize = 20.dp,
        content = {
            AppNavGraph(navController = navController)
        }
    )
}