package com.smsmanager.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smsmanager.data.Rule
import com.smsmanager.data.RuleAction

@Composable
fun RuleScreen(
    rule: Rule?,
    onSave: (Rule) -> Unit,
    onCancel: () -> Unit
) {
    var trigger by remember { mutableStateOf(rule?.trigger ?: "") }
    var sender by remember { mutableStateOf(rule?.sender ?: "") }
    var actions by remember { mutableStateOf(rule?.actions ?: emptyList()) }
    var forwardNumber by remember { mutableStateOf(rule?.forwardNumber ?: "") }
    var forwardEmail by remember { mutableStateOf(rule?.forwardEmail ?: "") }

    Column(Modifier.padding(16.dp)) {
        Text("Edit Rule", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = trigger,
            onValueChange = { trigger = it },
            label = { Text("Keyword/Trigger") }
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = sender,
            onValueChange = { sender = it },
            label = { Text("Sender (optional)") }
        )
        Spacer(Modifier.height(8.dp))
        Text("Actions")
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(
                checked = actions.contains(RuleAction.SHOW_NOTIFICATION),
                onCheckedChange = {
                    actions = if (it) actions + RuleAction.SHOW_NOTIFICATION else actions - RuleAction.SHOW_NOTIFICATION
                }
            )
            Text("Show Notification")
        }
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(
                checked = actions.contains(RuleAction.FORWARD_SMS),
                onCheckedChange = {
                    actions = if (it) actions + RuleAction.FORWARD_SMS else actions - RuleAction.FORWARD_SMS
                }
            )
            Text("Forward to phone number")
            Spacer(Modifier.width(8.dp))
            if (actions.contains(RuleAction.FORWARD_SMS)) {
                OutlinedTextField(
                    value = forwardNumber,
                    onValueChange = { forwardNumber = it },
                    label = { Text("Phone number") },
                    modifier = Modifier.width(180.dp)
                )
            }
        }
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(
                checked = actions.contains(RuleAction.FORWARD_EMAIL),
                onCheckedChange = {
                    actions = if (it) actions + RuleAction.FORWARD_EMAIL else actions - RuleAction.FORWARD_EMAIL
                }
            )
            Text("Forward to email")
            Spacer(Modifier.width(8.dp))
            if (actions.contains(RuleAction.FORWARD_EMAIL)) {
                OutlinedTextField(
                    value = forwardEmail,
                    onValueChange = { forwardEmail = it },
                    label = { Text("Email address") },
                    modifier = Modifier.width(180.dp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = {
                onSave(
                    rule!!.copy(
                        trigger = trigger,
                        sender = sender.ifBlank { null },
                        actions = actions,
                        forwardNumber = forwardNumber.ifBlank { null },
                        forwardEmail = forwardEmail.ifBlank { null }
                    )
                )
            }) { Text("Save") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onCancel) { Text("Cancel") }
        }
    }
}