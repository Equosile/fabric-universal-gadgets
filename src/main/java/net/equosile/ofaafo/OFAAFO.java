package net.equosile.ofaafo;



import net.equosile.ofaafo.block.OFAAFO_Blocks;
import net.equosile.ofaafo.event.OFAAFO_DiceEvent;
import net.equosile.ofaafo.event.OFAAFO_TickEvent;
import net.equosile.ofaafo.item.OFAAFO_ItemGroup;
import net.equosile.ofaafo.item.OFAAFO_Items;
import net.equosile.ofaafo.item.OFAAFO_Meteorite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class OFAAFO implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "ofaafo";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		OFAAFO_ItemGroup.registerItemGroup();

		OFAAFO_Blocks.registerOFAAFOBlocks();
		OFAAFO_Items.registerOFAAFOItems();

		FuelRegistry.INSTANCE.add(OFAAFO_Items.METEORITE, OFAAFO_Meteorite.getBurnTime());

		//EVENT REGISTER
		OFAAFO_DiceEvent.registerDiceEvent();
		ServerTickEvents.START_SERVER_TICK.register( server -> {
				OFAAFO_TickEvent.onServerTickEvent(server);
		} );

		LOGGER.info("Hello Fabric world!");
	}
}