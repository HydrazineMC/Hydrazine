package io.github.petercrawley.minecraftstarshipplugin.interfaces

import io.github.petercrawley.minecraftstarshipplugin.customblocks.MSPMaterial
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class InterfaceListener: Listener {
	@EventHandler
	fun interfaceUse(event: PlayerInteractEvent) {
		if (event.hand == EquipmentSlot.HAND && event.action == Action.RIGHT_CLICK_BLOCK && !event.player.isSneaking) {
			val clickedBlock: Block = event.clickedBlock!!

			if (MSPMaterial(clickedBlock) == MSPMaterial("INTERFACE")) {
				Interface(clickedBlock, event.player)

				event.isCancelled = true
			}
		}
	}
}