package io.github.hydrazinemc.hydrazine

import co.aikar.commands.PaperCommandManager
import io.github.hydrazinemc.hydrazine.commands.ConfigCommand
import io.github.hydrazinemc.hydrazine.crafts.CraftBlockSetter
import io.github.hydrazinemc.hydrazine.crafts.pilotable.ControlQueueRunnable
import io.github.hydrazinemc.hydrazine.crafts.pilotable.Pilotable
import io.github.hydrazinemc.hydrazine.crafts.pilotable.starships.StarshipMover
import io.github.hydrazinemc.hydrazine.crafts.pilotable.starships.commands.StarshipDebugCommands
import io.github.hydrazinemc.hydrazine.crafts.pilotable.starships.listeners.InterfaceListener
import io.github.hydrazinemc.hydrazine.customblocks.CustomBlockListener
import io.github.hydrazinemc.hydrazine.customblocks.CustomBlocks
import io.github.hydrazinemc.hydrazine.customblocks.MushroomEventListener
import io.github.hydrazinemc.hydrazine.customitems.CustomItems
import io.github.hydrazinemc.hydrazine.customitems.commands.CustomItemCommands
import io.github.hydrazinemc.hydrazine.customitems.listeners.ItemEnchantListener
import io.github.hydrazinemc.hydrazine.customitems.listeners.PowerItemBreakListener
import io.github.hydrazinemc.hydrazine.events.HydrazineConfigReloadEvent
import io.github.hydrazinemc.hydrazine.hud.ScoreboardHudRunnable
import io.github.hydrazinemc.hydrazine.multiblocks.MultiblockListener
import io.github.hydrazinemc.hydrazine.multiblocks.comands.MultiblockCommands
import io.github.hydrazinemc.hydrazine.utils.ConfigurableValues
import mu.KotlinLogging
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * Base plugin class for Hydrazine
 */
class Hydrazine : JavaPlugin() {
	companion object {
		/**
		 * The plugin instance. Not best practice to have it static,
		 * but the convenience is worth it.
		 *
		 * Sorry :)
		 */
		lateinit var plugin: Hydrazine
			private set

		/**
		 * The currently piloted [Pilotable]s
		 */
		var pilotedCrafts = mutableSetOf<Pilotable>()

		/**
		 * kotlin-logging logger for Hydrazine
		 * @see getLogger
		 */
		val klogger = KotlinLogging.logger("Hydrazine")
	}

	@Deprecated("Use kotlin-logging instead", ReplaceWith("klogger"))
	override fun getLogger(): Logger = super.getLogger()


	override fun onEnable() {
		// Plugin init

		plugin = this

		// Register listeners here
		setOf(
			MushroomEventListener(),
			CustomBlockListener(),
			InterfaceListener(),
			PowerItemBreakListener(),
			ItemEnchantListener(),
			MultiblockListener()
		).forEach { getPluginManager().registerEvents(it, this) }

		// Register commands here
		val commandManager = PaperCommandManager(this)
		setOf(
			ConfigCommand(),
			StarshipDebugCommands(),
			CustomItemCommands(),
			MultiblockCommands(),
		).forEach { commandManager.registerCommand(it) }
		commandManager.commandCompletions.registerCompletion(
			"customitems"
		) { CustomItems.all.keys }

		// Reload the config
		saveDefaultConfig()
		reloadConfig()

		// Start the bukkit tasks
		ControlQueueRunnable.runTaskTimer(plugin, 1, 1)
		CraftBlockSetter.runTaskTimer(plugin, 1, 1)
		StarshipMover.runTaskTimer(plugin, 1, 1)
		ScoreboardHudRunnable.runTaskTimer(plugin, 1, 1)
	}

	override fun reloadConfig() {
		super.reloadConfig()
		CustomItems.loadFromConfig() // needs to be called before custom blocks
		CustomBlocks.loadFromConfig()

		getPluginManager().callEvent(HydrazineConfigReloadEvent())
		ConfigurableValues.loadFromConfig()
	}
}
