package com.yashraj.login.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.yashraj.ui.R
import com.yashraj.ui.TypingAnimation
import com.yashraj.utils.showToast

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // (1) Create ActivityResultLauncher
    val launcher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = {
            if (it.resultCode == ComponentActivity.RESULT_OK) {
                navController.navigate("chat")
            } else {
                context.showToast("Sign in failed")
            }
        }
    )

    // (2) Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )

    // (3) Create and launch sign-in intent
    val intent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hi, I'm your", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            TypingAnimation(
                text = "Chat Buddy",
                fontSize = 32.sp,
                color = Blue,
                duration = 200L
            )
        }
        Image(
            painter = painterResource(id = R.drawable.walle_icon),
            contentDescription = null
        )

        Button(
            onClick = {
                launcher.launch(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                painter = painterResource(id = com.yashraj.login.R.drawable.ic_google_logo),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Sign in via Google")
        }
    }
}

