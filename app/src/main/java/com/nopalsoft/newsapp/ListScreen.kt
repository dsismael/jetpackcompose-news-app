package com.nopalsoft.newsapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import com.nopalsoft.newsapp.appbar.*
import com.nopalsoft.newsapp.model.News
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val newsList by viewModel.news.observeAsState(initial = emptyList())
    val backStackEntry = navController.currentBackStackEntryAsState()

    ListScreenBody(route = backStackEntry.value?.destination?.route?: "", onNavigate = {route ->
        navController.navigate(route)
    }, newsList)
    LaunchedEffect(key1 = true) {
        viewModel.getNews()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreenBody(
    route: String,
    onNavigate: (String) -> Unit,
    news: List<News>
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            DrawerContent()
        },
        bottomBar = {
            BottomNavigationBarContent(route = route, onNavigate = onNavigate)
        }
    )
    {
        LazyColumn {
            items(news) { new ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            onNavigate("${Destinations.DETAILS_SCREEN}/${new.title}")
                        },
                    backgroundColor = Color(0xFFE2E2E2)
                ) {
                    Column {
                        Text(
                            new.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            painter = rememberImagePainter(
                                data = new.urlToImage,
                                builder = {
                                    placeholder(R.drawable.placeholder)
                                    error(R.drawable.placeholder)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                        Text(new.content ?: "", maxLines = 3)
                    }
                }
            }
        }
    }
}

//añadir preview
@Preview
@Composable
fun PreviewListScreenBody(){
    ListScreenBody(onNavigate = {}, route = Destinations.LIST_SCREEN, news = listOf(
        News(
            title = "noticia",
            content = "copntenido de la noticia",
            author = "",
            url = "",
            urlToImage = "",
        )
    ))
}