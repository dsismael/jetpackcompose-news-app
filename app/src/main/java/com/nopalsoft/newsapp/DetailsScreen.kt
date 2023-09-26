package com.nopalsoft.newsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.nopalsoft.newsapp.model.News

@Composable
fun DetailsScreen(
    newTitle: String,
    navController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val new by viewModel.news.observeAsState(initial = null)

    new?.let {
        DetailsScreenBody(newTitle = newTitle, onNavigate = {navController.popBackStack()}, new = it,
        viewModel = viewModel
    )
    }

    LaunchedEffect(key1 = true) {
        viewModel.getNewsByTitle(newTitle)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreenBody(
    newTitle: String,
    onNavigate: () -> Unit,
    new: News,
    viewModel: DetailsScreenViewModel
) {
    var isFavorite by remember{ mutableStateOf(new.isFavorite) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(newTitle, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = { onNavigate() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.saveNotice(new)
                            isFavorite = !isFavorite
                            /*if (new != null) {
                                new.isFavorite = !new.isFavorite

                            }*/
                        }) {
                        if (isFavorite){
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                            )
                        } else{
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        new?.let {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                Column {
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
                    Column(Modifier.padding(8.dp)) {
                        val context = LocalContext.current

                        Text(new.title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                        Text(new.content ?: "", fontSize = 18.sp)
                        Box(Modifier.size(8.dp))
                        Button(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(new.url))
                                context.startActivity(intent)
                            }) {
                            Text("Ver mas...")
                        }
                    }
                }
            }
        } ?: run {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview
fun PreviewDetailScreen(){
    DetailsScreenBody(newTitle = "title", onNavigate = {}, new = News(
        title = "title",
        content = "content",
        author = "",
        url = "",
        urlToImage = ""
    ), viewModel = viewModel())
}