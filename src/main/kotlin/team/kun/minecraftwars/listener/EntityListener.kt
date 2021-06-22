package team.kun.minecraftwars.listener

import com.mineinabyss.idofront.nms.aliases.toNMS
import org.bukkit.entity.Creature
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.metadata.MetadataKey
import team.kun.minecraftwars.mob.ServantMob

class EntityListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onTarget(event: EntityTargetEvent) {
        val entity = event.entity
        val target = event.target
        val owner = entity.getMeta(MetadataKey.Owner)
        if (owner != null) {
            if (owner.getMeta(MetadataKey.IsAttacking, false)) {
                if (owner == target?.getMeta(MetadataKey.Owner)
                    || owner == target
                ) {
                    event.isCancelled = true
                    if (entity is Creature) {
                        entity.target = entity.location.getNearbyEntities(10.0, 10.0, 10.0)
                            .filterIsInstance<LivingEntity>()
                            .filterNot { it.isDead }
                            .filterNot { owner == it.getMeta(MetadataKey.Owner) }
                            .filterNot { owner == it }
                            .randomOrNull()
                    }
                }
            } else {
                if (owner != target) {
                    event.isCancelled = true
                    if (entity is Creature) {
                        entity.target = owner
                    }
                }
            }
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val damager = event.damager
        val owner = entity.getMeta(MetadataKey.Owner)
        val damagerOwner = damager.getMeta(MetadataKey.Owner)
        if (entity == damagerOwner
            || entity == owner
            || (owner != null && damagerOwner != null && owner == damagerOwner)
        ) {
            event.isCancelled = true
        }
        if (entity.isDead) {
            val nmsEntity = event.entity.toNMS()
            if (nmsEntity is ServantMob) {
                nmsEntity.dead(damager, plugin)
            }
        }
    }

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        event.droppedExp = 0
        event.drops.clear()
    }
}