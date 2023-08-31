package com.mitch.appname.data.repo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.mitch.appname.domain.repo.AppLanguageRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale
import javax.inject.Inject

class AppLanguageRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppLanguageRepo {
    override suspend fun setLocale(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        )
    }

    override fun getLocale() = callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
                    val locale = AppCompatDelegate.getApplicationLocales()[0]
                    if (locale != null) {
                        trySend(locale)
                    }
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }
}
