package team.kun.minecraftwars.mob

import com.mineinabyss.idofront.nms.aliases.NMSEntityType
import com.mineinabyss.idofront.nms.aliases.toBukkit
import net.minecraft.server.v1_16_R3.EntityLiving
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.item.HeadItem
import java.util.function.Predicate

class HuskMob(owner: Player) : ServantMob(owner, NMSEntityType.HUSK) {
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
        val baseSpeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue
        baseSpeed?.let {
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = it * 2.0
        }
    }

    override fun onDead(killer: Player, plugin: JavaPlugin) {
        HuskMob(killer).spawn(killer.location, plugin)
    }

    override fun setOnFire(i: Int, callEvent: Boolean) {
    }
}