package com.architecture.ui.newslist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.architecture.R
import com.architecture.ui.newslist.model.NewsArticleUIModel
import com.architecture.ui.newslist.viewmodel.NewsListViewModel
import com.architecture.ui.theme.SangaviSampleApplicationTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SangaviSampleApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        observer()
    }

    private fun observer() {
        with(viewModel) {
            viewState.observe(this@MainActivity) { viewState ->
                handleViewState(viewState)
            }
        }
    }

    private fun handleViewState(viewState: NewsListViewModel.ViewState) {
        when (viewState) {
            is NewsListViewModel.ViewState.Data -> {
                setContent {
                    NewsList(newsList = viewState.newsArticles)
                }
            }

            NewsListViewModel.ViewState.Error -> {
                setContent {
                    Greeting("Loading the Data")
                }
            }

            NewsListViewModel.ViewState.Loading -> {
                setContent {
                    Greeting("Error Occurred")
                }
            }
        }
    }
}


@Composable
fun NewsList(newsList: List<NewsArticleUIModel>) {
    Column {
        Text(
            text = "News List", style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(10.dp)
                .padding(5.dp)
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp)) {
            UserList(users = newsList)
        }
    }


}

@Composable
fun UserList(users: List<NewsArticleUIModel>) {

    LazyColumn(contentPadding = PaddingValues(bottom = 10.dp)) {
        itemsIndexed(users) { index, news ->
            UserCard(index, news)
        }
        /*items(users){ news ->
            UserCard(news)
        }*/
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello \n$name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SangaviSampleApplicationTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserCard(index: Int, news: NewsArticleUIModel) {
    //Horizontal Linear Layout
    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Column {
                GlideImage(
                    model = news.image,
                    contentDescription = "",
                    loading = placeholder(R.drawable.ic_launcher_foreground),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = news.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = news.description, style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Button(onClick = {
                    Toast.makeText(context, "${news.title} Clicked", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier.padding(10.dp)) {
                    Text(text = "View Profile")
                }
            }
        }
    }

}