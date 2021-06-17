package team.kun.minecraftwars.mob

import com.mineinabyss.idofront.nms.aliases.toBukkit
import com.mineinabyss.idofront.nms.aliases.toNMS
import com.mojang.authlib.GameProfile
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.CraftServer
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.lang.reflect.Field
import java.util.function.Predicate
import java.util.logging.Level

class PlayerMob(
    private val customName: String,
    private val gameProfile: GameProfile,
    private val location: Location
) : EntityZombie(EntityTypes.ZOMBIE, location.world.toNMS()) {
    override fun initPathfinder() {
        attributeMap.b().add(AttributeModifiable(GenericAttributes.ATTACK_DAMAGE) { a -> a.value = 10.0 })
        attributeMap.b().add(AttributeModifiable(GenericAttributes.FOLLOW_RANGE) { a -> a.value = 1.0 })
        goalSelector.a(0, PathfinderGoalFloat(this))
        goalSelector.a(0, PathfinderGoalMeleeAttack(this, 1.0, false))
        targetSelector.a(
            2, PathfinderGoalNearestAttackableTarget(
                this,
                EntityHuman::class.java, 20, true, true, null as Predicate<EntityLiving>?
            )
        )
        goalSelector.a(1, PathfinderGoalRandomLookaround(this))
        goalSelector.a(2, PathfinderGoalLookAtPlayer(this, EntityHuman::class.java, 8.0f))
    }

    fun spawn(plugin: JavaPlugin) {
        val destroy = PacketPlayOutEntityDestroy(id)

        val server: MinecraftServer = (Bukkit.getServer() as CraftServer).server
        val world = location.world.toNMS()
        val interactManager = PlayerInteractManager(world)

        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
        setLocation(location.x, location.y, location.z, location.yaw, location.pitch)

        val entityBukkit = toBukkit()
        entityBukkit.isCustomNameVisible = true
        entityBukkit.customName = customName

        val entityPlayer = EntityPlayer(server, world, gameProfile, interactManager)

        val entityPlayerBukkit = entityPlayer.toBukkit()
        entityPlayerBukkit.isCustomNameVisible = true
        entityPlayerBukkit.customName = customName

        entityPlayer.playerConnection =
            PlayerConnection(server, NetworkManager(EnumProtocolDirection.CLIENTBOUND), entityPlayer)

        val addInfo = PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer)
        val removeInfo =
            PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer)

        val spawn = PacketPlayOutNamedEntitySpawn(entityPlayer)

        try {
            val field: Field? = spawn.javaClass.getDeclaredField("a")
            field?.setAccessible(true)
            field?.set(spawn, id)
            plugin.logger.log(Level.INFO, "spawn id : ${field?.get(spawn)}")
        } catch (noSuchFieldException: NoSuchFieldException) {
            noSuchFieldException.printStackTrace()
        } catch (noSuchFieldException: IllegalAccessException) {
            noSuchFieldException.printStackTrace()
        }

        plugin.server.onlinePlayers.forEach {
            val connection = it.toNMS().playerConnection

            object : BukkitRunnable() {
                override fun run() {
                    connection.sendPacket(destroy)
                    connection.sendPacket(addInfo)
                    connection.sendPacket(spawn)
                    connection.sendPacket(removeInfo)
                }
            }.runTaskLater(plugin, 10)
        }
    }

    override fun setOnFire(i: Int, callEvent: Boolean) {
    }
}