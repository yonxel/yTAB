import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
}

val platforms = setOf(
    rootProject.projects.bukkit,
    rootProject.projects.bukkit.paper1205,
    rootProject.projects.bukkit.paper1211,
    rootProject.projects.bukkit.paper1212,
    rootProject.projects.bukkit.paper1214,
    rootProject.projects.bukkit.v18R3,
    rootProject.projects.bukkit.v112R1,
    rootProject.projects.bukkit.v116R3,
    rootProject.projects.bukkit.v117R1,
    rootProject.projects.bukkit.v118R2,
    rootProject.projects.bukkit.v119R1,
    rootProject.projects.bungeecord,
    rootProject.projects.velocity,
    rootProject.projects.sponge
).map { it.dependencyProject }

val moddedPlatforms = setOf(
    rootProject.projects.fabric,
    rootProject.projects.neoforge,
    rootProject.projects.forge
).map { it.dependencyProject }

tasks {
    shadowJar {
        archiveFileName.set("TAB-${project.version}.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        fun registerPlatform(project: Project, shadeTask: org.gradle.jvm.tasks.Jar) {
            dependsOn(shadeTask)
            dependsOn(project.tasks.withType<Jar>())
            from(zipTree(shadeTask.archiveFile))
        }

        platforms.forEach {
            registerPlatform(it, it.tasks.named<ShadowJar>("shadowJar").get())
        }

        moddedPlatforms.forEach {
            registerPlatform(it, it.tasks.named<org.gradle.jvm.tasks.Jar>("remapJar").get())
        }
    }
    build.get().dependsOn(shadowJar)
}
