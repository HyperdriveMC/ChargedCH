package io.github.hyperdrivemc.chargedch.listeners

import io.github.hyperdrivemc.chargedch.ChargedCH
import io.github.hyperdrivemc.chargedch.explosion.ExplosionHealer
import io.github.hyperdrivemc.chargedch.explosion.Explosion
import io.github.hyperdrivemc.chargedch.explosion.ExplosionManager
import io.github.hyperdrivemc.chargedch.utils.getRegen
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.entity.EntityExplodeEvent

class ExplosionListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        if (event.location.world?.getRegen() == false) return

        val explosion = Explosion(event.blockList())
        ExplosionManager.explosions.add(explosion)
        Bukkit.getScheduler().runTaskLater(ChargedCH.INSTANCE,
            Runnable { ExplosionManager.explosions.remove(explosion) }, 1)

        val blockStateList = event.blockList().map { it.state }.toMutableList()
        val explosionCenter = event.location

        blockStateList.sortWith(compareByDescending { it.location.distanceSquared(explosionCenter) })
        blockStateList.sortWith(compareByDescending { -it.location.y })

        ExplosionHealer(blockStateList).heal()

        event.yield = 0F
    }

    @EventHandler(ignoreCancelled = true)
    fun onPhysicsUpdate(event: BlockPhysicsEvent) {
        if (ExplosionManager.containsBlock(event.sourceBlock)) {
            event.isCancelled = true
            event.block.state.update()
        }
    }

}