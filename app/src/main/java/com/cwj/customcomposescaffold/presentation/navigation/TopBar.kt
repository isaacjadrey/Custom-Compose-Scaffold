package com.cwj.customcomposescaffold.presentation.navigation

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cwj.composecustomscaffold.CustomScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBar(scope: CoroutineScope, customScaffoldState: CustomScaffoldState) {
    TopAppBar(
        title = { Text(text = "Compose Custom Drawer") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { customScaffoldState.customDrawerState.open() } }) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface
    )
}