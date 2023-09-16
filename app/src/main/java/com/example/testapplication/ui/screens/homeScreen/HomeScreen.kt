package com.example.testapplication.ui.screens.homeScreen

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.testapplication.R
import com.example.testapplication.network.Story
import com.example.testapplication.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.StateFlow

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    appUiState: AppUiState,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Log.i("HomeScreen", "Input")
    when (appUiState) {
        is AppUiState.Loading -> LoadingScreen(modifier)
        is AppUiState.Success -> NewsScreen(
            newsList = viewModel.newsList,
            modifier = modifier,
            viewModel = viewModel
        )
        is AppUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun NewsScreen(
    newsList: StateFlow<List<Story>>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val searchText by viewModel.searchText.collectAsState()
    val testList = newsList.collectAsState().value
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            label = null,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.retry))},
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(6.dp),
        ) {
            items(
                testList,
                key = { it.exp_date }
            ) {news ->
                CardNews(
                    story = news,
                    saveNews = {  }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardNews(
    story: Story,
    saveNews: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = saveNews,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .height(150.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(

        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(story.image_logo)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = story.news_name,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = story.news_name,
                    modifier = Modifier.weight(1f),
                    fontSize = 15.sp,
                    minLines = 3,
                    maxLines = 3,
                    letterSpacing = 0.sp,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
                FavoriteIcon()
            }
        }
    }
}

@Composable
fun FavoriteIcon(
    /*flight: Flights,
    viewModel: SelectedAirportViewModel*/
) {
    val coroutineScope = rememberCoroutineScope()
    var checked by remember { mutableStateOf<Boolean?>(true) }

    /*if (checked == null) {
        coroutineScope.launch {
            *//*checked = viewModel.checkAirport(flight.id)*//*
        }
    }*/
    IconToggleButton(
        modifier = Modifier
            .fillMaxHeight(),
        checked = checked == true,
        onCheckedChange = {
            checked = it

        }) {
        /** Выбор цвета иконки */
        val tint by animateColorAsState(
            if (checked == true) Color(0xFFFF8006)
            else Color(0xFFB0BEC5)
        )

        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = stringResource(id = R.string.loading),
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}