package com.smsmanager.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.smsmanager.data.RuleRepository
import com.smsmanager.util.NotificationHelper

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Extract SMS from intent
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val ruleRepository = RuleRepository(context)
        val rules = ruleRepository.getAllRulesSync() // You may want to use coroutines

        for (message in messages) {
            val sender = message.displayOriginatingAddress
            val body = message.displayMessageBody
            // Check against rules, execute actions if matched
        }
    }
}