package dev.jonaz.server

import io.micronaut.runtime.Micronaut.*

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        build(*args)
                .mainClass(Application.javaClass)
                .start()
    }
}

