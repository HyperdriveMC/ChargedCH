package io.github.hyperdrivemc.chargedch.listeners

import io.github.hyperdrivemc.chargedch.fire.FireHealer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBurnEvent

class FireListener : Listener {
    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        FireHealer(event.block.state).heal()
    }
}