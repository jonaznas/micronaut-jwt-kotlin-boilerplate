import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val coroutinesVersion = "1.3.9"
val micronautVersion = "2.0.0"
val kotlinVersion = "1.4.0"
val exposedVersion = "0.25.1"
val serializationVersion = "1.0.0-RC"

plugins {
    application
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("jvm") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
}

version = "0.1"
group = "dev.jonaz.server"
application.mainClassName = "$group/ApplicationKt"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut.security:micronaut-security-annotations")
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")

    compileOnly(platform("io.micronaut:micronaut-bom:$micronautVersion"))

    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${serializationVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")

    implementation("org.jetbrains.exposed:exposed-core:${exposedVersion}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${exposedVersion}")
    implementation("org.jetbrains.exposed:exposed-java-time:${exposedVersion}")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("com.zaxxer:HikariCP:3.2.0")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

kapt {
    arguments {
        arg("micronaut.processing.incremental", true)
        arg("micronaut.processing.annotations", "dev.jonaz.server.*")
        arg("micronaut.processing.group", "dev.jonaz.server")
        arg("micronaut.processing.module", "micronautBoilerplate")
    }
}

tasks.named<ShadowJar>("shadowJar") {
    mergeServiceFiles()

    manifest {
        attributes(mapOf("Main-Class" to "$group/ApplicationKt"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        javaParameters = true
    }
}
