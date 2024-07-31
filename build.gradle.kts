plugins {
    kotlin("jvm") version "2.0.20-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
//    id("proguard")
}

group = "top.mckingdom"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.auxilor.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/nms/")
    maven("https://repo.essentialsx.net/releases/")

    maven("https://jitpack.io") {
        content { includeGroupByRegex("com\\.github\\..*") }
    }

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly(files("libs/kotlin-stdlib-2.0.20-Beta2-remapped.jar"))
    compileOnly(files("libs/KingdomsX-1.17.0-ALPHA.jar"))
    compileOnly(files("libs/xseries-11.2.0.1-remapped.jar"))
    compileOnly(files("libs/hikari-5.1.0-remapped.jar"))
    compileOnly(files("libs/guava-33.2.1-jre-remapped.jar"))
    compileOnly(files("libs/gson-2.11.0-remapped.jar"))
    compileOnly(files("libs/caffeine-3.1.8-remapped.jar"))
//    compileOnly "com.github.cryptomorin:kingdoms:1.16.8.1.1";
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8");
    compileOnly("org.bstats:bstats-bukkit:3.0.2")


    compileOnly(files("libs/PlayerInv-3.2.09.jar"))
    compileOnly("cn.superiormc.enchantmentslots:EnchantmentSlots:1.0.0")
    compileOnly(files("libs/EnchantmentSlots-2.4.0.jar"))
    compileOnly(files("libs/original-EnchantSeparate-1.0-SNAPSHOT.jar"))
    compileOnly(files("libs/[苦力怕论坛]Snow-EnchantSP-1.1.jar"))
    compileOnly("mc.rellox:ExtractableEnchantments:11.2")

}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {

//    shadowJar {
//        relocate("kotlin", "org.kingdoms.libs.kotlin")
//    }

    build {
        dependsOn("shadowJar")
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    shadowJar {
        dependencies {
            exclude("org.jetbrains:annotations")
        }
    }


}

