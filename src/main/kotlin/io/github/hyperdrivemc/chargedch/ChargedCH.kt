package io.github.hyperdrivemc.chargedch

import io.github.hyperdrivemc.chargedch.listeners.ExplosionListener
import org.bukkit.plugin.java.JavaPlugin

class ChargedCH : JavaPlugin() {
    override fun onEnable() {
        INSTANCE = this
        server.pluginManager.registerEvents(ExplosionListener(), this)
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var INSTANCE: ChargedCH
    }
}