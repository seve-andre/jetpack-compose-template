package com.mitch.appname.domain.repo

import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface AppLanguageRepo {
    suspend fun setLocale(locale: Locale)
    fun getLocale(): Flow<Locale>
    fun getAvailableLocales(): List<Locale>
}
