package com.mitch.appname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mitch.appname.presentation.NavGraphs
import com.mitch.appname.presentation.ui.theme.AppTheme
import com.mitch.appname.util.SnackbarController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

/* what to add:
- add internet connection check
- provide controllers for destinations (navHost, snackbar, ...)
- ci/cd github workflows
- snackbar: success, info & error
* */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState = rememberScaffoldState()
                    val systemUiController = rememberSystemUiController()
                    val snackbarController = SnackbarController(lifecycleScope)

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                dependenciesContainerBuilder = {
                                    /*
                                        TODO: Replace container with manual call to composables
                                        see at https://composedestinations.rafaelcosta.xyz/destination-arguments/navhost-level-parameters#manually-call-your-screen-composable
                                    */
                                    dependency(scaffoldState)
                                    dependency(systemUiController)
                                    dependency(snackbarController)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
