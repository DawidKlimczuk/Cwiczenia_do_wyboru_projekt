pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.0.21" // Ustawienie wersji Kotlina
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0" // Wtyczka do zarządzania środowiskami JDK
}

rootProject.name = "Komunikator" // Nazwa projektu głównego