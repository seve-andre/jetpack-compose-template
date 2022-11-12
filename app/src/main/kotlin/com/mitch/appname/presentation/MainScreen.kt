package com.mitch.appname.presentation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.mitch.appname.util.SnackbarController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    scaffoldState: ScaffoldState,
    systemUiController: SystemUiController,
    snackbarController: SnackbarController
) {

}
