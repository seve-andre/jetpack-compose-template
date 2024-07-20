package com.mitch.template.lint.compose

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.isKotlin
import com.mitch.template.lint.util.LintOption
import com.mitch.template.lint.util.OptionLoadingDetector
import com.mitch.template.lint.util.isComposable
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtPropertyAccessor
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

abstract class ComposableFunctionDetector(vararg options: LintOption) :
    OptionLoadingDetector(*options), SourceCodeScanner {

    final override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        UMethod::class.java
    )

    final override fun createUastHandler(context: JavaContext): UElementHandler? {
        if (!isKotlin(context.uastFile?.lang)) return null
        return object : UElementHandler() {
            override fun visitMethod(node: UMethod) {
                if (node.isComposable) {
                    visitComposable(context, node)
                    when (val sourcePsi = node.sourcePsi ?: return) {
                        is KtPropertyAccessor -> {
                            visitComposable(context, node, sourcePsi)
                        }

                        is KtFunction -> {
                            visitComposable(context, node, sourcePsi)
                        }
                    }
                }
            }
        }
    }

    open fun visitComposable(context: JavaContext, method: UMethod) {}

    open fun visitComposable(context: JavaContext, method: UMethod, property: KtPropertyAccessor) {}

    open fun visitComposable(context: JavaContext, method: UMethod, function: KtFunction) {}
}
