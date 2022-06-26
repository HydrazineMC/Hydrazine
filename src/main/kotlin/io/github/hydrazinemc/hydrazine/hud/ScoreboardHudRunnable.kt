package io.github.hydrazinemc.hydrazine.hud

import io.github.hydrazinemc.hydrazine.hud.elements.TestElement
import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * Updates the Scoreboard HUDs of players
 */
object ScoreboardHudRunnable: BukkitRunnable() {
	override fun run() {
		getServer().onlinePlayers.forEach {player ->
			var text = mutableListOf<String>()
			player.hudElements.forEach { element ->
				text.add(element.display(player))
			}
			player.displayScoreboard(text.joinToString("\n"))
		}
	}
}

/**
 * Display [text] on the player's scoreboard
 */
fun Player.displayScoreboard(text: String) {}

/**
 * The player's HUD [Element]s
 */
var Player.hudElements: List<Element>
	get() = listOf(TestElement())
	set(value) {}
