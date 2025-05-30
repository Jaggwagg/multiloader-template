plugins {
    id("multiloader-loader")
    id("fabric-loom")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${property("parchment_minecraft")}:${property("parchment_version")}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
}

loom {
    val aw = project(":common").file("src/main/resources/${property("mod_id")}.accesswidener")
    if (aw.exists()) {
        accessWidenerPath.set(aw)
    }

    mixin {
        defaultRefmapName.set("${property("mod_id")}.refmap.json")
    }

    runs {
        named("client") {
            client()
            setIdeConfigGenerated(true)
            configName = "Fabric Client"
            runDir = "runs/client"
        }
        named("server") {
            server()
            setIdeConfigGenerated(true)
            configName = "Fabric Server"
            runDir = "runs/server"
        }
    }
}