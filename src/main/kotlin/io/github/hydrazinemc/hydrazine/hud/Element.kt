package io.github.hydrazinemc.hydrazine.hud

import org.bukkit.entity.Player

/**
 * Represents an element of the Scoreboard HUD
 */
abstract class Element {
	/**
	 * The ID of this element, must be unique, used to identify this element type.
	 */
	abstract val id: String

	/**
	 * @return the text that should be displayed on [player]'s scoreboard
	 */
	abstract fun display(player: Player): String
}
