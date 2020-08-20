import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val micronautVersion = "2.0.1"
val kotlinVersion = "1.4.0"

plugins {
    application
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("jvm") version "1.4.0"
    kotlin("kapt") version "1.4.0"
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
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")

    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")

    runtimeOnly("ch.qos.logback:logback-classic")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    kaptTest(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")

    testImplementation(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
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
        jvmTarget = "11"
        javaParameters = true
    }
}
