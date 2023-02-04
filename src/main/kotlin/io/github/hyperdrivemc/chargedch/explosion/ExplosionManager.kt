package io.github.hyperdrivemc.chargedch.explosion

import org.bukkit.block.Block

object ExplosionManager {

    val explosions = mutableListOf<Explosion>()

    fun containsBlock(block: Block): Boolean {
        return explosions.firstOrNull { it.explodedBlocks.contains(block) } != null
    }

}