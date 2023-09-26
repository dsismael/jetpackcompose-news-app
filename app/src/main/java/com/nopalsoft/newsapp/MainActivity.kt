package com.nopalsoft.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nopalsoft.newsapp.model.News
import com.nopalsoft.newsapp.repository.NewsRepository
import com.nopalsoft.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

object Destinations {
    const val LIST_SCREEN = "LIST_SCREEN"
    const val DETAILS_SCREEN = "DETAILS_SCREEN"
    const val FAVORITE_SCREEN = "FAVORITE_SCREEN"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.LIST_SCREEN,
                    ) {
                        composable(Destinations.LIST_SCREEN) {
                            ListScreen(navController)
                        }
                        composable("${Destinations.DETAILS_SCREEN}/{newTitle}") {
                            it.arguments?.getString("newTitle")?.let { title ->
                                DetailsScreen(title, navController)
                            }
                        }
                        composable(Destinations.FAVORITE_SCREEN) {
                            FavoriteScreen(navController = navController)
                        }
                        /*composable("${Destinations.FAVORITE_SCREEN}"){
                            it.arguments?.getBoolean("newFavorite")?.let { isFavorite ->
                                FavoriteScreen(news = viewModel(), navController = navController)
                            }
                        }*/
                    }
                }
            }
        }
    }
}