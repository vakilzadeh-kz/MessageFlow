package com.smsmanager.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.smsmanager.data.Rule
import com.smsmanager.data.RuleRepository

@Composable
fun RuleScreen(
    rule: Rule?,
    onSave: (Rule) -> Unit,
    onCancel: () -> Unit
) {
    // UI for creating/editing a rule: trigger (keyword/sender), action(s)
    // Use TextField for trigger, checkboxes for actions, etc.
    // Call onSave with the constructed Rule object
}