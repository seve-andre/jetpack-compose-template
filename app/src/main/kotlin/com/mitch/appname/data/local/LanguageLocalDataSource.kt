package com.mitch.appname.data.local

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun getLocale(): Flow<Locale> {
        val localeState = MutableStateFlow(
            AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
        )

        val localeChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
                    val newLocale =
                        AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
                    newLocale?.let {
                        localeState.value = it
                    }
                }
            }
        }
        context.registerReceiver(localeChangedReceiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))

        return flow {
            emit(localeState.value)

            // Continuously emit values as they change
            localeState.collect {
                emit(it)
            }
        }
    }
}
