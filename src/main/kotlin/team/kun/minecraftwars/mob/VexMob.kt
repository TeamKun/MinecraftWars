package team.kun.minecraftwars.mob

import com.mineinabyss.idofront.nms.aliases.NMSEntityType
import com.mineinabyss.idofront.nms.aliases.toBukkit
import net.minecraft.server.v1_16_R3.*
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.item.HeadItem
import java.util.function.Predicate

class VexMob(owner: Player) : ServantMob(owner, NMSEntityType.VEX) {
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

    override fun onSpawn(owner: Player, plugin: JavaPlugin) {
        val entity = toBukkit()

        entity.equipment?.helmet = HeadItem(owner).toItemStack(plugin)
    }

    override fun onDead(killer: Player, plugin: JavaPlugin) {
        VexMob(killer).spawn(killer.location, plugin)
    }

    override fun setOnFire(i: Int, callEvent: Boolean) {
    }
}