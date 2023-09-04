package com.mitch.appname.data.local

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class LanguageLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun setLocale(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        )
    }

    /*fun getLocale() = callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
                    val locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
                    Timber.d("LanguageLocalDataSource locale: $locale")
                    trySend(locale)
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }*/

    fun getLocale() = flow {
        emit(AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault())
    }
}
1
