package com.mitch.template.util

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import kotlin.jvm.optionals.getOrNull

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.library(alias: String): Provider<MinimalExternalModuleDependency> {
    val maybeLibrary = this.findLibrary(alias).getOrNull()
    return maybeLibrary
        ?: throw DependencyNotFoundException("No library found with alias [$alias] in the catalog")
}

fun VersionCatalog.plugin(alias: String): String {
    val maybePluginId = this.findPlugin(alias).getOrNull()?.get()?.pluginId
    return maybePluginId
        ?: throw DependencyNotFoundException("No plugin found with alias [$alias] in the catalog")
}

class DependencyNotFoundException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)
