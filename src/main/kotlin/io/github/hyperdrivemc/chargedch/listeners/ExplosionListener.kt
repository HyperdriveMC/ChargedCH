package io.github.hyperdrivemc.chargedch.listeners

import io.github.hyperdrivemc.chargedch.ChargedCH
import io.github.hyperdrivemc.chargedch.explosion.ExplosionHealer
import io.github.hyperdrivemc.chargedch.explosion.Explosion
import io.github.hyperdrivemc.chargedch.explosion.ExplosionManager
import io.github.hyperdrivemc.chargedch.utils.getRegen
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.entity.EntityExplodeEvent

class ExplosionListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        handleExplosion(event.location, event.blockList())
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        handleExplosion(event.block.location, event.blockList())
    }

    @EventHandler(ignoreCancelled = true)
    fun onPhysicsUpdate(event: BlockPhysicsEvent) {
        if (ExplosionManager.containsBlock(event.sourceBlock)) {
            event.isCancelled = true
            event.block.state.update()
        }
    }

    private fun handleExplosion(center: Location, blockList: MutableList<Block>) {
        if (center.world?.getRegen() == false) return

        val explosion = Explosion(blockList)
        ExplosionManager.explosions.add(explosion)
        Bukkit.getScheduler().runTaskLater(ChargedCH.INSTANCE,
            Runnable { ExplosionManager.explosions.remove(explosion) }, 1)

        val blockStateList = blockList.map { it.state }.toMutableList()

        blockStateList.sortWith(compareByDescending { it.location.distanceSquared(center) })
        blockStateList.sortWith(compareByDescending { -it.location.y })

        ExplosionHealer(blockStateList).heal()

        blockList.forEach { it.type = Material.AIR }
    }

}