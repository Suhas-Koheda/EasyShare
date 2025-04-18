package dev.haas.easyshare.network

import java.net.NetworkInterface

actual fun getBroadcastAddress() : List<String>{
    var networkBroadcastAddresses = mutableListOf<String>()
    try{
        NetworkInterface.getNetworkInterfaces()
            .asSequence()
            .filter { it.name.startsWith("eth") || it.name.startsWith("ap")}
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