package me.neko.nzhelper.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSourceScreen(
    navController: NavHostController
) {
    val scrollBehavior = pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("开放源代码") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
            .only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(licenseList) { item ->
                LicenseItemView(item) {
                    val intent = Intent(Intent.ACTION_VIEW, item.url.toUri())
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun LicenseItemView(
    item: LicenseItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(text = "${item.name} - ${item.author}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = item.url,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            )
            Text(text = getLicense(item.type), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private val licenseList = listOf(
    LicenseItem(
        "Google",
        "Jetpack Compose",
        "https://github.com/androidx/androidx",
        LicenseType.Apache2
    ),
    LicenseItem(
        "JetBrains",
        "Kotlin",
        "https://github.com/JetBrains/kotlin",
        LicenseType.Apache2
    ),
    LicenseItem(
        "Google",
        "Material Design 3",
        "https://m3.material.io/",
        LicenseType.Apache2
    ),
    LicenseItem(
        "Google",
        "Gson",
        "https://github.com/google/gson",
        LicenseType.Apache2
    ),
    LicenseItem(
        "square",
        "Moshi",
        "https://github.com/square/moshi",
        LicenseType.Apache2
    ),
    LicenseItem(
        "square",
        "okhttp",
        "https://github.com/square/okhttp",
        LicenseType.Apache2
    ),
)

data class LicenseItem(
    val author: String,
    val name: String,
    val url: String,
    val type: LicenseType
)

enum class LicenseType {
    Apache2,
    MIT,
    GPL3
}

private fun getLicense(type: LicenseType): String =
    when (type) {
        LicenseType.Apache2 -> "Apache Software License 2.0"
        LicenseType.MIT -> "MIT License"
        LicenseType.GPL3 -> "GNU general public license Version 3"
    }

@Preview(showBackground = true)
@Composable
fun OpenSourceScreenPreview() {
    OpenSourceScreen(
        navController = rememberNavController()
    )
}