pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
        maven("https://maven.ornithemc.net/releases") { name = "Ornithe Releases" }
        maven("https://maven.ornithemc.net/snapshots") { name = "Ornithe Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7-beta.7"
}

stonecutter {
    create(rootProject) {
        versions("1.8.9", "1.9.4", "1.11.2", "1.13.2")
        vcsVersion = "1.8.9"
    }
}

rootProject.name = "ornithe-ears"