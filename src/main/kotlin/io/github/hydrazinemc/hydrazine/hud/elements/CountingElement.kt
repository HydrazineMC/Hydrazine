package io.github.hydrazinemc.hydrazine.hud.elements

import io.github.hydrazinemc.hydrazine.hud.Element
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * Test element
 */
object CountingElement: Element() {
	var num = 0
	override val id: String = "count"
	override val entry = ChatColor.BLACK.toString()
	override fun display(player: Player): String {
		num++
		return "Hi... $num!"
	}
}
