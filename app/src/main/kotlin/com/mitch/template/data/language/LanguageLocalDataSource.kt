package com.mitch.template.data.language

import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.util.Locale

class LanguageLocalDataSource {
    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    suspend fun setLocale(locale: Locale) {
        changeAppLocale(locale)
        refreshTrigger.emit(Unit)
    }

    fun getLocale(): Flow<Locale> = refreshTrigger
        .onStart { emit(Unit) }
        .flatMapLatest {
            flowOf(
                AppCompatDelegate.getApplicationLocales()[0] ?: initializeLocale()
            )
        }
        .distinctUntilChanged()

    /**
     * Initializes the application's locale based on the device's default locale.
     *
     * Retrieves the device's default locale, maps it to the domain language preference
     * and applies its corresponding locale to the app.
     *
     * @return The initialized locale.
     */
    private suspend fun initializeLocale(): Locale {
        val defaultLocale = Locale.getDefault()
        val preference = defaultLocale.toDomainModel()
        val preferenceLocale = preference.locale
        changeAppLocale(preferenceLocale)
        return preferenceLocale
    }

    private suspend fun changeAppLocale(locale: Locale) {
        withContext(Dispatchers.Main) {
            // temporarily allow disk reads and writes,
            // since setApplicationLocales is a blocking operation;
            // (returns old policy to restore afterwards)
            val oldPolicy = StrictMode.allowThreadDiskWrites()
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(locale.toLanguageTag())
            )
            // restore old policy
            StrictMode.setThreadPolicy(oldPolicy)
        }
    }
}
