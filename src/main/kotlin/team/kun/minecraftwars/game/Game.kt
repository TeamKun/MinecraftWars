package team.kun.minecraftwars.game

import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Player
import team.kun.minecraftwars.MinecraftWars
import team.kun.minecraftwars.ext.getNbt
import team.kun.minecraftwars.ext.removeMeta
import team.kun.minecraftwars.ext.scheduleRunnable
import team.kun.minecraftwars.item.Horn
import team.kun.minecraftwars.metadata.BasicNbtKey
import team.kun.minecraftwars.metadata.MetadataKey
import team.kun.minecraftwars.mob.ZombieMob
import team.kun.minecraftwars.runnable.ItemRunnable
import team.kun.minecraftwars.rx.Observable

class Game {
    fun start(plugin: MinecraftWars) {
        plugin.server.worlds.forEach { world ->
            world.entities.filter {
                it.persistentDataContainer.getNbt(
                    plugin,
                    BasicNbtKey.IsMinecraftWarsEntity
                ) == 1.toByte()
            }.forEach { entity ->
                entity.remove()
            }
        }
        plugin.server.onlinePlayers.forEach { player ->
            player.removeMeta(plugin, MetadataKey.PlayerMobs)
            player.removeMeta(plugin, MetadataKey.PlayerMobs)
        }
        Observable.interval(20)
            .take(10)
            .doOnNext {
                plugin.server.onlinePlayers.forEach { player ->
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f)
                    player.sendTitle("${10 - it.toInt()}", "", 2, 50, 50)
                }
            }
            .doOnComplete {
                plugin.server.onlinePlayers.forEach { player ->
                    player.inventory.clear()
                    player.gameMode = GameMode.ADVENTURE
                    player.sendTitle("開始", "", 2, 50, 50)
                    player.inventory.addItem(Horn().toItemStack(plugin))
                    repeat(5) {
                        ZombieMob(player).spawn(player.location, plugin)
                    }
                }
                plugin.isStart = true
                plugin.scheduleRunnable(ItemRunnable(plugin), 20, 200)
            }
            .subscribe(plugin)
    }

    fun stop(plugin: MinecraftWars) {
        plugin.server.onlinePlayers.forEach { player ->
            player.sendTitle("中止しました", "", 2, 50, 50)
            player.playSound(player.location, Sound.BLOCK_BELL_USE, 1.0f, 1.0f)
        }
        plugin.isStart = false
    }

    fun end(winner: Player, plugin: MinecraftWars) {
        plugin.server.onlinePlayers.forEach { player ->
            player.sendTitle("${winner.name}の勝利", "", 2, 50, 50)
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
        }
        plugin.isStart = false
    }
}