plugins {
    id("multiloader-loader")
    id("net.neoforged.moddev")
}

neoForge {
    version = property("neoforge_version") as String

    // Automatically enable AccessTransformers if the file exists
    val at = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    parchment {
        minecraftVersion = property("parchment_minecraft") as String
        mappingsVersion = property("parchment_version") as String
    }

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", property("mod_id") as String)
            ideName = "NeoForge ${name.replaceFirstChar { it.uppercase() }} (${project.path})"
        }

        register("client") {
            client()
        }

        register("data") {
            data()
        }

        register("server") {
            server()
        }
    }

    mods {
        create(property("mod_id") as String) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}