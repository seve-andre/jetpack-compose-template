package com.mitch.template.lint.kt

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.mitch.template.lint.kt.KotlinNamingDetector.Companion.ConstantFieldNameIssue
import com.mitch.template.lint.kt.KotlinNamingDetector.Companion.EnumConstantNameIssue
import org.junit.Test

class KotlinNamingDetectorTest {

    @Test
    fun `detect object member wrong name`() {
        lint()
            .issues(ConstantFieldNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    object Object {
                        const val myConstant = 5
                    }
                    """
                ).indented()
            )
            .run()
            .expect(
                """
                src/Object.kt:2: Error: Using myConstant instead of MyConstant (PascalCase) [ConstantFieldName]
                    const val myConstant = 5
                    ~~~~~~~~~~~~~~~~~~~~~~~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `detect companion object member wrong name`() {
        lint()
            .issues(ConstantFieldNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    class CompanionObject {
                        companion object {
                            const val myConstant = 5
                        }
                    }
                    """
                ).indented()
            )
            .run()
            .expect(
                """
                src/CompanionObject.kt:3: Error: Using myConstant instead of MyConstant (PascalCase) [ConstantFieldName]
                        const val myConstant = 5
                        ~~~~~~~~~~~~~~~~~~~~~~~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `do not detect class member name`() {
        lint()
            .issues(ConstantFieldNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    class Class {
                        val myConstant = 5
                    }
                    """
                ).indented()
            )
            .run()
            .expectClean()
    }

    @Test
    fun `do not detect object literal member name`() {
        lint()
            .issues(ConstantFieldNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    interface Logger {
                        fun log(value: String)
                    }
                    val MyLogger = object : Logger {
                        override fun log(value: String) {
                            val toLog = "myValue"
                            println(toLog)
                        }
                    }
                    """
                ).indented()
            )
            .run()
            .expectClean()
    }

    @Test
    fun `do not detect object expression member name`() {
        lint()
            .issues(ConstantFieldNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    val HelloWorld = object {
                        val hello = "Hello"
                        val world = "World"
                        override fun toString() = "＄hello ＄world"
                    }
                    """
                ).indented()
            )
            .run()
            .expectClean()
    }

    @Test
    fun `detect top level property name`() {
        lint()
            .issues(ConstantFieldNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    const val andrea = "n"
                    """
                ).indented()
            )
            .run()
            .expect(
                """
                src/test.kt:1: Error: Using andrea instead of Andrea (PascalCase) [ConstantFieldName]
                const val andrea = "n"
                ~~~~~~~~~~~~~~~~~~~~~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `detect enum constant wrong name`() {
        lint()
            .issues(EnumConstantNameIssue)
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    enum class Direction {
                        NORTH, South, East, West
                    }
                    """
                ).indented()
            )
            .run()
            .expect(
                """
                src/Direction.kt:2: Error: Using NORTH instead of North (PascalCase) [EnumConstantName]
                    NORTH, South, East, West
                    ~~~~~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }
}
