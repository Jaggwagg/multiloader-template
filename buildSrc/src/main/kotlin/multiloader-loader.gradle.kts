plugins {
    id("multiloader-common")
}

val commonJava by configurations.creating {
    isCanBeResolved = true
}

val commonResources by configurations.creating {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common")) {
        capabilities {
            requireCapability("${project.group}:${project.findProperty("mod_id")}")
        }
    }
    commonJava(project(path = ":common", configuration = "commonJava"))
    commonResources(project(path = ":common", configuration = "commonResources"))
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(commonJava)
    source(commonJava)
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(commonResources)
    from(commonResources)
}

tasks.named<Javadoc>("javadoc") {
    dependsOn(commonJava)
    source(commonJava)
}

tasks.named<Jar>("sourcesJar") {
    dependsOn(commonJava, commonResources)
    from(commonJava)
    from(commonResources)
}
