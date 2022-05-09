package com.example.githubuserfinder.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.core.view.WindowCompat
import com.example.githubuserfinder.app.theme.lightThemeColors

const val TAG = "MainActivity"

// Dependencies are created manually in the main activity and injected to the lower layers
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            MaterialTheme(
                colors = lightThemeColors,
            ) {
                GithubUserFinderApp()
            }
        }
    }
}
