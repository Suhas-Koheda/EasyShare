    package dev.haas.easyshare.network

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.*
    import kotlinx.coroutines.channels.Channel
    import kotlinx.coroutines.flow.*
    import java.net.SocketTimeoutException

    data class NetworkDevice(
        val ipAddress: String,
        val isConnected: Boolean = false
    )

    data class NetworkState(
        val devices: List<NetworkDevice> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    class NetworkViewModel : ViewModel() {
        private val _state = MutableStateFlow(NetworkState())
        val state: StateFlow<NetworkState> = _state.asStateFlow()

        private val receivedMessages = Channel<String>(capacity = Channel.UNLIMITED)

        init {
            startListening()
        }

        private fun startListening() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    startUdpServer { message ->
                        viewModelScope.launch {
                            handleIncomingMessage(message)
                        }
                    }
                } catch (e: Exception) {
                    _state.update { it.copy(errorMessage = "UDP server failed: ${e.message}") }
                }
            }

            // Listen to incoming messages
            viewModelScope.launch(Dispatchers.Default) {
                while (isActive) {
                    try {
                        val message = receivedMessages.receive()
                        val ip = extractIpFromMessage(message)

                        if (ip != null && !_state.value.devices.any { it.ipAddress == ip }) {
                            _state.update { current ->
                                current.copy(devices = current.devices + NetworkDevice(ip))
                            }
                        }
                    } catch (e: TimeoutCancellationException) {
                        // Optional: timeout logic
                    } catch (e: Exception) {
                        _state.update { it.copy(errorMessage = "Message processing error: ${e.message}") }
                    }
                }
            }
        }

        fun sendDiscoveryBroadcast() {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
                try {
                    withContext(Dispatchers.IO) {
                        withTimeout(10_000) {
                            sendBroadcast()
                        }
                    }
                } catch (e: SocketTimeoutException) {
                    _state.update { it.copy(errorMessage = "Broadcast timed out") }
                } catch (e: Exception) {
                    _state.update { it.copy(errorMessage = "Broadcast failed: ${e.message}") }
                } finally {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }

        fun toggleConnection(device: NetworkDevice) {
            _state.update { current ->
                current.copy(
                    devices = current.devices.map {
                        if (it.ipAddress == device.ipAddress) it.copy(isConnected = !it.isConnected)
                        else it
                    }
                )
            }
        }

        suspend fun handleIncomingMessage(message: String) {
            receivedMessages.send(message)
        }

        private fun extractIpFromMessage(message: String): String? {
            return """\b(?:[0-9]{1,3}\.){3}[0-9]{1,3}\b""".toRegex().find(message)?.value
        }
    }
