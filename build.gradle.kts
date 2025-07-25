import net.ornithemc.ploceus.api.GameSide

plugins {
    `maven-publish`
    id("fabric-loom")
    id("ploceus")

    kotlin("jvm") version "2.2.0"
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.12"
    id("com.gradleup.shadow") version "9.0.0-rc1"
    // id("me.modmuss50.mod-publish-plugin")
}

val mc = if (hasProperty("deps.minecraft"))
    property("deps.minecraft").toString() else stonecutter.current.version

version = "${property("mod.version")}+${mc}"
group = property("mod.group") as String
base.archivesName = property("mod.id") as String

val useClient = stonecutter.eval(stonecutter.current.version, "<1.3")

if (useClient) {
    loom {
        clientOnlyMinecraftJar()
    }

    ploceus {
        @Suppress("DEPRECATION")
        clientOnlyMappings()
    }
}

repositories {
    /**
     * Restricts dependency search of the given [groups] to the [maven URL][url],
     * improving the setup speed.
     */
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    maven("https://repo.unascribed.com") {
        content {
            includeGroup("com.unascribed")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${mc}")
    mappings(ploceus.featherMappings(property("deps.feather").toString()))
    if (hasProperty("deps.raven"))
        exceptions(ploceus.raven(property("deps.raven").toString()))
    if (hasProperty("deps.sparrow"))
        signatures(ploceus.sparrow(property("deps.sparrow").toString()))
    if (hasProperty("deps.nests"))
        nests(ploceus.nests(property("deps.nests").toString()))

    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    if (hasProperty("deps.ears.target")) {
        property("deps.ears.target").toString().split(",").forEach {
            implementation("com.unascribed:ears-common:${property("deps.ears")}:${it}")
        }
    } else {
        implementation("com.unascribed:ears-common:${property("deps.ears")}")
        implementation("com.unascribed:ears-api:${property("deps.ears")}")
    }

    ploceus.dependOsl(property("deps.osl").toString(),
        if (useClient) GameSide.CLIENT else GameSide.MERGED)
}

loom {
    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        ideConfigGenerated(true)
        vmArgs("-Dmixin.debug.export=true") // Exports transformed classes for debugging
        runDir = "../../run" // Shares the run directory between versions
    }
}

java {
    withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

stonecutter {
    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("net.minecraft.resource.Identifier", "net.minecraft.client.resource.Identifier")
    }

    replacements.regex {
        direction = eval(current.version, ">=1.6")
        replace("(.)InputClientPlayerEntity(.)", "$1ClientPlayerEntity$2")
        reverse("(.)ClientPlayerEntity(.)", "$1InputClientPlayerEntity$2")
    }

    replacements.string {
        direction = eval(current.version, "<1.3")
        replace("ClientPlayerEntity", "InputPlayerEntity")
    }

    replacements.string {
        direction = eval(current.version, "<1.3")
        replace("Minecraft.getInstance()", "OrnitheEars.MINECRAFT")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.bindTexture(", "GL11.glBindTexture(GL11.GL_TEXTURE_2D, ")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.enableBlend()", "GL11.glEnable(GL11.GL_BLEND)")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.disableBlend()", "GL11.glDisable(GL11.GL_BLEND)")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.translate", "GL11.glTranslate")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.rotate", "GL11.glRotate")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.scale", "GL11.glScale")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace("GlStateManager.blendFunc", "GL11.glBlendFunc")
    }

    replacements.string {
        direction = eval(current.version, "<1.8")
        replace(
            "Tessellator.getInstance().end()",
            "BufferBuilder.INSTANCE.end()"
        )
    }
}

fletchingTable {
    mixins.register("main") {
        mixin(alias = "default", file = "ornitheears.mixins.json")
    }
}

tasks {
    processResources {
        inputs.property("id", project.property("mod.id"))
        inputs.property("name", project.property("mod.name"))
        inputs.property("version", version)
        inputs.property("minecraft", project.property("mod.mc_dep"))

        val props = mapOf(
            "id" to project.property("mod.id"),
            "name" to project.property("mod.id"),
            "version" to version,
            "minecraft" to project.property("mod.mc_dep")
        )

        filesMatching("fabric.mod.json") { expand(props) }
    }

    var outDir = rootProject.layout.buildDirectory.dir("libs")
    shadowJar {
        dependencies {
            exclude { !it.allModuleArtifacts.any() { p -> p.name.startsWith("ears-") } }
        }
    }

    remapJar {
        input.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        destinationDirectory = outDir
    }

    remapSourcesJar {
        destinationDirectory = outDir
    }

    // Builds the version into a shared folder in `build/libs/${mod version}/`
    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile }, remapSourcesJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

/*
publishMods {
    file = tasks.remapJar.get().archiveFile
    additionalFiles.from(tasks.remapSourcesJar.get().archiveFile)
    displayName = "${mod.name} ${mod.version} for $mcVersion"
    version = mod.version
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    dryRun = providers.environmentVariable("MODRINTH_TOKEN")
        .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
    }

    curseforge {
        projectId = property("publish.curseforge").toString()
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
    }
}
*/
/*
publishing {
    repositories {
        maven("...") {
            name = "..."
            credentials(PasswordCredentials::class.java)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "${property("mod.group")}.${mod.id}"
            artifactId = mod.version
            version = mcVersion

            from(components["java"])
        }
    }
}
*/