import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
    id("com.google.protobuf").version("0.8.8")
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.30")

    api("commons-codec:commons-codec:1.11")
    api("com.github.kklongming:sz-scaffold:unspecified")
    api("com.google.protobuf:protobuf-java:3.6.1")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.kklongming"

            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.6.1"
    }

    generatedFilesBaseDir = "$projectDir/src/generated"
}

sourceSets {
    main {
        java {
            srcDirs("${projectDir.path}/src/generated/main/java", "${projectDir.path}/src/main/kotlin")
        }
    }
}

tasks.register<Delete>("cleanGenerated") {
    this.delete("$projectDir/src/generated/main/java")
    this.isFollowSymlinks = true
}