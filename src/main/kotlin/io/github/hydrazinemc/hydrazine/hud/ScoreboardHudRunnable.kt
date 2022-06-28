package io.github.hydrazinemc.hydrazine.hud

import io.github.hydrazinemc.hydrazine.hud.elements.CountingElement
import io.github.hydrazinemc.hydrazine.hud.elements.TestElement
import io.github.hydrazinemc.hydrazine.utils.extensions.asMiniMessage
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.DisplaySlot

/**
 * Updates the Scoreboard HUDs of players
 */
object ScoreboardHudRunnable: BukkitRunnable() {
	override fun run() {
		getServer().onlinePlayers.forEach {player ->
			// Ensure there is a scoreboard displayed
			val board = Bukkit.getScoreboardManager().newScoreboard
			val obj = board.getObjective("dummy") ?: board.registerNewObjective("dummy", "dummy", "dummy".asMiniMessage)
			obj.displaySlot = DisplaySlot.SIDEBAR

			var score = 1
			player.hudElements.reversed().forEach { element ->
				val team = board.getTeam("line-$score") ?: board.registerNewTeam("line-$score")
				team.entries.forEach{team.removeEntry(it)}
				team.prefix(element.display(player).asMiniMessage)
				team.addEntry(element.entry)
				obj.getScore(element.entry).score = score
				score++
			}
			player.scoreboard = board
		}
	}
}

/**
 * The player's HUD [Element]s
 */
var Player.hudElements: List<Element>
	get() = listOf(TestElement, CountingElement)
	set(value) {}
