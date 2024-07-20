package com.mitch.template.lint.util.fix

import com.android.tools.lint.detector.api.LintFix

fun interface FixStrategy {
    fun fix(lintFixBuilder: LintFix.Builder): LintFix
}

data class Fix(
    val fileExceptions: List<FileName> = emptyList(),
    val strategy: FixStrategy? = null
)

@JvmInline
value class FileName private constructor(val name: String) {
    companion object {
        fun of(name: String): FileName = FileName("$name.kt")
    }
}

data class Reportable(
    val wrongName: String,
    val correctName: String,
    val fix: Fix? = null
)

fun designSystemComponentFixStrategy(oldName: String, newName: String): FixStrategy = FixStrategy {
    val componentsPackage = "com.mitch.template.ui.designsystem.components"

    it.replace()
        .text(oldName)
        .with("$componentsPackage.$newName")
        .shortenNames()
        .build()
}
