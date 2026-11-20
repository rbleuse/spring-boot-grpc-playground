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

protobuf {
	// shortcut: Boot 4.2.0 misses empty generator versions; remove when automatic alignment is fixed.
	protoc {
		artifact = "com.google.protobuf:protoc:${dependencyManagement.importedProperties.getValue("protobuf-java.version")}"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:${dependencyManagement.importedProperties.getValue("grpc-java.version")}"
		}
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
