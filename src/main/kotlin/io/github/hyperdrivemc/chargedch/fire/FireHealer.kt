package io.github.hyperdrivemc.chargedch.fire

import io.github.hyperdrivemc.chargedch.ChargedCH
import io.github.hyperdrivemc.chargedch.utils.getRegenDelay
import org.bukkit.block.BlockState
import org.bukkit.scheduler.BukkitRunnable

class FireHealer(private val blockState: BlockState) : BukkitRunnable() {
    override fun run() {
        blockState.update(true, false)
    }

    fun heal() {
        this.runTaskLater(ChargedCH.INSTANCE, blockState.world.getRegenDelay())
    }
}
