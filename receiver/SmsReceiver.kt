package com.smsmanager.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import com.smsmanager.data.RuleRepository
import com.smsmanager.data.RuleAction
import com.smsmanager.util.NotificationHelper
import kotlinx.coroutines.runBlocking

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val repo = RuleRepository(context)
        val rules = repo.getAllRulesSync()

        for (msg in messages) {
            val sender = msg.displayOriginatingAddress
            val body = msg.displayMessageBody
            for (rule in rules) {
                val triggerMatch = body.contains(rule.trigger, ignoreCase = true)
                val senderMatch = rule.sender.isNullOrBlank() || sender == rule.sender
                if (triggerMatch && senderMatch) {
                    if (RuleAction.SHOW_NOTIFICATION in rule.actions) {
                        NotificationHelper.showNotification(context, "SMS Rule Triggered", body)
                    }
                    if (RuleAction.FORWARD_SMS in rule.actions && !rule.forwardNumber.isNullOrBlank()) {
                        SmsManager.getDefault().sendTextMessage(
                            rule.forwardNumber, null, body, null, null
                        )
                    }
                    if (RuleAction.FORWARD_EMAIL in rule.actions && !rule.forwardEmail.isNullOrBlank()) {
                        val emailIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(rule.forwardEmail))
                            putExtra(Intent.EXTRA_SUBJECT, "Forwarded SMS")
                            putExtra(Intent.EXTRA_TEXT, "From: $sender\n\n$body")
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(Intent.createChooser(emailIntent, "Send email"))
                    }
                }
            }
        }
    }
}