package io.github.rbleuse.playground.serviceone.lookup

import io.github.rbleuse.playground.contract.profile.v1.ProfileRequest
import io.github.rbleuse.playground.contract.profile.v1.ProfileServiceGrpc
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ProfileClient(
	private val stub: ProfileServiceGrpc.ProfileServiceBlockingStub,
) {

	fun getProfile(subject: String): Profile {
		val response = stub.withDeadlineAfter(2, TimeUnit.SECONDS).getProfile(
			ProfileRequest.newBuilder()
				.setSubject(subject)
				.build(),
		)

		return Profile(
			id = response.id,
			displayName = response.displayName,
			source = response.source,
		)
	}

	data class Profile(
		val id: String,
		val displayName: String,
		val source: String,
	)
}
