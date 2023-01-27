import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val minecraftMajorVersion: String by project
val minecraftMinorVersion: String by project
val spigotVersion: String by project
val projectVersion: String by project

group = "io.github.hyperdrivemc"
version = projectVersion

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("org.spigotmc:spigot-api:$minecraftMajorVersion.$minecraftMinorVersion-$spigotVersion")
}


tasks {

    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    processResources {
        eachFile {
            filter<org.apache.tools.ant.filters.ReplaceTokens>(mapOf(
                "tokens" to mapOf(
                    "version" to project.version,
                    "api-version" to minecraftMajorVersion
                )
            ))
        }
    }
}
