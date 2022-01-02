import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.10"

    id("com.diffplug.spotless") version "6.1.0"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")

    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    // https://github.com/harawata/appdirs
    implementation("net.harawata:appdirs:1.2.1")
    implementation("com.github.ajalt.clikt:clikt:3.3.0")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")

    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
}

application {
    // Define the main class for the application.
    mainClass.set("advent.of.code.AppKt")
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

tasks.withType<Test>() {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }
}
