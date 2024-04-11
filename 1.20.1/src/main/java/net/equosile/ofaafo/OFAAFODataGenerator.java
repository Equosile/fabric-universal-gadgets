package net.equosile.ofaafo;



import net.equosile.ofaafo.datagen.*;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class OFAAFODataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(OFAAFO_BlockTagProvider::new);
		//pack.addProvider(OFAAFO_ItemTagProvider::new);
		//pack.addProvider(OFAAFO_BlockLootTableGenerator::new);
		//pack.addProvider(OFAAFO_ModelProvider::new);
		pack.addProvider(OFAAFO_RecipeGenerator::new);

	}
}
