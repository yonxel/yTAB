enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral() // Netty, SnakeYaml, json-simple, Guava, Kyori event, bStats, AuthLib, LuckPerms
        maven("https://repo.viaversion.com/") // ViaVersion
        maven("https://repo.william278.net/releases/") // VelocityScoreboardAPI
        maven("https://repo.papermc.io/repository/maven-public/") // paperweight, Velocity
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
        maven("https://repo.opencollab.dev/maven-snapshots/") // Floodgate, Bungeecord-proxy
        maven("https://repo.purpurmc.org/snapshots") // Purpur
        maven("https://repo.spongepowered.org/repository/maven-public/") // Sponge
        maven("https://jitpack.io") // PremiumVanish, Vault, YamlAssist, RedisBungee
        maven("https://repo.md-5.net/content/groups/public/") // LibsDisguises
        maven("https://repo.codemc.org/repository/nms/") // CraftBukkit + NMS
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://maven.architectury.dev/")
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "TAB"

include(":api")
include(":shared")
include(":velocity")
include(":bukkit")
include(":bukkit:paper_1_20_5")
include(":bukkit:paper_1_21_1")
include(":bukkit:paper_1_21_2")
include(":bukkit:paper_1_21_4")
include(":bukkit:v1_8_R3")
include(":bukkit:v1_12_R1")
include(":bukkit:v1_16_R3")
include(":bukkit:v1_17_R1")
include(":bukkit:v1_18_R2")
include(":bukkit:v1_19_R1")
include(":bungeecord")
include(":component")
include(":sponge")
include(":fabric")
include(":neoforge")
include(":forge")
include(":jar")
