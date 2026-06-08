package io.github.rbleuse.playground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootGrpcPlaygroundApplication

fun main(args: Array<String>) {
	runApplication<SpringBootGrpcPlaygroundApplication>(*args)
}
