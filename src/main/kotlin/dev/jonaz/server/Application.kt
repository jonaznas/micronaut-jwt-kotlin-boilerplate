package dev.jonaz.server

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("dev.jonaz.server")
		.start()
}

