package io.github.rbleuse.playground.serviceone

import io.github.rbleuse.playground.servicetwo.ServiceTwoApplication
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@SpringBootTest(
	classes = [ServiceOneApplication::class],
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class LookupFlowIntegrationTests @Autowired constructor(
	@LocalServerPort private val port: Int
) {
	@Test
	fun `POST lookups returns data retrieved from service two over grpc`() {
		val response = RestClient.create("http://localhost:$port")
			.post()
			.uri("/lookups")
			.contentType(MediaType.APPLICATION_JSON)
			.body("""{"subject":"spring"}""")
			.retrieve()
			.body<LookupResponse>()

		response shouldBe LookupResponse(
			subject = "spring",
			profileId = "profile-spring",
			displayName = "Spring Profile",
			source = "service-two-grpc",
		)
	}

	data class LookupResponse(
		val subject: String,
		val profileId: String,
		val displayName: String,
		val source: String,
	)

	companion object {
		private lateinit var serviceTwoContext: ConfigurableApplicationContext

		@JvmStatic
		@DynamicPropertySource
		fun serviceTwoGrpcProperties(registry: DynamicPropertyRegistry) {
			serviceTwoContext = SpringApplicationBuilder(ServiceTwoApplication::class.java)
				.properties(
					mapOf(
						"spring.application.name" to "service-two",
						"spring.grpc.server.port" to "0",
						"spring.main.web-application-type" to "none",
					),
				)
				.run()

			val grpcPort = serviceTwoContext.environment.getRequiredProperty("local.grpc.server.port")
			registry.add("spring.grpc.client.channel.profile-service.target") {
				"static://localhost:$grpcPort"
			}
		}

		@JvmStatic
		@AfterAll
		fun stopServiceTwo() {
			if (::serviceTwoContext.isInitialized) {
				serviceTwoContext.close()
			}
		}
	}
}
