package dev.haas.easyshare

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform