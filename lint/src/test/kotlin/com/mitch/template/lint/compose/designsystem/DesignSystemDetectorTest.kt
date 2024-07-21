package com.mitch.template.lint.compose.designsystem

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.mitch.template.lint.compose.designsystem.DesignSystemDetector.Companion.IncorrectDesignSystemCallIssue
import com.mitch.template.lint.compose.designsystem.DesignSystemDetector.Companion.ReceiverNames
import com.mitch.template.lint.util.ComposablePreviewStub
import com.mitch.template.lint.util.ComposableStub
import org.junit.Test

class DesignSystemDetectorTest {

    @Test
    fun `detect replacements of Methods`() {
        lint()
            .issues(IncorrectDesignSystemCallIssue)
            .allowMissingSdk()
            .files(
                ComposableStub,
                ComposablePreviewStub,
                kotlin(
                    """
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.tooling.preview.Preview                    

                    @Preview
                    @Composable
                    fun ComposablePreview() {
                        MaterialTheme()
                    }
                    """
                ).indented()
            )
            .run()
            .expect(
                """
                src/test.kt:7: Error: Using MaterialTheme instead of TemplateTheme [IncorrectDesignSystemCall]
                    MaterialTheme()
                    ~~~~~~~~~~~~~~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `detect replacements of Receiver`() {
        lint()
            .issues(IncorrectDesignSystemCallIssue)
            .allowMissingSdk()
            .files(
                WrongObjectStubs,
                kotlin(
                    """
                    fun main() {
                    ${
                        ReceiverNames
                            .mapIndexed { index, reportable -> index to reportable.wrongName }
                            .joinToString("\n") { (index, name) -> "    val y$index = $name.x" }
                    }
                    }
                    """
                ).indented()
            )
            .run()
            .expect(
                """
                src/test.kt:2: Error: Using Icons instead of TemplateIcons [IncorrectDesignSystemCall]
                    val y0 = Icons.x
                             ~~~~~~~
                src/test.kt:3: Error: Using MaterialTheme instead of TemplateDesignSystem [IncorrectDesignSystemCall]
                    val y1 = MaterialTheme.x
                             ~~~~~~~~~~~~~~~
                2 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `do not detect correct Methods usage`() {
        lint()
            .issues(IncorrectDesignSystemCallIssue)
            .allowMissingSdk()
            .files(
                ComposableStub,
                ComposablePreviewStub,
                kotlin(
                    """
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.tooling.preview.Preview                    

                    @Preview
                    @Composable
                    fun ComposablePreview() {
                        TemplateTheme()
                    }
                    """
                ).indented()
            )
            .run()
            .expectClean()
    }

    @Test
    fun `do not detect correct Receiver usage`() {
        lint()
            .issues(IncorrectDesignSystemCallIssue)
            .allowMissingSdk()
            .files(
                CorrectObjectStubs,
                kotlin(
                    """
                    fun main() {
                    ${
                        ReceiverNames
                            .mapIndexed { index, reportable -> index to reportable.correctName }
                            .joinToString("\n") { (index, name) -> "    val y$index = $name.x" }
                    }
                    }
                    """
                ).indented()
            )
            .run()
            .expectClean()
    }

    private companion object {
        private val WrongObjectStubs = kotlin(
            """
            ${ReceiverNames.joinToString("\n") { "|object ${it.wrongName} { val x = 1 }" }}    
            """.trimMargin()
        )

        private val CorrectObjectStubs = kotlin(
            """
            ${ReceiverNames.joinToString("\n") { "|object ${it.correctName} { val x = 1 }" }}    
            """.trimMargin()
        )
    }
}
