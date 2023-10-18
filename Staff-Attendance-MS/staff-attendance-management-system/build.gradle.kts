import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	id("org.jmailen.kotlinter") version "3.2.0"

}

ext["springBootVersion"] = "2.6.0"

group = "me.ponhrith"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
	implementation("org.springframework.security:spring-security-web:5.6.1")
	implementation("org.springframework.security:spring-security-config:5.6.1")
	implementation("org.springframework.security:spring-security-core:5.6.1")
	implementation("org.springframework.boot:spring-boot-starter-test")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	runtimeOnly("com.h2database:h2")
	implementation("org.postgresql:postgresql")

	testImplementation("org.apache.httpcomponents.client5:httpclient5")
	testImplementation("org.springframework.security:spring-security-crypto")
	testImplementation("org.springframework.boot:spring-boot-starter-mail")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}





