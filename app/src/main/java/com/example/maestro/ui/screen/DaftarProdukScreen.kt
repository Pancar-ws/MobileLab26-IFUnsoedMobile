package com.example.maestro.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.maestro.R
import com.example.maestro.data.dummy.DummyData
import com.example.maestro.data.model.Category
import com.example.maestro.data.model.Product
import com.example.maestro.ui.theme.MaestroTheme

// 1. Komponen Kartu Produk
@Composable
fun ProductItemCard(product: Product, onClick: () -> Unit) {
    // pembuatan card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        // pembuatan Column dan Box
        Column(modifier = Modifier.padding(8.dp)) {
            val imageRes = if (product.img == "icon_app_jualan") R.drawable.icon_app_jualan else R.drawable.icon_app_jualan

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White),
                    contentScale = ContentScale.Fit
                )

                if (product.category != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = product.category.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rp ${product.price.toInt()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Stok: ${product.stock}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

// 2. Komponen Item Kategori (Pill)
@Composable
// layout menampilkan kategori produk
fun CategoryItem(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontWeight = FontWeight.Medium
        )
    }
}

// 3. Layar Utama Daftar Produk
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarProdukScreen() {
    var selectedCategoryId by remember { mutableStateOf(DummyData.categories.firstOrNull()?.id) }

    val filteredProducts = if (selectedCategoryId != null) {
        DummyData.products.filter { it.category_id == selectedCategoryId }
    } else {
        DummyData.products
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Produk UMKM") },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Keranjang Belanja", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart, // Ganti painterResource menjadi ini
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Kategori Produk",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(all = 16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DummyData.categories) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = category.id == selectedCategoryId,
                        onClick = { selectedCategoryId = category.id }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Daftar Produk",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProducts) { product ->
                    ProductItemCard(product = product) {
                        Toast.makeText(context, "Clicked: ${product.name}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

// 4. Preview Komponen
@Preview(showBackground = true)
@Composable
fun PreviewProduct() {
    MaestroTheme {
        ProductItemCard(product = DummyData.products[0], onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategory() {
    MaestroTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CategoryItem(category = DummyData.categories[0], isSelected = true, onClick = {})
        }
    }
}

@Preview(showBackground = false, name = "Light Theme")
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Dark Theme")
@Composable
fun PreviewDaftarProduk() {
    MaestroTheme {
        DaftarProdukScreen()
    }
}