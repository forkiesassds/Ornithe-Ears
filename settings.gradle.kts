pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
        maven("https://maven.ornithemc.net/releases") { name = "Ornithe Releases" }
        maven("https://maven.ornithemc.net/snapshots") { name = "Ornithe Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.3-beta.2"
}

stonecutter {
    create(rootProject) {
        versions("1.0.0-beta.7.3", "1.0.0-beta.8.1", "1.0.0", "1.3.2", "1.4.7", "1.5.2", "1.6.4", "1.7.10", "1.8.9", "1.9.4", "1.11.2", "1.13.2")
        vcsVersion = "1.8.9"
    }
}

rootProject.name = "ornithe-ears"