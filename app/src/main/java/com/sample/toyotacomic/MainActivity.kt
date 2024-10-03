package com.sample.toyotacomic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.sample.toyotacomic.states.ComicState
import com.sample.toyotacomic.ui.theme.ToyotaComicTheme
import com.sample.toyotacomic.viewmodel.ComicViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToyotaComicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()

                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "firstScreen") {
        composable("firstScreen") { FirstScreen(navController) }
        composable(
            "secondScreen/{month}/{year}",
            arguments = listOf(
                navArgument("month") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieve the arguments from the back stack entry
            val month = backStackEntry.arguments?.getString("month") ?: ""
            val year = backStackEntry.arguments?.getString("year") ?: ""
            SecondScreen(month, year)
        }
    }
}
@Composable
fun FirstScreen(navController: NavController, viewModel: ComicViewModel = hiltViewModel()) {
    val comicState by viewModel.comicState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (comicState) {
            is ComicState.Loading -> {
                Text("Loading...")
            }
            is ComicState.Success -> {
                val comicInfo = (comicState as ComicState.Success).comicInfo
                AsyncImage(
                    model = comicInfo.img,
                    contentDescription = comicInfo.alt,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Text(text = "Month: ${comicInfo.month}")
                Text(text = "Year: ${comicInfo.year}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navController.navigate("secondScreen/${comicInfo.month}/${comicInfo.year}")
                }) {
                    Text("Go to Second Screen")
                }
            }
            is ComicState.Error -> {
                val errorMessage = (comicState as ComicState.Error).message
                Text(text = "Error: $errorMessage")
            }

            else -> {}
        }
    }
}

@Composable
fun SecondScreen(month: String, year: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Month: $month")
        Text(text = "Year: $year")
        // Add additional details or UI components as needed
    }
}

