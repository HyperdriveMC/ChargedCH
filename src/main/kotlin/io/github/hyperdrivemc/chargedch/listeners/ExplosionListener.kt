package io.github.hyperdrivemc.chargedch.listeners

import io.github.hyperdrivemc.chargedch.tasks.BorderRestorer
import io.github.hyperdrivemc.chargedch.tasks.ExplosionHealer
import io.github.hyperdrivemc.chargedch.utils.ExplosionShape
import io.github.hyperdrivemc.chargedch.utils.getRegen
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

class ExplosionListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        if (event.location.world?.getRegen() == false) return
        val blockStateList = event.blockList().map { it.state }.toMutableList()
        val borderStateList = ExplosionShape.getBorder(blockStateList).toMutableList()
        val explosionCenter = event.location
        blockStateList.sortWith(compareByDescending { it.location.distanceSquared(explosionCenter) })
        ExplosionHealer(blockStateList).heal()
        BorderRestorer(borderStateList).restore()
        event.yield = 0F
    }
}