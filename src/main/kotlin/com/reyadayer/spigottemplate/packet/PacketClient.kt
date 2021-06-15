package com.reyadayer.spigottemplate.packet

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import org.bukkit.entity.Player
import java.lang.reflect.InvocationTargetException

abstract class PacketClient {
    protected val protocolManager: ProtocolManager = ProtocolLibrary.getProtocolManager()

    abstract fun send(player: Player)

    open fun send(players: List<Player>) {
        players.forEach {
            send(it)
        }
    }

    protected fun sendPacket(player: Player, packetContainer: PacketContainer) {
        try {
            protocolManager.sendServerPacket(player, packetContainer)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot send packet $packetContainer", e)
        }
    }
}