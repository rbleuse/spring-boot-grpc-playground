package io.github.rbleuse.playground.servicetwo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(proxyBeanMethods = false)
class ServiceTwoApplication

fun main(args: Array<String>) {
	runApplication<ServiceTwoApplication>(*args)
}
