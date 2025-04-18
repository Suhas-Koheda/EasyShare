package dev.haas.easyshare.network

import java.net.NetworkInterface
import java.util.Arrays
import kotlin.streams.asSequence

actual object NetworkUtils {
    actual fun getBroadcastAddress(): List<String> {
        var networkBroadcastAddresses = mutableListOf<String>()
        try {
            NetworkInterface.networkInterfaces()
                .asSequence()
                .filter { it.name == "wlan0" || it.name == "wlp0s20f3" }
                .flatMap { it.interfaceAddresses }
                .forEach {
                    networkBroadcastAddresses.add(it?.broadcast.toString())
                }
        } catch (e: Exception) {
            println(e.message)
        }
        println(networkBroadcastAddresses.toTypedArray().contentToString())
        return networkBroadcastAddresses
    }

    actual fun getLocalIpAddress(): String {
        try {
            NetworkInterface.getNetworkInterfaces()?.let { interfaces ->
                for (interface_ in interfaces.toList()) {
                    if (interface_.name == "wlan0" ||
                        interface_.name == "wlp0s20f3"
                    ) {

                        for (address in interface_.inetAddresses.toList()) {
                            if (!address.isLoopbackAddress && address is java.net.Inet4Address) {
                                return address.hostAddress ?: "127.0.0.1"
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return "127.0.0.1"
    }
}