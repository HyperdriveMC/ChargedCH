package io.github.hyperdrivemc.chargedch.tasks

import org.bukkit.Sound
import org.bukkit.block.BlockState
import org.bukkit.block.data.MultipleFacing
import org.bukkit.block.data.type.Wall
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class BorderRestorer(private val blockStateList: MutableList<BlockState>) : BukkitRunnable() {
    override fun run() {
        if (blockStateList.isEmpty()) {
            cancel()
            return
        }
        if (blockStateList[0].blockData !is Wall &&
            blockStateList[0].blockData !is MultipleFacing) {
            blockStateList.removeAt(0)
            run()
            return
        }
        blockStateList[0].location.chunk.load()
        blockStateList[0].update(true, false)
        blockStateList[0].world.playSound(blockStateList[0].location, Sound.ENTITY_ITEM_PICKUP, 0.2F, 2F)
        blockStateList.removeAt(0)
    }
}