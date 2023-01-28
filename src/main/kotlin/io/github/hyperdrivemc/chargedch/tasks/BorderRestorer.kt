package io.github.hyperdrivemc.chargedch.tasks

import io.github.hyperdrivemc.chargedch.ChargedCH
import org.bukkit.Sound
import org.bukkit.block.BlockState
import org.bukkit.scheduler.BukkitRunnable

class BorderRestorer(private val blockStateList: MutableList<BlockState>) : BukkitRunnable() {

    override fun run() {
        if (blockStateList.isEmpty()) {
            cancel()
            return
        }
        val currentState = blockStateList[0].block.state
        if (currentState.type != blockStateList[0].type || blockStateList[0] == currentState) {
            blockStateList.removeAt(0)
            run()
            return
        }
        blockStateList[0].location.chunk.load()
        blockStateList[0].update(true, false)
        blockStateList[0].world.playSound(blockStateList[0].location, Sound.ENTITY_ITEM_PICKUP, 0.2F, 2F)
        blockStateList.removeAt(0)
    }

    fun restore() {
        this.runTaskTimer(ChargedCH.INSTANCE, 10 + (2*blockStateList.size).toLong(), 2)
    }

}