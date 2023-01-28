package io.github.hyperdrivemc.chargedch.listeners

import io.github.hyperdrivemc.chargedch.ChargedCH
import io.github.hyperdrivemc.chargedch.tasks.BorderRestorer
import io.github.hyperdrivemc.chargedch.tasks.ExplosionHealer
import io.github.hyperdrivemc.chargedch.utils.ExplosionShape
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

class ExplosionListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        val blockStateList = event.blockList().map { it.state }.toMutableList()
        val borderStateList = ExplosionShape.getBorder(blockStateList).toMutableList()
        val explosionCenter = event.location
        blockStateList.sortWith(compareByDescending { it.location.distanceSquared(explosionCenter) })
        ExplosionHealer(blockStateList).runTaskTimer(ChargedCH.INSTANCE,10,2)
        BorderRestorer(borderStateList).runTaskTimer(ChargedCH.INSTANCE, 10 + (2*blockStateList.size).toLong(), 2)
        event.yield = 0F
    }
}