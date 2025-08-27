package com.smsmanager

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.material3.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.smsmanager.ui.RuleListScreen
import com.smsmanager.data.RuleRepository

class MainActivity : ComponentActivity() {
    private lateinit var ruleRepository: RuleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Request permissions at launch
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS
            ),
            100
        )

        ruleRepository = RuleRepository(applicationContext)
        setContent {
            SMSManagerTheme {
                RuleListScreen(ruleRepository = ruleRepository)
            }
        }
    }
}