plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "art.galushko"
version = "0.1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml:jackson-xml-databind:0.6.2")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.squareup:kotlinpoet:1.13.0")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.5")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}