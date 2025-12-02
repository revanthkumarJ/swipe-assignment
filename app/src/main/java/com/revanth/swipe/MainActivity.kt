package com.revanth.swipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.core.ui.theme.SwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwipeTheme {
                SwipeScaffold(
                    topBarTitle = "Swipe",
                    onNavigateBack = {},
                ) {
                    Text("Hello")
                }
            }
        }
    }
}