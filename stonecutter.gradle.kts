plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.10-SNAPSHOT" apply false
    id("ploceus") version "1.10-SNAPSHOT" apply false
    // id("me.modmuss50.mod-publish-plugin") version "0.8.+" apply false // Publishes builds to hosting websites
}

stonecutter active "1.8.9"

/*
// Make newer versions be published last
stonecutter tasks {
    order("publishModrinth")
    order("publishCurseforge")
}
 */

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    swaps["mod_version"] = "\"" + property("mod.version") + "\";"
    swaps["minecraft"] = "\"" + node.metadata.version + "\";"
    constants["release"] = property("mod.id") != "template"
    dependencies["osl"] = node.project.property("deps.osl") as String
}
