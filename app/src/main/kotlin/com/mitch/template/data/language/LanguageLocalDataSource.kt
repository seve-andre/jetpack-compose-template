package com.mitch.template.data.language

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import java.util.Locale

class LanguageLocalDataSource(
    private val context: Context
) {
    suspend fun setLocale(locale: Locale) {
        withContext(Dispatchers.Main) {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(locale.toLanguageTag())
            )
        }
    }

    fun getLocale(): Flow<Locale> = callbackFlow {
        val localeChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
                    val newLocale =
                        AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
                    trySend(newLocale)
                }
            }
        }
        context.registerReceiver(localeChangedReceiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))

        trySend(AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault())

        awaitClose {
            context.unregisterReceiver(localeChangedReceiver)
        }
    }.distinctUntilChanged()
}
