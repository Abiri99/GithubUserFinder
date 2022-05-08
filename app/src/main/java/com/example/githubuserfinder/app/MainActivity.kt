package com.example.githubuserfinder.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.example.githubuserfinder.app.theme.lightThemeColors
import com.example.githubuserfinder.core.presentation.SystemUiUtil

const val TAG = "MainActivity"

// Dependencies are created manually in the main activity and injected to the lower layers
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colors = lightThemeColors,
            ) {
                GithubUserFinderApp()
            }
        }
    }
}
