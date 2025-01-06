package org.cmpbachelor.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform