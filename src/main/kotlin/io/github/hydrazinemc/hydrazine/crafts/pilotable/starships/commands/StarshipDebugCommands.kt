package io.github.hydrazinemc.hydrazine.crafts.pilotable.starships.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import io.github.hydrazinemc.hydrazine.crafts.pilotable.starships.Starship
import io.github.hydrazinemc.hydrazine.utils.Vector3
import io.github.hydrazinemc.hydrazine.utils.extensions.craft
import org.bukkit.entity.Player

/**
 * Command handling for the starship debug related commands.
 */
@CommandAlias("shipdebug")
class StarshipDebugCommands : BaseCommand() {

	/**
	 * Set starship velocity
	 */
	@Subcommand("setvelocity")
	@Description("Set the starship velocity")
	@CommandPermission("hydrazine.starship.debug.setvelocity")
	fun onSetVelocity(sender: Player, x: Double, y: Double, z: Double) {
		(getShip(sender) ?: return).velocity = Vector3(x, y, z)
		sender.sendRichMessage("<green>Set starship velocity to ($x, $y, $z)")
	}


	/**
	 * Set starship velocity
	 */
	@Subcommand("setacceleration")
	@Description("Set the starship acceleration")
	@CommandPermission("hydrazine.starship.debug.setacceleration")
	fun onSetAcceleration(sender: Player, x: Double, y: Double, z: Double) {
		(getShip(sender) ?: return).acceleration = Vector3(x, y, z)
		sender.sendRichMessage("<green>Set starship acceleration to ($x, $y, $z)")
	}

	/**
	 * Get the ship's accel and velocity
	 */
	@Subcommand("get")
	@Description("Get the starship's velocity and acceleration")
	@CommandPermission("hydrazine.starship.debug.get")
	fun onGetData(sender: Player) {
		val ship = getShip(sender) ?: return
		sender.sendRichMessage(
			"""
			<gray>------</gray>
			<b><white>Starship Movement Debug</b><gray>
			
			Velocity: ${ship.velocity.miniMessage}
			Acceleration: ${ship.acceleration.miniMessage}
			Moves Per Second: ${ship.movesPerSecond}
			Ticks Since Move: ${ship.ticksSinceMove}
			Time Spent Moving: ${ship.timeSpentMoving}
			------</gray>
		""".trimIndent()
		)
	}

	private fun getShip(sender: Player): Starship? {
		return ((sender.craft ?: run {
			sender.sendRichMessage("<red>You are not riding a craft!")
			return null
		}) as? Starship) ?: run {
			sender.sendRichMessage("<gold>This craft is not a Starship!")
			return null
		}
	}
}

