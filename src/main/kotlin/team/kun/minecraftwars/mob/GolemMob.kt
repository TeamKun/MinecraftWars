package team.kun.minecraftwars.mob

import com.mineinabyss.idofront.nms.aliases.NMSEntityType
import com.mineinabyss.idofront.nms.aliases.toBukkit
import com.mineinabyss.idofront.nms.aliases.toNMS
import com.mineinabyss.idofront.nms.pathfindergoals.removePathfinderGoal
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.ext.setMeta
import team.kun.minecraftwars.ext.setNbt
import team.kun.minecraftwars.item.HeadItem
import team.kun.minecraftwars.metadata.BasicNbtKey
import team.kun.minecraftwars.metadata.MetadataKey
import java.util.function.Predicate

class GolemMob(private val owner: Player) : EntityIronGolem(NMSEntityType.IRON_GOLEM, owner.world.toNMS()),
    Servantable {
    override fun initPathfinder() {
        goalSelector.a(0, PathfinderGoalFloat(this))
        goalSelector.a(0, PathfinderGoalMeleeAttack(this, 1.0, false))
        targetSelector.a(
            2, PathfinderGoalNearestAttackableTarget(
                this,
                EntityLiving::class.java, 20, true, true, null as Predicate<EntityLiving>?
            )
        )
    }

    override fun spawn(location: Location, plugin: JavaPlugin) {
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

    override fun dead(killer: Entity, plugin: JavaPlugin) {
        val entity = toBukkit()
        val playerMobs = owner.getMeta(MetadataKey.PlayerMobs, listOf()).toMutableList()
        playerMobs.remove(entity)
        owner.setMeta(plugin, MetadataKey.PlayerMobs, playerMobs)

        if (entity is Player) {
            onDead(entity, plugin)
        }
    }

    override fun onSpawn(owner: Player, plugin: JavaPlugin) {
        val entity = toBukkit()

        entity.equipment?.helmet = HeadItem(owner).toItemStack(plugin)
        val baseSpeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue
        baseSpeed?.let {
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = it * 2.0
        }
    }

    override fun onDead(killer: Player, plugin: JavaPlugin) {
        GolemMob(killer).spawn(killer.location, plugin)
    }

    override fun setOnFire(i: Int, callEvent: Boolean) {
    }

    override fun canPickup(itemstack: ItemStack?): Boolean {
        return false
    }

    override fun isCollidable(): Boolean {
        return false
    }
}