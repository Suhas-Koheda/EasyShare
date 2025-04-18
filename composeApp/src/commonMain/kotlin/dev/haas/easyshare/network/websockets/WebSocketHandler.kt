package dev.haas.easyshare.network.websockets

expect object WebSocketUtil{
    fun startLocalServer(): Boolean
    fun startLocalClient():Boolean
}