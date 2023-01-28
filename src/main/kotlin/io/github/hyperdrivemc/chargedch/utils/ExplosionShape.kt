package io.github.hyperdrivemc.chargedch.utils

import org.bukkit.block.BlockFace
import org.bukkit.block.BlockState

object ExplosionShape {

    private val cartesianFaces = BlockFace.values().filter { it.isCartesian }

    fun getBorder(blockStateList: List<BlockState>): MutableSet<BlockState> {
        val borderStateList = mutableSetOf<BlockState>()
        blockStateList.forEach {
            cartesianFaces.forEach { face ->
                val relativeBlock = it.block.getRelative(face).state
                if (!it.type.isAir && !blockStateList.contains(relativeBlock)) { borderStateList.add(relativeBlock) }
            }
        }
        return borderStateList
    }

}
