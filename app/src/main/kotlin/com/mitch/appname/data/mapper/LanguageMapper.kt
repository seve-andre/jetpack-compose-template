package com.mitch.appname.data.mapper

import com.mitch.appname.util.AppLanguage
import java.util.Locale

fun Locale.toAppLanguage(): AppLanguage {
    return AppLanguage.fromLocale(this)
}
