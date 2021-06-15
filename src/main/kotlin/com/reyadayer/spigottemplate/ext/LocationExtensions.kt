package com.reyadayer.spigottemplate.ext

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.util.Vector
import java.util.*
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun Location.spawnEntity(entityType: EntityType): Entity? = world?.spawnEntity(this, entityType)

fun Location.spawnFallingBlock(material: Material): FallingBlock? {
    return world?.spawnFallingBlock(this, material.createBlockData())
}

inline fun <reified T : Entity> Location.spawn(noinline function: (entity: T) -> Unit): T? = world?.spawn<T>(this, T::class.java, function)

fun Location.spawnParticle(particle: Particle, count: Int) {
    this.world?.spawnParticle(particle, this, count, 0.0, 0.0, 0.0, 0.0)
}

fun Location.spawnParticle(particle: Particle, count: Int, dustOptions: Particle.DustOptions) {
    this.world?.spawnParticle(particle, this, count, dustOptions)
}

fun Location.playSound(sound: Sound, volume: Float, pitch: Float) {
    this.world?.playSound(this, sound, volume, pitch)
}

fun Location.random(x: Double, y: Double, z: Double): Location {
    return clone().add((Random().nextDouble() - 0.5) * x, (Random().nextDouble() - 0.5) * y, (Random().nextDouble() - 0.5) * z)
}

fun Location.convertToPolarCoordinates(radius: Double, angle: Double): Location {
    val currentLocation = this.clone()
    val x = cos(angle) * radius
    val y = 0.0
    val z = sin(angle) * radius
    return currentLocation.add(x, y, z)
}

/**
 * 目線のベクトルを標準とした座標系へ変換する
 * @param x        基底座標でのx
 * @param y        基底座標でのy
 * @param z        基底座標でのz
 * @return 座標変換されたLocation
 */
fun Location.transform(x: Double, y: Double, z: Double): Location {
    val location = this.clone()
    var rate = 1.0
    val vector: Vector = location.direction
    if (vector.x > 0) {
        rate = -1.0
    }
    val targetAngle = atan(vector.z / vector.x) + Math.PI / 2
    val targetAngle2 = rate * atan(vector.y / sqrt(vector.x.pow(2.0) + vector.z.pow(2.0)))

    val z1 = z * cos(targetAngle2) - y * sin(targetAngle2)
    val y1 = z * sin(targetAngle2) + y * cos(targetAngle2)

    val x1 = x * cos(targetAngle) - z1 * sin(targetAngle)
    val z2 = x * sin(targetAngle) + z1 * cos(targetAngle)

    // 位置の調整
    return location.add(x1, y1, z2)
}