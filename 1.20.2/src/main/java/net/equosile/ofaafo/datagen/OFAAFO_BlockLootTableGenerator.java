package net.equosile.ofaafo.datagen;

import net.equosile.ofaafo.block.OFAAFO_Blocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;

public class OFAAFO_BlockLootTableGenerator extends FabricBlockLootTableProvider {

    public OFAAFO_BlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        //This DataGen LootTable replaces the legacy JSON loot tables.

        //EVENT AND MIXIN
        //LootTableLoadingCallback Hooking that Moment of Loading LootTables





    }



    /*

    private static LootTable.Builder compose_Dice_LootTable() {

        LootPool.Builder poolBuilderForDICE = LootPool.builder();

        poolBuilderForDICE.with(ItemEntry.builder(OFAAFO_Blocks.DICE_MONOPIP).weight(16).build());
        poolBuilderForDICE.with(ItemEntry.builder(OFAAFO_Blocks.DICE_DIPIP).weight(16).build());
        poolBuilderForDICE.with(ItemEntry.builder(OFAAFO_Blocks.DICE_TRIPIP).weight(16).build());
        poolBuilderForDICE.with(ItemEntry.builder(OFAAFO_Blocks.DICE_TETRAPIP).weight(16).build());
        poolBuilderForDICE.with(ItemEntry.builder(OFAAFO_Blocks.DICE_PENTAPIP).weight(16).build());
        poolBuilderForDICE.with(ItemEntry.builder(OFAAFO_Blocks.DICE_HEXAPIP).weight(16).build());

        LootPool lootPoolForDice = poolBuilderForDICE.build();

        return LootTable.builder().pool(lootPoolForDice);
    }

     */

}
