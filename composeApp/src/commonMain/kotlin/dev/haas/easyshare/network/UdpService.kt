package dev.haas.easyshare.network

import androidx.compose.runtime.mutableStateListOf
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Datagram
import io.ktor.network.sockets.InetSocketAddress
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.toJavaAddress
import io.ktor.util.network.hostname
import io.ktor.utils.io.core.BytePacketBuilder
import io.ktor.utils.io.core.build
import io.ktor.utils.io.core.writeText
import io.ktor.utils.io.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

var receivedMessages= mutableStateListOf<String>()
@OptIn(DelicateCoroutinesApi::class)
fun startUdpServer(onMessageReceived: (String) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val serverSocket = aSocket(selectorManager).udp().bind(InetSocketAddress("0.0.0.0", 4545))
            println("Server is listening on port 4545")

            while (true) {
                val datagram = serverSocket.receive()
                val senderIp = datagram.address.toJavaAddress().hostname
                if (senderIp != NetworkUtils.getLocalIpAddress()) {
                    val text = datagram.packet.readText()
                    println("Received from $senderIp: $text")
                    onMessageReceived(text)
                } else {
                    println("Received message from self")
                }
            }
        } catch (e: Exception) {
            println("Server error: ${e.message}")
        }
    }
}


fun sendBroadcast(){
    CoroutineScope(Dispatchers.IO).launch {
        val selectorManager = SelectorManager(Dispatchers.IO)
        try {
            val socket = aSocket(selectorManager).udp().bind {
                broadcast = true
            }

            val broadcastAddress = NetworkUtils.getBroadcastAddress().get(NetworkUtils.getBroadcastAddress().size-1)

            val message = "Hello from platform ! My IP is ${NetworkUtils.getLocalIpAddress()}"


            val packet = BytePacketBuilder().apply {
                writeText(message)
            }.build()

            socket.send(Datagram(packet, InetSocketAddress(broadcastAddress.substring(1), 4545)))
            println("Broadcast sent to $broadcastAddress: $message")

        } catch (e: Exception) {
            println("Error sending broadcast: ${e.message}")
        } finally {
            selectorManager.close()
        }
    }
}