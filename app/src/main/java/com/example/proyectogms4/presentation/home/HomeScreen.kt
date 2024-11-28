package com.example.proyectogms4.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectogms4.R
import com.google.firebase.firestore.FirebaseFirestore

data class MenuItem(
    val title: String,
    val icon: Int,
    val route: String
)

@Composable
fun HomeScreen(
    db: FirebaseFirestore,
    navController: NavController
) {
    val menuItems = listOf(
        MenuItem("Panel de Control", R.drawable.ic_dashboard, "dashboard"),
        MenuItem("Configuración", R.drawable.ic_settings, "settings"),
        MenuItem("Noticias", R.drawable.ic_news, "news"),
        MenuItem("Clima Hoy", R.drawable.ic_weather, "weather"),
        MenuItem("Testeo", R.drawable.ic_test, "testing"),
        MenuItem("Manual de Uso", R.drawable.ic_play, "manual")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Panel Principal",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(menuItems) { item ->
                MenuCard(
                    menuItem = item,
                    onItemClick = { navController.navigate(item.route) }
                )
            }
        }
    }
}

@Composable
private fun MenuCard(
    menuItem: MenuItem,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onItemClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = menuItem.icon),
                contentDescription = menuItem.title,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}