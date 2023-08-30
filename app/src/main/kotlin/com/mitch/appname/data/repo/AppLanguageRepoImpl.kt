package com.mitch.appname.data.repo

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.mitch.appname.domain.repo.AppLanguageRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class AppLanguageRepoImpl @Inject constructor() : AppLanguageRepo {
    override suspend fun setLocale(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        )
    }

    override fun getLocale(): Flow<Locale> {
        return flow {
            val locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
            emit(locale)
        }
    }

    override fun getAvailableLocales(): List<Locale> {
        val list = AppCompatDelegate.getApplicationLocales()
        AppCompatDelegate.getApplicationLocales().get(1)
        return emptyList()
    }
}
