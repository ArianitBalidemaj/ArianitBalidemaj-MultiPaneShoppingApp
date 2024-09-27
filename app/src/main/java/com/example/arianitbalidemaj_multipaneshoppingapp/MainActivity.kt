package com.example.arianitbalidemaj_multipaneshoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.arianitbalidemaj_multipaneshoppingapp.ui.theme.ArianitBalidemajMultiPaneShoppingAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArianitBalidemajMultiPaneShoppingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MultiPaneShoppingApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val description: String
)

val products = listOf(
    Product("iPhone 16", "$1000", "New iPhone that comes in many different colors and new specs."),
    Product("Apple Watch 10", "$400", "New watch with waterproof technology and great "),
    Product("MacBook Pro 13.3'", "$2000", "Brand new M3 laptop with all the new specs and MacOs")
)

@Composable
fun MultiPaneShoppingApp(modifier: Modifier = Modifier) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                ProductList(onProductSelected = { selectedProduct = it })
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                if (selectedProduct != null) {
                    ProductDetails(
                        product = selectedProduct,
                        onBack = { selectedProduct = null }
                    )
                } else {
                    Text(
                        text = "Select a product to view details",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            if (selectedProduct == null) {
                ProductList(onProductSelected = { selectedProduct = it }) // same thing as before

                Text(
                    text = "Select a product to view details",
                    style = MaterialTheme.typography.headlineSmall
                )
            } else {
                ProductDetails(
                    product = selectedProduct,
                    onBack = { selectedProduct = null }
                )
            }
        }
    }
}

@Composable
fun ProductList(modifier: Modifier = Modifier, onProductSelected: (Product) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(products.size) { index ->
            val product = products[index]
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductSelected(product) }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ProductDetails(product: Product?, onBack: () -> Unit, modifier: Modifier = Modifier) {
    if (product != null) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Name: ${product.name}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Price: ${product.price}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = modifier.height(8.dp))

            Text(
                text = "Description: ${product.description}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = modifier.height(8.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArianitBalidemajMultiPaneShoppingAppTheme {
        MultiPaneShoppingApp()
    }
}