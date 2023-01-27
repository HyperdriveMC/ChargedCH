package io.github.hyperdrivemc.chargedch.tasks

import org.bukkit.Sound
import org.bukkit.block.BlockState
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class ExplosionHealer(private val blockStateList: MutableList<BlockState>) : BukkitRunnable() {
    override fun run() {
        if (blockStateList.isEmpty()) {
            cancel()
            return
        }
        blockStateList[0].location.chunk.load()
        blockStateList[0].update(true, false)
        blockStateList[0].world.playSound(blockStateList[0].location, Sound.ENTITY_ITEM_PICKUP, 1F, Random.nextFloat()*2F)
        blockStateList.removeAt(0)
    }
}

