package com.nopalsoft.newsapp.appbar

import android.widget.RemoteViews.RemoteCollectionItems
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Header", fontSize = 60.sp)
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    onItemClicked: (MenuItem) -> Unit
) {

    LazyColumn(modifier){
        items(items) {item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked(item) }
                    .padding(16.dp)
            ) {
                Icon(imageVector = item.icon, contentDescription = item.contentDescription)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun DrawerContent(){
    DrawerBody(
        items = listOf(
            MenuItem(
                id = "Home",
                title = "Home",
                contentDescription = "Go Home",
                icon = Icons.Default.Home
            ),
            MenuItem(
                id = "Settings",
                title = "Settings",
                contentDescription = "Go to Settings",
                icon = Icons.Default.Settings
            ),
            MenuItem(
                id = "Help",
                title = "Help",
                contentDescription = "Get Help",
                icon = Icons.Default.Info
            )
        ),
        onItemClicked = {
            println("clicked on ${it.title}")
        }
    )
}