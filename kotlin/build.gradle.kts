plugins {
    kotlin("jvm") version "2.1.21"
}

group = "de.activegroup"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}