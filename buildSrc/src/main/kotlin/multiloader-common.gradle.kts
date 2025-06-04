plugins {
    id("java-library")
    id("maven-publish")
}

base {
    archivesName.set("${project.findProperty("mod_id")}-${project.name}-${project.findProperty("minecraft_version")}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(project.findProperty("java_version").toString().toInt()))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()

    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public")
            }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }

    exclusiveContent {
        forRepositories(
            maven {
                name = "ParchmentMC"
                url = uri("https://maven.parchmentmc.org/")
            },
            maven {
                name = "NeoForge"
                url = uri("https://maven.neoforged.net/releases")
            }
        )
        filter { includeGroup("org.parchmentmc.data") }
    }
}

listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.named(variant) {
        outgoing {
            capability("${project.group}:${project.name}:${project.version}")
            capability("${project.group}:${base.archivesName.get()}:${project.version}")
            capability("${project.group}:${project.findProperty("mod_id")}-${project.name}-${project.findProperty("minecraft_version")}:${project.version}")
            capability("${project.group}:${project.findProperty("mod_id")}:${project.version}")
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
                suppressPomMetadataWarningsFor(variant)
            }
        }
    }
}

tasks.named<Jar>("sourcesJar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${project.findProperty("mod_name")}" }
    }
}

tasks.named<Jar>("jar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${project.findProperty("mod_name")}" }
    }

    manifest {
        attributes(
            "Specification-Title" to project.findProperty("mod_name"),
            "Specification-Vendor" to project.findProperty("mod_author"),
            "Specification-Version" to archiveVersion.get(),
            "Implementation-Title" to project.name,
            "Implementation-Version" to archiveVersion.get(),
            "Implementation-Vendor" to project.findProperty("mod_author"),
            "Built-On-Minecraft" to project.findProperty("minecraft_version")
        )
    }
}

tasks.named<ProcessResources>("processResources") {
    val expandProps = mapOf(
        "version" to project.version,
        "group" to project.group,
        "minecraft_version" to project.findProperty("minecraft_version"),
        "minecraft_version_range" to project.findProperty("minecraft_version_range"),
        "fabric_version" to project.findProperty("fabric_version"),
        "fabric_loader_version" to project.findProperty("fabric_loader_version"),
        "mod_name" to project.findProperty("mod_name"),
        "mod_author" to project.findProperty("mod_author"),
        "mod_id" to project.findProperty("mod_id"),
        "license" to project.findProperty("license"),
        "description" to project.description,
        "neoforge_version" to project.findProperty("neoforge_version"),
        "neoforge_loader_version_range" to project.findProperty("neoforge_loader_version_range"),
        "credits" to project.findProperty("credits"),
        "java_version" to project.findProperty("java_version")
    )

    val jsonExpandProps = expandProps.mapValues { (_, value) ->
        if (value is String) value.replace("\n", "\\\\n") else value
    }

    filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
        expand(expandProps)
    }

    filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
        expand(jsonExpandProps)
    }

    inputs.properties(expandProps)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
    repositories {
        val localMavenUrl = System.getenv("local_maven_url")
        if (!localMavenUrl.isNullOrBlank()) {
            maven {
                url = uri(localMavenUrl)
            }
        }
    }
}
