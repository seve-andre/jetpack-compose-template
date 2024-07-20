package com.mitch.template.lint.util

import com.android.tools.lint.client.api.Configuration
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector

/**
 * A layer of indirection for implementations of option loaders without needing to extend from
 * Detector. This goes along with [OptionLoadingDetector].
 */
interface LintOption {
    fun load(configuration: Configuration)
}

/** A [Detector] that supports reading the given [options]. */
abstract class OptionLoadingDetector(vararg options: LintOption) : Detector() {
    private val options = options.toList()

    override fun beforeCheckRootProject(context: Context) {
        super.beforeCheckRootProject(context)
        val config = context.configuration
        options.forEach { it.load(config) }
    }
}
