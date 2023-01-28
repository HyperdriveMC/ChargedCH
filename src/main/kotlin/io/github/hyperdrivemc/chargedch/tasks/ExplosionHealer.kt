package io.github.hyperdrivemc.chargedch.tasks

import io.github.hyperdrivemc.chargedch.ChargedCH
import io.github.hyperdrivemc.chargedch.utils.getRegenDelay
import io.github.hyperdrivemc.chargedch.utils.getRegenSpeed
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.block.BlockState
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class ExplosionHealer(private val blockStateList: MutableList<BlockState>) : BukkitRunnable() {
    private var physic = false
    private var explosionWorld: World? = null

    init {
        if (blockStateList.isNotEmpty()) {
            explosionWorld = blockStateList[0].world
        }
    }

    constructor(blockStateList: MutableList<BlockState>, physic: Boolean) : this(blockStateList) {
        this.physic = physic
    }

    override fun run() {
        if (blockStateList.isEmpty()) {
            cancel()
            return
        }
        blockStateList[0].location.chunk.load()
        blockStateList[0].update(true, physic)
        blockStateList[0].world.playSound(blockStateList[0].location, Sound.ENTITY_ITEM_PICKUP, 1F, Random.nextFloat()*2F)
        blockStateList.removeAt(0)

    }

    fun heal() {
        explosionWorld?.let {
            this.runTaskTimer(ChargedCH.INSTANCE, it.getRegenDelay(), it.getRegenSpeed())
        }
    }
}

