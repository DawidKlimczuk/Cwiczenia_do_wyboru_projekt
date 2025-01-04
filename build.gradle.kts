plugins {
    kotlin("jvm") version "1.9.10" // Wersja Kotlina
    application // Plugin aplikacji
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral() // Repozytorium Maven
}

dependencies {
    // Kotlin standard library
    implementation(kotlin("stdlib"))

    // Testy w Kotlinie
    testImplementation(kotlin("test"))

    // SQLite dla bazy danych
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")

    // JUnit 5 dla testów jednostkowych
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
}

tasks.test {
    useJUnitPlatform() // Użyj platformy JUnit 5
}

application {
    // Klasa główna aplikacji
    mainClass.set("main.MainKt")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(20)) // Ustawienie wersji Javy na 20
    }
}
