package io.github.rbleuse.playground.serviceone

import io.github.rbleuse.playground.contract.profile.v1.ProfileServiceGrpc
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.grpc.client.ImportGrpcClients

@SpringBootApplication(proxyBeanMethods = false)
@ImportGrpcClients(
	target = "profile-service",
	types = [ProfileServiceGrpc.ProfileServiceBlockingStub::class],
)
class ServiceOneApplication

fun main(args: Array<String>) {
	runApplication<ServiceOneApplication>(*args)
}
