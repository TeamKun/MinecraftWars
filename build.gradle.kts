import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm").version(Dependencies.Kotlin.version)
    kotlin("kapt").version(Dependencies.Kotlin.version)
}

group = "team.kun"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


repositories {
    jcenter()
    mavenCentral()
    maven(Dependencies.Nms.repository)
    maven(Dependencies.Spigot.repository)
    maven(Dependencies.Paper.repository)
    maven(Dependencies.SonaType.repository)
    maven(Dependencies.ProtocolLib.repository)
    maven(Dependencies.MockBukkit.repository)
    maven(Dependencies.MineInAbyss.repository)
}

dependencies {
    compileOnly(Dependencies.Paper.paper)
    compileOnly(Dependencies.Paper.api)
    compileOnly(Dependencies.Spigot.annotations)
    kapt(Dependencies.Spigot.annotations)
    compile(Dependencies.Kotlin.stdlib)
    compile(Dependencies.Kotlin.reflect)
    compileOnly(Dependencies.ProtocolLib.core) {
        exclude("com.comphenix.executors", "BukkitExecutors")
    }
    compileOnly(Dependencies.MineInAbyss.Geary.core)
    compileOnly(Dependencies.MineInAbyss.Geary.spigot)
    compile(Dependencies.MineInAbyss.Idofront.nms)
    testCompile(Dependencies.JUnit.core)
    testCompile(Dependencies.MockBukkit.core)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.classpath)
    }
}

tasks {
    withType<Jar> {
        from(configurations.getByName("compile").map { if (it.isDirectory) it else zipTree(it) })
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}