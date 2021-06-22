package team.kun.minecraftwars.mob

import com.mineinabyss.idofront.nms.aliases.NMSEntityCreature
import com.mineinabyss.idofront.nms.aliases.NMSEntityType
import com.mineinabyss.idofront.nms.aliases.toBukkit
import com.mineinabyss.idofront.nms.aliases.toNMS
import net.minecraft.server.v1_16_R3.ItemStack
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.ext.setMeta
import team.kun.minecraftwars.ext.setNbt
import team.kun.minecraftwars.metadata.BasicNbtKey
import team.kun.minecraftwars.metadata.MetadataKey

abstract class ServantMob(
    private val owner: Player,
    entityTypes: NMSEntityType<out NMSEntityCreature>
) : NMSEntityCreature(entityTypes, owner.world.toNMS()) {
    fun spawn(location: Location, plugin: JavaPlugin) {
        val world = location.world.toNMS()
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
        setLocation(location.x, location.y, location.z, location.yaw, location.pitch)

        val entity = toBukkit()
        entity.isCustomNameVisible = true
        entity.customName = owner.name
        entity.isCollidable = false
        this::class.simpleName?.let {
            entity.setMeta(plugin, MetadataKey.Name, it)
        }

        entity.setMeta(plugin, MetadataKey.Owner, owner)
        entity.persistentDataContainer.setNbt(plugin, BasicNbtKey.IsMinecraftWarsEntity, 1)

        val playerMobs = owner.getMeta(MetadataKey.PlayerMobs, listOf()).toMutableList()
        playerMobs.add(entity)
        owner.setMeta(plugin, MetadataKey.PlayerMobs, playerMobs)

        onSpawn(owner, plugin)
    }

    fun dead(killer: Entity, plugin: JavaPlugin) {
        val entity = toBukkit()
        val playerMobs = owner.getMeta(MetadataKey.PlayerMobs, listOf()).toMutableList()
        playerMobs.remove(entity)
        owner.setMeta(plugin, MetadataKey.PlayerMobs, playerMobs)

        if (entity is Player) {
            onDead(entity, plugin)
        }
    }

    abstract fun onSpawn(owner: Player, plugin: JavaPlugin)

    abstract fun onDead(killer: Player, plugin: JavaPlugin)

    override fun canPickup(itemstack: ItemStack?): Boolean {
        return false
    }

    override fun isCollidable(): Boolean {
        return false
    }
}