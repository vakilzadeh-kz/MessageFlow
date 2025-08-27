package com.smsmanager.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smsmanager.data.Rule
import com.smsmanager.data.RuleRepository
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun RuleListScreen(ruleRepo: RuleRepository) {
    val scope = rememberCoroutineScope()
    var rules by remember { mutableStateOf(listOf<Rule>()) }
    var editingRule by remember { mutableStateOf<Rule?>(null) }

    LaunchedEffect(Unit) {
        rules = ruleRepo.getRules()
    }

    if (editingRule != null) {
        RuleScreen(
            rule = editingRule,
            onSave = { rule ->
                scope.launch {
                    ruleRepo.addOrUpdateRule(rule)
                    rules = ruleRepo.getRules()
                    editingRule = null
                }
            },
            onCancel = { editingRule = null }
        )
    } else {
        Column(Modifier.padding(16.dp)) {
            Text("Rules", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(rules) { rule ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { editingRule = rule }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Trigger: ${rule.trigger}")
                            Text("Actions: ${rule.actions.joinToString()}")
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                editingRule = Rule(
                    id = UUID.randomUUID().toString(),
                    trigger = "",
                    sender = null,
                    actions = emptyList()
                )
            }) {
                Text("Add Rule")
            }
        }
    }
}