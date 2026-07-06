package io.github.rbleuse.playground.serviceone.lookup

import io.github.rbleuse.playground.contract.profile.v1.ProfileRequest
import io.github.rbleuse.playground.contract.profile.v1.ProfileServiceGrpc
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/lookups")
class LookupController(
	private val profileStub: ProfileServiceGrpc.ProfileServiceBlockingStub,
) {

	@PostMapping
	fun lookup(@Valid @RequestBody request: LookupRequest): LookupResponse {
		val profile = profileStub.withDeadlineAfter(2, TimeUnit.SECONDS).getProfile(
			ProfileRequest.newBuilder()
				.setSubject(request.subject)
				.build(),
		)
		return LookupResponse(
			subject = request.subject,
			profileId = profile.id,
			displayName = profile.displayName,
			source = profile.source,
		)
	}
}

data class LookupRequest(
	@field:NotBlank
	val subject: String,
)

data class LookupResponse(
	val subject: String,
	val profileId: String,
	val displayName: String,
	val source: String,
)
