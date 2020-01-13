rootProject.name = "FastAsyncWorldEdit"

plugins {
    id("com.gradle.build-scan").version("3.1.1")
}

include("worldedit-libs")

listOf("bukkit", "core").forEach {
    include("worldedit-libs:$it")
    include("worldedit-$it")
}
include("worldedit-libs:core:ap")
