object Dependencies {
    object Kotlin {
        val version = "1.4.10"
        val classpath = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Nms {
        val repository = "https://repo.codemc.io/repository/nms/"
    }

    object Spigot {
        val version = "1.16.5-R0.1-SNAPSHOT"
        val spigot = "org.spigotmc:spigot:$version"
        val api = "org.spigotmc:spigot-api:$version"
        val annotations = "org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT"
        val repository = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    }

    object Paper {
        val version = "1.16.5-R0.1-SNAPSHOT"
        val paper = "com.destroystokyo.paper:paper:$version"
        val api = "com.destroystokyo.paper:paper-api:$version"
        val repository = "https://papermc.io/repo/repository/maven-public/"
    }

    object SonaType {
        val repository = "https://oss.sonatype.org/content/groups/public/"
    }

    object ProtocolLib {
        val version = "4.5.1"
        val repository = "http://repo.dmulloy2.net/nexus/repository/public/"
        val core = "com.comphenix.protocol:ProtocolLib:$version"
    }

    object JUnit {
        val core = "org.junit.jupiter:junit-jupiter:5.5.2"
    }

    object MockBukkit {
        val version = "0.25.0"
        val repository = "https://hub.spigotmc.org/nexus/content/repositories/public/"
        val core = "com.github.seeseemelk:MockBukkit-v1.16:$version"
    }

    object MineInAbyss {
        val repository = "https://repo.mineinabyss.com/releases"

        object Mobzy {
            val version = "0.9.25"
            val core = "com.mineinabyss:mobzy:$version"
        }

        object Geary {
            val version = "0.4.43"
            val core = "com.mineinabyss:geary:$version"
            val spigot = "com.mineinabyss:geary-spigot:$version"
        }

        object Idofront {
            val version = "0.6.13"
            val nms = "com.mineinabyss:idofront-nms:$version"
        }
    }
}