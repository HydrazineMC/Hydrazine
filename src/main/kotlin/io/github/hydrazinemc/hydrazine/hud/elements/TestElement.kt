package io.github.hydrazinemc.hydrazine.hud.elements

import io.github.hydrazinemc.hydrazine.hud.Element
import org.bukkit.entity.Player

/**
 * Test element
 */
class TestElement: Element() {
	override val id: String = "test"
	override fun display(player: Player): String = "Hello ${player.name}!"
}
