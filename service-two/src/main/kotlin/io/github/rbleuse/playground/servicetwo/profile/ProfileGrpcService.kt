package io.github.rbleuse.playground.servicetwo.profile

import io.github.rbleuse.playground.contract.profile.v1.ProfileRequest
import io.github.rbleuse.playground.contract.profile.v1.ProfileResponse
import io.github.rbleuse.playground.contract.profile.v1.ProfileServiceGrpc
import io.grpc.stub.StreamObserver
import org.springframework.grpc.server.service.GrpcService

@GrpcService
class ProfileGrpcService : ProfileServiceGrpc.ProfileServiceImplBase() {

	override fun getProfile(
		request: ProfileRequest,
		responseObserver: StreamObserver<ProfileResponse>,
	) {
		val subject = request.subject.trim()
		val response = ProfileResponse.newBuilder()
			.setId("profile-$subject")
			.setDisplayName("${subject.replaceFirstChar(Char::uppercase)} Profile")
			.setSource("service-two-grpc")
			.build()

		responseObserver.onNext(response)
		responseObserver.onCompleted()
	}
}
