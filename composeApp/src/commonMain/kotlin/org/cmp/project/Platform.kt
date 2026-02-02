package org.cmp.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform