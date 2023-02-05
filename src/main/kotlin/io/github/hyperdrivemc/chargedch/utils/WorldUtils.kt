package io.github.hyperdrivemc.chargedch.utils

import io.github.hyperdrivemc.chargedch.ChargedCH
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.persistence.PersistentDataType

lateinit var regenKey: NamespacedKey
lateinit var regenSpeedKey: NamespacedKey
lateinit var regenDelayKey: NamespacedKey

fun assignKeys(instance: ChargedCH) {
    regenKey = NamespacedKey(instance, "regen")
    regenSpeedKey = NamespacedKey(instance, "regenSpeed")
    regenDelayKey = NamespacedKey(instance, "regenDelay")
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