package team.kun.minecraftwars.listener

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import team.kun.minecraftwars.MinecraftWars
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.ext.setMeta
import team.kun.minecraftwars.game.Game
import team.kun.minecraftwars.item.*
import team.kun.minecraftwars.metadata.MetadataKey
import team.kun.minecraftwars.metadata.PlayerFlagMetadata

class PlayerListener(private val plugin: MinecraftWars) : Listener {
    private val playerFlagMetadata = PlayerFlagMetadata(plugin)

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        if (playerFlagMetadata.getFlag(player)) {
            return
        }
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            if (Horn().equal(event.player.inventory.itemInMainHand, plugin)) {
                playerFlagMetadata.avoidTwice(player)
                Horn().execute(player, plugin)
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onClick(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        if (playerFlagMetadata.getFlag(player)) {
            return
        }
        if (Horn().equal(event.player.inventory.itemInMainHand, plugin)) {
            playerFlagMetadata.avoidTwice(player)
            Horn().execute(player, plugin)
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPickUp(event: EntityPickupItemEvent) {
        val entity = event.entity
        val item = event.item
        if (entity is Player) {
            if (ZombieItem().equal(item.itemStack, plugin)) {
                ZombieItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (WitherSkeletonItem().equal(item.itemStack, plugin)) {
                WitherSkeletonItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (IronItem().equal(item.itemStack, plugin)) {
                IronItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (DiamondItem().equal(item.itemStack, plugin)) {
                DiamondItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (NetheriteItem().equal(item.itemStack, plugin)) {
                NetheriteItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (GolemItem().equal(item.itemStack, plugin)) {
                GolemItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (VexItem().equal(item.itemStack, plugin)) {
                VexItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            } else if (HuskItem().equal(item.itemStack, plugin)) {
                HuskItem().pickUp(entity, plugin)
                item.remove()
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val playerMobs = player.getMeta(MetadataKey.PlayerMobs, listOf()).toMutableList()
        if (playerMobs.filterNot { it.isDead }.size > 0) {
            val entity = playerMobs.filterNot { it.isDead }.firstOrNull()
            entity?.remove()
            playerMobs.remove(entity)
            player.setMeta(plugin, MetadataKey.PlayerMobs, playerMobs)

            player.health = 20.0
            event.isCancelled = true
            return
        }
        player.gameMode = GameMode.SPECTATOR

        val players = plugin.server.onlinePlayers
            .filterNot { it.gameMode == GameMode.CREATIVE }
            .filterNot { it.gameMode == GameMode.SPECTATOR }
        if (players.size == 1) {
            Game().end(players.first(), plugin)
        }
    }
}