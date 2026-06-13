plugins {
	kotlin("jvm") version "2.4.0" apply false
	kotlin("plugin.spring") version "2.4.0" apply false
	id("org.springframework.boot") version "4.1.0" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("com.google.protobuf") version "0.10.0" apply false
}

group = "io.github.rbleuse"
version = "0.0.1-SNAPSHOT"

allprojects {
	group = rootProject.group
	version = rootProject.version

	repositories {
		mavenCentral()
	}
}

subprojects {
	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

tasks.register("bootRunAll") {
	group = "application"
	description = "Runs service-one and service-two together. Use --parallel so both long-running bootRun tasks can stay active."
	dependsOn(":service-two:bootRun", ":service-one:bootRun")
}
