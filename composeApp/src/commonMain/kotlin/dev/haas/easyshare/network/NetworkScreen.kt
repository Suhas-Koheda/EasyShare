package dev.haas.easyshare.network

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import cafe.adriel.voyager.core.screen.Screen

class NetworkScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = NetworkViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.sendDiscoveryBroadcast()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Network Devices",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            state.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (state.devices.isEmpty()) {
                Text(
                    text = "No devices found",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(state.devices) { device ->
                        NetworkDeviceItem(
                            device = device,
                            onToggleConnection = { viewModel.toggleConnection(device) }
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.sendDiscoveryBroadcast() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Rescan Network")
            }
        }
    }
}

@Composable
private fun NetworkDeviceItem(
    device: NetworkDevice,
    onToggleConnection: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = device.ipAddress,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = if (device.isConnected) "Connected" else "Disconnected",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (device.isConnected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Button(onClick = onToggleConnection) {
                Text(if (device.isConnected) "Disconnect" else "Connect")
            }
        }
    }
}