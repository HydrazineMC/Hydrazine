package io.github.hydrazinemc.hydrazine.hud.elements

import io.github.hydrazinemc.hydrazine.hud.Element
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * Test element
 */
object TestElement: Element() {
	override val id: String = "test"
	override val entry = ChatColor.AQUA.toString()
	override fun display(player: Player): String = "Hello ${player.name}!"
}
