package dev.haas.easyshare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.haas.easyshare.network.NetworkScreen
import dev.haas.easyshare.network.NetworkUtils
import dev.haas.easyshare.network.receivedMessages
import dev.haas.easyshare.network.sendBroadcast
import dev.haas.easyshare.network.startUdpServer
import org.jetbrains.compose.ui.tooling.preview.Preview

// MainActivity.kt or App.kt
@Composable
fun App() {
    MaterialTheme {
        /*val receivedMessages by remember { derivedStateOf { receivedMessages } }
        var ip  by remember { mutableStateOf(mutableListOf("Not Started")) }
        var localIp by remember { mutableStateOf(NetworkUtils.getLocalIpAddress()) }
        LaunchedEffect(Unit) {
            startUdpServer() // Start server when composable launches
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                ip = NetworkUtils.getBroadcastAddress().toMutableList()
            }) {
                Text("Check broadcast addresses")
            }

            Text("Local IP: ${NetworkUtils.getLocalIpAddress()}")

            Button(onClick = { sendBroadcast() }) {
                Text("Send broadcast message")
            }

            Text("Received messages:")
            LazyColumn {
                items(dev.haas.easyshare.network.receivedMessages) { message ->
                    Text(text = message.toString())
                }
            }
        }*/
        Navigator(NetworkScreen())
    }
}