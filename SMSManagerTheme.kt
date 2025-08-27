package com.smsmanager

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun SMSManagerTheme(content: @Composable () -> Unit) {
    // Define theme and apply Vazir font globally
    // You'll need to add Vazir.ttf to assets/fonts and reference it here.
    MaterialTheme(
        typography = Typography(
            // Use Vazir font for all text styles
        ),
        content = content
    )
}