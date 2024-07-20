package com.mitch.template.lint.compose.vm

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.util.childrenOfType
import com.mitch.template.lint.compose.ComposableFunctionDetector
import com.mitch.template.lint.util.Priorities
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.referenceExpression
import org.jetbrains.uast.UMethod

class ViewModelInjectionDetector : ComposableFunctionDetector(), SourceCodeScanner {

    companion object {
        @JvmField
        val ViewModelInjectionIssue: Issue = Issue.create(
            id = "IncorrectViewModelInjection",
            briefDescription = "Implicit dependencies of composables should be made explicit",
            explanation =
            """
            Dependencies of a Composable should always be passed in as parameters.
            """,
            category = Category.CORRECTNESS,
            priority = Priorities.High,
            severity = Severity.ERROR,
            implementation = Implementation(
                ViewModelInjectionDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )

        val KnownViewModelFactories by lazy {
            setOf(
                "viewModel", // AAC VM
                "hiltViewModel", // Hilt
                "koinViewModel" // Koin
            )
        }
    }

    override fun visitComposable(context: JavaContext, method: UMethod, function: KtFunction) {
        val bodyBlock = function.bodyBlockExpression ?: return
        val viewModelProperties = bodyBlock.childrenOfType<KtProperty>()
            .map { it to it.initializer?.referenceExpression()?.text }
            .filter { (_, declaration) -> declaration != null }
            .filter { (_, declaration) -> declaration in KnownViewModelFactories }

        for ((property, viewModelFactoryName) in viewModelProperties) {
            context.report(
                ViewModelInjectionIssue,
                property,
                context.getLocation(property),
                """
                Usages of $viewModelFactoryName to acquire a ViewModel should be done in \
                composable default parameters, so that it is more testable and flexible.
                """.trimIndent()
            )
        }
    }
}
