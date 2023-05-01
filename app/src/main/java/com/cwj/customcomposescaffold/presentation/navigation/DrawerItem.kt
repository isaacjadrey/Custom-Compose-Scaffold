package com.cwj.customcomposescaffold.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerItem(
    item: Screen,
    onItemClick: (Screen) -> Unit,
    selected: Boolean,
) {
    val background = if (selected) Color.Transparent.copy(alpha = .3f) else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .clip(RoundedCornerShape(6.dp))
            .background(background)
            .padding(horizontal = 16.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = item.title, fontSize = 20.sp)
    }
}

@Preview
@Composable
fun DIPreview() {
    DrawerItem(item = Screen.Notifications, onItemClick = {}, selected = true)
}