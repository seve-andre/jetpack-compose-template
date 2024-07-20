package com.mitch.template.lint.compose.vm

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.checks.infrastructure.TestMode
import com.mitch.template.lint.compose.vm.ViewModelInjectionDetector.Companion.ViewModelInjectionIssue
import com.mitch.template.lint.util.ComposableStub
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ViewModelInjectionDetectorTest(private val viewModel: String) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "viewModel = {0}")
        fun data(): Collection<Array<String>> {
            return listOf(
                arrayOf("viewModel"),
                arrayOf("hiltViewModel"),
                arrayOf("koinViewModel")
            )
        }
    }

    @Test
    fun `detect incorrect view model reified declaration`() {
        val vmWordUnderline = "~".repeat(viewModel.length)
        lint()
            .issues(ViewModelInjectionIssue)
            .allowMissingSdk()
            .skipTestModes(TestMode.PARENTHESIZED)
            .files(
                ComposableStub,
                kotlin(
                    """
                    import androidx.compose.runtime.Composable

                    @Composable
                    fun HomeScreen() {
                        val viewModel = $viewModel<MyVm>()
                    }
                    """.trimIndent()
                )
            )
            .run()
            .expect(
                """
                src/test.kt:5: Error: Usages of $viewModel to acquire a ViewModel should be done in composable default parameters, so that it is more testable and flexible. [IncorrectViewModelInjection]
                    val viewModel = $viewModel<MyVm>()
                    ~~~~~~~~~~~~~~~~$vmWordUnderline~~~~~~~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `detect incorrect view model explicit declaration`() {
        val vmWordUnderline = "~".repeat(viewModel.length)
        lint()
            .issues(ViewModelInjectionIssue)
            .allowMissingSdk()
            .skipTestModes(TestMode.PARENTHESIZED)
            .files(
                ComposableStub,
                kotlin(
                    """
                    import androidx.compose.runtime.Composable

                    @Composable
                    fun HomeScreen() {
                        val viewModel: MyVm = $viewModel()
                    }
                    """.trimIndent()
                )
            )
            .run()
            .expect(
                """
                src/test.kt:5: Error: Usages of $viewModel to acquire a ViewModel should be done in composable default parameters, so that it is more testable and flexible. [IncorrectViewModelInjection]
                    val viewModel: MyVm = $viewModel()
                    ~~~~~~~~~~~~~~~~~~~~~~$vmWordUnderline~~
                1 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun `do not detect correct view model reified declaration`() {
        lint()
            .issues(ViewModelInjectionIssue)
            .allowMissingSdk()
            .skipTestModes(TestMode.PARENTHESIZED)
            .files(
                ComposableStub,
                kotlin(
                    """
                    import androidx.compose.runtime.Composable

                    @Composable
                    fun HomeScreen(
                        viewModel = $viewModel<MyVm>()
                    ) { }
                    """.trimIndent()
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun `do not detect correct view model explicit declaration`() {
        lint()
            .issues(ViewModelInjectionIssue)
            .allowMissingSdk()
            .skipTestModes(TestMode.PARENTHESIZED)
            .files(
                ComposableStub,
                kotlin(
                    """
                    import androidx.compose.runtime.Composable

                    @Composable
                    fun HomeScreen(
                        viewModel: MyVm = $viewModel()
                    ) { }
                    """.trimIndent()
                )
            )
            .run()
            .expectClean()
    }
}
