package com.cwj.customcomposescaffold.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cwj.composecustomscaffold.CustomDrawerValue
import com.cwj.composecustomscaffold.CustomScaffoldState
import com.cwj.composecustomscaffold.rememberCustomDrawerState
import com.cwj.composecustomscaffold.rememberCustomScaffoldState
import com.cwj.composecustomscaffolddrawerlib.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    scope: CoroutineScope,
    customScaffoldState: CustomScaffoldState,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Compose Custom Drawer",
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Modded by:",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Isaac jadrey",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        for (screen in screens) {
            DrawerItem(item = screen, onItemClick = {
                navController.navigate(screen.route) {
                    navController.graph.startDestinationId.let {
                        popUpTo(it) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch { customScaffoldState.customDrawerState.close() }
                }
            }, selected = currentRoute == screen.route)
        }
    }
}

val screens = listOf(
    Screen.Home,
    Screen.Notifications,
    Screen.Cart,
    Screen.Orders,
    Screen.Profile
)

@Preview(showBackground = true)
@Composable
fun DCPreview() {
    DrawerContent(
        scope = rememberCoroutineScope(), customScaffoldState = rememberCustomScaffoldState(
            rememberCustomDrawerState(initialValue = CustomDrawerValue.Closed)
        ),
        navController = rememberNavController()
    )
}