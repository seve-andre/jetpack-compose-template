package com.mitch.template.lint.kt

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.mitch.template.lint.util.isCompanionObjectMember
import com.mitch.template.lint.util.isObjectLiteralMember
import com.mitch.template.lint.util.isObjectMember
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UEnumConstant

/**
 * A detector that checks for incorrect naming conventions in Kotlin and Compose code.
 */
class KotlinNamingDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        UEnumConstant::class.java,
        UDeclaration::class.java,
    )

    override fun createUastHandler(context: JavaContext): UElementHandler =
        object : UElementHandler() {
            override fun visitDeclaration(node: UDeclaration) {
                val property = node.sourcePsi as? KtProperty ?: return
                val propertyName = property.name.orEmpty()
                if (property.isObjectLiteralMember) return // exclude nodes inside object : Class()

                if (
                    property.isObjectMember ||
                    property.isCompanionObjectMember ||
                    property.isTopLevel
                ) {
                    if (!PascalCaseRegex.matches(propertyName)) {
                        reportConstantFieldName(context, node, propertyName)
                    }
                }
            }

            private fun reportConstantFieldName(
                context: JavaContext,
                node: UElement,
                wrongName: String
            ) {
                val correctPropertyName = wrongName.toPascalCase()
                println("Using $wrongName instead of $correctPropertyName")
                context.report(
                    ConstantFieldNameIssue,
                    node,
                    context.getLocation(node),
                    "Using $wrongName instead of $correctPropertyName (PascalCase)",
                    fix()
                        .replace()
                        .text(wrongName)
                        .with(correctPropertyName)
                        .build()
                )
            }

            override fun visitEnumConstant(node: UEnumConstant) {
                val constantName = node.name
                if (!PascalCaseRegex.matches(constantName)) {
                    val correctConstantName = constantName.toPascalCase()
                    println("Using $constantName instead of $correctConstantName")
                    context.report(
                        EnumConstantNameIssue,
                        node,
                        context.getLocation(node),
                        "Using $constantName instead of $correctConstantName (PascalCase)",
                        fix()
                            .replace()
                            .text(constantName)
                            .with(correctConstantName)
                            .build()
                    )
                }
            }
        }

    companion object {
        @JvmField
        val EnumConstantNameIssue: Issue = Issue.create(
            id = "EnumConstantName",
            briefDescription = "Enum constants should use PascalCase",
            explanation = "Compose API guidelines suggest using PascalCase for enum constants, " +
                "instead of CAPITALS_AND_UNDERSCORES",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 8,
            severity = Severity.ERROR,
            implementation = Implementation(
                KotlinNamingDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )

        @JvmField
        val ConstantFieldNameIssue: Issue = Issue.create(
            id = "ConstantFieldName",
            briefDescription = "Constants should use PascalCase",
            explanation = "Compose API guidelines suggest using PascalCase for top-level properties, " +
                "members of an object or a companion object, instead of camelCase",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 8,
            severity = Severity.ERROR,
            implementation = Implementation(
                KotlinNamingDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )

        @JvmField
        val PropertyFieldNameIssue: Issue = Issue.create(
            id = "PropertyFieldName",
            briefDescription = "Properties should use camelCase.",
            explanation = "Compose API guidelines follows Kotlin suggestion for properties, " +
                "so they should use camelCase and not PascalCase.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 8,
            severity = Severity.ERROR,
            implementation = Implementation(
                KotlinNamingDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}

private val PascalCaseRegex = Regex("[A-Z]([a-z0-9]+(?:[A-Z][a-z0-9]+)*)?")
private fun String.toPascalCase(): String {
    return this
        .splitWords()
        .joinToString("") { it.capitalize() }
}

private fun String.toCamelCase(): String {
    return this
        .splitWords()
        .mapIndexed { index, word ->
            if (index == 0) word.lowercase() else word.capitalize()
        }
        .joinToString("")
}

private fun String.splitWords(): List<String> {
    val regex = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=\\w)(?=_+)|(?<=_)(?=\\w)|(?<=[a-z])(?=[A-Z])"
    return this.split(Regex(regex)).filterNot { it == "_" }
}

private fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}
