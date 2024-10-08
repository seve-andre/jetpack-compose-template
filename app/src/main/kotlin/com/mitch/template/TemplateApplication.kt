package com.mitch.template

import android.app.Application
import com.mitch.template.di.DefaultDependenciesProvider
import com.mitch.template.di.DependenciesProvider
import timber.log.Timber

class TemplateApplication : Application() {

    lateinit var dependenciesProvider: DependenciesProvider

    override fun onCreate() {
        super.onCreate()
        dependenciesProvider = DefaultDependenciesProvider(this)

        // BuildConfig will be created after first run of the app
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
