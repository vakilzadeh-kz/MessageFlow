package com.smsmanager.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

data class Rule(
    val id: String,
    val trigger: String,
    val sender: String?,
    val actions: List<RuleAction>
)

enum class RuleAction {
    SHOW_NOTIFICATION, FORWARD_SMS, FORWARD_EMAIL
}

class RuleRepository(private val context: Context) {
    // Use Jetpack DataStore for CRUD operations on rules
    // Suspend functions for create, read, update, delete
    fun getAllRulesSync(): List<Rule> {
        // Synchronous stub for BroadcastReceiver; use coroutine in real code
        return emptyList()
    }
}