package com.mitch.template

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

enum class TemplateFlavorDimension {
    Version;

    val dimensionName = this.name.replaceFirstChar { it.lowercase() }
}

enum class TemplateFlavor(
    val dimension: TemplateFlavorDimension,
    val applicationIdSuffix: String? = null
) {
    Demo(dimension = TemplateFlavorDimension.Version),
    Prod(dimension = TemplateFlavorDimension.Version);

    val flavorName = this.name.replaceFirstChar { it.lowercase() }
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        flavorDimensions += TemplateFlavorDimension.values().map { it.dimensionName }
        productFlavors {
            TemplateFlavor.values().forEach { flavor ->
                create(flavor.flavorName) {
                    dimension = flavor.dimension.dimensionName
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (flavor.applicationIdSuffix != null) {
                            applicationIdSuffix = flavor.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
