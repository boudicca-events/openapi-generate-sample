import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm") version "1.9.21"
    id("org.openapi.generator") version "7.2.0"
}

group = "events.boudicca.samples"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

val openapiSpec: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    openapiSpec("events.boudicca:search-api:0.2.0:openapi@json")
}

tasks.register<GenerateTask>("generateTypescriptClient") {
    inputs.files(openapiSpec)
    inputSpec.set(provider { openapiSpec.files.firstOrNull()?.path })
    outputDir.set(layout.buildDirectory.dir("generated/typescript").get().toString())
    generatorName.set("typescript-axios")
    configOptions.putAll(
        mapOf(
            "npmName" to "@boudicca/search-api-client",
            "npmVersion" to "${project.version}",
            "supportsES6" to "true",
        )
    )
}