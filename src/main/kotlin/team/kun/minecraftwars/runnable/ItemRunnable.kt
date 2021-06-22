package team.kun.minecraftwars.runnable

import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable
import team.kun.minecraftwars.MinecraftWars
import team.kun.minecraftwars.ext.random
import team.kun.minecraftwars.item.*

class ItemRunnable(private val plugin: MinecraftWars) : BukkitRunnable() {
    private var count: Int = 0

    private val itemTables: Map<Int, List<PickUpItem>> = mapOf(
        0 to listOf(
            ZombieItem(),
        ),
        1 to listOf(
            ZombieItem(),
            WitherSkeletonItem(),
        ),
        2 to listOf(
            ZombieItem(),
            WitherSkeletonItem(),
            IronItem(),
        ),
        3 to listOf(
            ZombieItem(),
            WitherSkeletonItem(),
            GolemItem(),
            IronItem(),
        ),
        4 to listOf(
            ZombieItem(),
            WitherSkeletonItem(),
            GolemItem(),
            VexItem(),
            DiamondItem(),
        ),
        5 to listOf(
            ZombieItem(),
            WitherSkeletonItem(),
            GolemItem(),
            VexItem(),
            HuskItem(),
            NetheriteItem(),
        ),
    )

    override fun run() {
        if (!plugin.isStart) {
            plugin.server.scheduler.cancelTask(taskId)
        }
        var level = count / 10
        if (level > 5) {
            level = 5
        }
        plugin.server.onlinePlayers
            .filterNot { it.gameMode == GameMode.CREATIVE }
            .filterNot { it.gameMode == GameMode.SPECTATOR }
            .forEach { player ->
                val itemTable = itemTables[level]
                repeat(level + 1) {
                    val location = player.location.random(40.0, 0.0, 40.0).toHighestLocation()
                    itemTable?.randomOrNull()?.let { item ->
                        item.spawn(location, plugin)
                    }
                }
            }
        count += 1
    }
}