package com.smsmanager.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
data class Rule(
    val id: String,
    val trigger: String,
    val sender: String?,
    val actions: List<RuleAction>,
    val forwardNumber: String? = null,
    val forwardEmail: String? = null
)

@Serializable
enum class RuleAction {
    SHOW_NOTIFICATION, FORWARD_SMS, FORWARD_EMAIL
}

val Context.dataStore by preferencesDataStore("rules")

class RuleRepository(private val context: Context) {
    private val RULES_KEY = stringPreferencesKey("rules_json")

    suspend fun getRules(): List<Rule> {
        val prefs = context.dataStore.data.first()
        val json = prefs[RULES_KEY] ?: "[]"
        return Json.decodeFromString(json)
    }

    fun getAllRulesSync(): List<Rule> = runBlocking { getRules() }

    suspend fun saveRules(rules: List<Rule>) {
        context.dataStore.edit { prefs ->
            prefs[RULES_KEY] = Json.encodeToString(rules)
        }
    }

    suspend fun addOrUpdateRule(rule: Rule) {
        val rules = getRules().toMutableList()
        val idx = rules.indexOfFirst { it.id == rule.id }
        if (idx >= 0) rules[idx] = rule else rules.add(rule)
        saveRules(rules)
    }

    suspend fun deleteRule(id: String) {
        val rules = getRules().filter { it.id != id }
        saveRules(rules)
    }
}