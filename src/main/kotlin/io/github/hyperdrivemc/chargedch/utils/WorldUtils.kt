package io.github.hyperdrivemc.chargedch.utils

import io.github.hyperdrivemc.chargedch.ChargedCH
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.persistence.PersistentDataType

lateinit var regenKey: NamespacedKey
lateinit var regenSpeedKey: NamespacedKey
lateinit var regenDelayKey: NamespacedKey
lateinit var replaceBlockKeysMapKey: NamespacedKey
lateinit var replaceBlockValuesMapKey: NamespacedKey

fun assignKeys(instance: ChargedCH) {
    regenKey = NamespacedKey(instance, "regen")
    regenSpeedKey = NamespacedKey(instance, "regenSpeed")
    regenDelayKey = NamespacedKey(instance, "regenDelay")
    replaceBlockKeysMapKey = NamespacedKey(instance, "replaceBlockKeysMap")
    replaceBlockValuesMapKey = NamespacedKey(instance, "replaceBlockValuesMap")
}

fun World.setRegen(regen: Boolean) {
    persistentDataContainer.set(regenKey, PersistentDataType.BYTE, if (regen) 1 else 0)
}

fun World.getRegen(): Boolean {
    persistentDataContainer.get(regenKey, PersistentDataType.BYTE)?.let {
        return it == 1.toByte()
    }
    return ChargedCH.INSTANCE.config.getBoolean("default-regen", true)
}

fun World.setRegenSpeed(ticks: Long) {
    persistentDataContainer.set(regenSpeedKey, PersistentDataType.LONG, ticks)
}

fun World.getRegenSpeed(): Long {
    return persistentDataContainer.get(regenSpeedKey, PersistentDataType.LONG) ?:
    ChargedCH.INSTANCE.config.getLong("default-regen-speed", 2)
}

fun World.setRegenDelay(ticks: Long) {
    persistentDataContainer.set(regenDelayKey, PersistentDataType.LONG, ticks)
}

fun World.getRegenDelay(): Long {
    return persistentDataContainer.get(regenDelayKey, PersistentDataType.LONG) ?:
    ChargedCH.INSTANCE.config.getLong("default-regen-delay", 10)
}

fun World.setReplaceBlockMap(blockMap: Map<Material, Material>) {
    persistentDataContainer.set(replaceBlockKeysMapKey,
        PersistentDataType.STRING, blockMap.keys.joinToString(",") { it.key.toString() })
    persistentDataContainer.set(replaceBlockValuesMapKey,
        PersistentDataType.STRING, blockMap.values.joinToString(",") { it.key.toString() })
}

fun World.getReplaceBlockMap(): Map<Material, Material> {
    val storedKeysList = persistentDataContainer.get(replaceBlockKeysMapKey, PersistentDataType.STRING)
    val storedValuesList = persistentDataContainer.get(replaceBlockValuesMapKey, PersistentDataType.STRING)
    if (storedKeysList != null && storedValuesList != null) {
        val keys = storedKeysList.split(",").map { Material.matchMaterial(it) ?: Material.AIR }
        val values = storedValuesList.split(",").map { Material.matchMaterial(it) ?: Material.AIR }
        return keys.zip(values).associate { (key, value) -> key to value }
    }
    ChargedCH.INSTANCE.config.getConfigurationSection("default-replace-blocks")?.let {
        val keys = it.getKeys(false)
        val values = keys.map { key -> it.getString(key) ?: "minecraft:air" }
        return keys.zip(values).associate { (key, value) ->
            (Material.matchMaterial(key) ?: Material.AIR) to (Material.matchMaterial(value) ?: Material.AIR)
        }.filter { (key) -> key != Material.AIR }
    }
    return emptyMap()
}