package dev.haas.easyshare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.haas.easyshare.network.getBroadcastAddress
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var ip  by remember { mutableStateOf(mutableListOf(" "," ")) }
        Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.Center) {
            Button(onClick = {ip=getBroadcastAddress().toMutableList()}) {
                Text("Check if its working")
            }
            Text(ip.toTypedArray().contentToString())
        }
    }
}