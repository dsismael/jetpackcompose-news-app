package com.nopalsoft.newsapp.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nopalsoft.newsapp.Destinations

@Composable
fun BottomNavigationBar(
    items: List<BottomBarItems>,
    route: String,
    modifier: Modifier = Modifier,
    onItemClicked: (BottomBarItems) -> Unit
) {

    //val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClicked(item) },
                selectedContentColor = Color.Cyan,
                unselectedContentColor = MaterialTheme.colors.onPrimary,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                       if (selected){
                           Text(
                               text = item.title,
                               textAlign = TextAlign.Center
                           )
                       }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBarContent(onNavigate: (String) -> Unit, route: String) {
    BottomNavigationBar(
        items = listOf(
            BottomBarItems(
                title = "List",
                route = Destinations.LIST_SCREEN,
                icon = Icons.Default.List,
            ),
            BottomBarItems(
                title = "Favorite",
                route = Destinations.FAVORITE_SCREEN,
                icon = Icons.Default.Star,
            )
        ),
        route = route,
        onItemClicked = {
            onNavigate(it.route)
        }
    )
}