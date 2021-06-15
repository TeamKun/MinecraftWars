package com.reyadayer.spigottemplate

import net.kunmc.lab.theworld.PluginCommands
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author

@Plugin(name = "SpigotTemplate", version = "1.0-SNAPSHOT")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.v1_15)
@Commands(
        Command(
                name = PluginCommands.SETTING,
                desc = "setting command",
                usage = "/<command>",
                permission = PluginPermissions.ADMIN,
                permissionMessage = "You don't have <permission>"
        )
)
class SpigotTemplate : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}