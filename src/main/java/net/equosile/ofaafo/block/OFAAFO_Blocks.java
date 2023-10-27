package net.equosile.ofaafo.block;

import net.equosile.ofaafo.OFAAFO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class OFAAFO_Blocks {

    public static final Block SUPERNOVA = registerBlock("supernova",
            new OFAAFO_Supernova(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .requiresTool().strength(50.0F, 1200.0F)
                    .luminance(state -> OFAAFO_Supernova.supernovaCheckState(state) ? 15:0),
                    UniformIntProvider.create(1237, 2474))
    );
    public static final Block DICE_MONOPIP = registerBlock("dice_monopip",
            new OFAAFO_Dice_Monopip(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .strength(1.0F, 1.0F)
                    .luminance((state) -> {return 15;}), UniformIntProvider.create(7, 17)));
    public static final Block DICE_DIPIP = registerBlock("dice_dipip",
            new OFAAFO_Dice_Dipip(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .strength(1.0F, 1.0F)
                    .luminance((state) -> {return 15;}), UniformIntProvider.create(7, 17)));
    public static final Block DICE_TRIPIP = registerBlock("dice_tripip",
            new OFAAFO_Dice_Tripip(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .strength(1.0F, 1.0F)
                    .luminance((state) -> {return 15;}), UniformIntProvider.create(7, 17)));
    public static final Block DICE_TETRAPIP = registerBlock("dice_tetrapip",
            new OFAAFO_Dice_Tetrapip(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .strength(1.0F, 1.0F)
                    .luminance((state) -> {return 15;}), UniformIntProvider.create(7, 17)));
    public static final Block DICE_PENTAPIP = registerBlock("dice_pentapip",
            new OFAAFO_Dice_Pentapip(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .strength(1.0F, 1.0F)
                    .luminance((state) -> {return 15;}), UniformIntProvider.create(7, 17)));
    public static final Block DICE_HEXAPIP = registerBlock("dice_hexapip",
            new OFAAFO_Dice_Hexapip(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .strength(1.0F, 1.0F)
                    .luminance((state) -> {return 15;}), UniformIntProvider.create(7, 17)));

    public static final Block ENIGMA = registerBlock("enigma",
            new OFAAFO_Enigma(FabricBlockSettings.create().mapColor(MapColor.BLACK).instrument(Instrument.HARP)
                    .requiresTool().strength(50.0F, 1200.0F)
                    .luminance(state -> OFAAFO_Enigma.enigmaCheckState(state) ? 15:0),
                    UniformIntProvider.create(1237, 2474))
    );



    //Helper Method for Registry
    private static Block registerBlock(String name, Block block) {
        registerBlockItem_FabSetFree(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(OFAAFO.MODID, name), block);
    }
    private static Item registerBlockItem_FabSetFree(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(OFAAFO.MODID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerOFAAFOBlocks() {
        OFAAFO.LOGGER.info("Registering OFAAFO Blocks...");


    }
}
