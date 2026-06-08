plugins {
	`java-library`
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("com.google.protobuf")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

dependencies {
	api("io.grpc:grpc-protobuf")
	api("io.grpc:grpc-stub")
	api("com.google.protobuf:protobuf-java")
	runtimeOnly("com.google.protobuf:protobuf-java-util")
	runtimeOnly("io.grpc:grpc-util")
	compileOnly("jakarta.annotation:jakarta.annotation-api")
}

tasks.bootJar {
	enabled = false
}

tasks.jar {
	enabled = true
}
