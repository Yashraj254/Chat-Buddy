package com.yashraj.chatbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashraj.chat_presentation.ChatScreen
import com.yashraj.chatbuddy.ui.theme.ChatBuddyTheme
import com.yashraj.login.presentation.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()

            systemUiController.setSystemBarsColor(color = MaterialTheme.colorScheme.background, darkIcons = false)
            systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.background, darkIcons = false)
            val user by remember { mutableStateOf(Firebase.auth.currentUser) }

            ChatBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val startDestination = if (user == null) "login" else "chat"
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("login") {
                            LoginScreen(
                                navController = navController
                            )
                        }
                        composable("chat") { ChatScreen(navController = navController) }

                    }
                }
            }
        }
    }
}
