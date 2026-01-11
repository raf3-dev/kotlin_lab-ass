package com.example.ass6scaffold

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ass6scaffold.ui.theme.Ass6ScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ass6ScaffoldTheme {
                MyScaffoldLayout()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(contextForToast: Context,navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = { Text(text = "My Application") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Green.copy(alpha = 0.3f)
        ),
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.Friend1.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                Toast.makeText(contextForToast, "Friend1", Toast.LENGTH_SHORT).show()
            }) {
                Icon(painter = painterResource(id = R.drawable.tonklasunshine), contentDescription = "Friend1", tint = Color.Unspecified)
            }
            IconButton(onClick = {
                navController.navigate(Screen.Friend2.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                Toast.makeText(contextForToast, "Friend2", Toast.LENGTH_SHORT).show()
            }) {
                Icon(painter = painterResource(id = R.drawable.bellpineapple), contentDescription = "Friend2", tint = Color.Unspecified)
            }
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Settings") },
                        onClick = {
                            Toast.makeText(contextForToast, "Settings", Toast.LENGTH_SHORT).show()
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Logout") },
                        onClick = {
                            Toast.makeText(contextForToast, "Logout", Toast.LENGTH_SHORT).show()
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = null)
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun MyBottomBar(navController: NavHostController, contextForToast: Context) {
    val navigationItems = listOf(Screen.Home, Screen.Friend1, Screen.Friend2)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.Green.copy(alpha = 0.3f)
    ) {
        navigationItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(text = screen.name) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    Toast.makeText(contextForToast, screen.name, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun MyScaffoldLayout() {
    val contextForToast = LocalContext.current.applicationContext
    val navController = rememberNavController()

    Scaffold(
        topBar = { MyTopAppBar(contextForToast = contextForToast, navController = navController) },
        bottomBar = { MyBottomBar(navController, contextForToast = contextForToast) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavGraph(navController = navController)
        }
    }
}