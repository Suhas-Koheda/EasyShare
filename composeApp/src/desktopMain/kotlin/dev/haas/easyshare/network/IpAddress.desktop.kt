package dev.haas.easyshare.network

import java.net.NetworkInterface
import java.util.Arrays
import kotlin.streams.asSequence

actual fun getBroadcastAddress() : List<String>{
    var networkBroadcastAddresses = mutableListOf<String>()
    try{
        NetworkInterface.networkInterfaces()
            .asSequence()
            .filter { it.name == "wlan0" || it.name == "wlp0s20f3" }
            .flatMap { it.interfaceAddresses  }
            .forEach {
                networkBroadcastAddresses.add(it?.broadcast.toString())
            }
    }
    catch(e: Exception){
        println(e.message)
    }
    println(networkBroadcastAddresses.toTypedArray().contentToString())
    return networkBroadcastAddresses
}