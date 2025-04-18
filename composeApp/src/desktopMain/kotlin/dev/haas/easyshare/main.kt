package dev.haas.easyshare

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "EasyShare",
    ) {
        App()
    }
}