package net.nonimi.nindotx

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.nonimi.nindotx.ui.home.HomeScreen
import net.nonimi.nindotx.ui.login.LoginScreen
import net.nonimi.nindotx.ui.theme.NindoTXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NindoTXTheme {
                Scaffold { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val activity = (LocalContext.current as? Activity)
    NavHost(navController = navController, startDestination = "login", modifier = modifier) {
        composable("login") {
            LoginScreen(onLoginClicked = { username -> navController.navigate("home?username=$username") })
        }
        composable(
            "home?username={username}",
            arguments = listOf(navArgument("username") { 
                type = NavType.StringType
                nullable = true 
            })
        ) {
            val username = it.arguments?.getString("username")
            HomeScreen(
                username = username,
                onExitClicked = { activity?.finish() },
                onLogoutClicked = { navController.popBackStack() })
        }
    }
}
