package com.mitch.template.lint.util

import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtProperty

/**
 * Returns whether the member is inside an object declaration.
 *
 * This example shows `Low`, `Normal` and `High`, members of an object:
 * ```
 * object Priorities {
 *     const val Low = 3
 *     const val Normal = 5
 *     const val High = 10
 * }
 * ```
 */
val KtProperty.isObjectMember: Boolean
    get() {
        val parentContext = this.parent.context
        return parentContext is KtObjectDeclaration
    }

/**
 * Returns whether the member is inside a companion object.
 *
 * This example shows `Tag`, a member of a companion object:
 * ```
 * class Logger {
 *     companion object {
 *         val Tag = "LoggerTag"
 *     }
 * }
 * ```
 */
val KtProperty.isCompanionObjectMember: Boolean
    get() {
        val parentContext = this.parent.context
        return parentContext is KtObjectDeclaration && parentContext.isCompanion()
    }

/**
 * Object literals, also known as [object expressions](https://kotlinlang.org/docs/object-declarations.html#object-expressions),
 * are objects of anonymous classes. Members are properties declared inside them.
 *
 * Returns whether the member is inside an object literal.
 *
 * This example shows `printPrefix`, a member of an object literal:
 * ```
 * val MyPrinter = object : Printer {
 *     val printPrefix = "printing..."
 * }
 * ```
 *
 * This example shows `hello` and `world`, also members of an object literal:
 * ```
 * val Literal = object {
 *     val hello = "Hello"
 *     val world = "World"
 *     override fun toString() = "$hello $world"
 * }
 * ```
 */
val KtProperty.isObjectLiteralMember: Boolean
    get() {
        val parentContext = this.parent.context
        return parentContext is KtObjectDeclaration && parentContext.isObjectLiteral()
    }
