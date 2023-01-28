package io.github.hyperdrivemc.chargedch.utils

import org.bukkit.block.BlockFace
import org.bukkit.block.BlockState

object ExplosionShape {
    fun getBorder(blockStateList: List<BlockState>): MutableSet<BlockState> {
        val borderStateList = mutableSetOf<BlockState>()
        blockStateList.forEach {
            BlockFace.values().forEach { face ->
                var relativeBlock = it.block.getRelative(face).state
                if (!blockStateList.contains(relativeBlock))  { borderStateList.add(relativeBlock) }
            }
        }
        borderStateList.removeIf { it.type.isAir }
        return borderStateList
    }
}
