package dev.haas.easyshare.network

import java.net.NetworkInterface

actual object NetworkUtils {
    actual fun getBroadcastAddress(): List<String> {
        var networkBroadcastAddresses = mutableListOf<String>()
        try {
            NetworkInterface.getNetworkInterfaces()
                .asSequence()
                .filter { it.name.startsWith("eth") || it.name.startsWith("ap") }
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
                    if (interface_.name.startsWith("eth") ||
                        interface_.name.startsWith("ap")
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