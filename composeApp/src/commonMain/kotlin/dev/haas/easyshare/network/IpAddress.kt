package dev.haas.easyshare.network

expect object NetworkUtils {
    fun getBroadcastAddress(): List<String>
    fun getLocalIpAddress(): String
}