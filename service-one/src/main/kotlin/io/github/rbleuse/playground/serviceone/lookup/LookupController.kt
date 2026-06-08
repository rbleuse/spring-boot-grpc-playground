package io.github.rbleuse.playground.serviceone.lookup

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lookups")
class LookupController(
	private val profileClient: ProfileClient,
) {

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	fun lookup(@Valid @RequestBody request: LookupRequest): LookupResponse {
		val profile = profileClient.getProfile(request.subject)
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
