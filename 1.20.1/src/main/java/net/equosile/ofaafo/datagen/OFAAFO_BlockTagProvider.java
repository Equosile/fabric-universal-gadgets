package net.equosile.ofaafo.datagen;

import net.equosile.ofaafo.block.OFAAFO_Blocks;
import net.equosile.ofaafo.block.OFAAFO_Enigma;
import net.equosile.ofaafo.util.OFAAFO_Tags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class OFAAFO_BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public OFAAFO_BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.METEORITE_PARADIGM)
                .add(OFAAFO_Blocks.SUPERNOVA, OFAAFO_Blocks.ENIGMA);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(OFAAFO_Tags.Blocks.METEORITE_PARADIGM);

        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.CAELUSTRA_DETECTOR)
                .add(
                        OFAAFO_Blocks.SUPERNOVA, Blocks.ANCIENT_DEBRIS, Blocks.NETHER_QUARTZ_ORE,
                        Blocks.SUSPICIOUS_GRAVEL, Blocks.SUSPICIOUS_SAND, Blocks.CHEST,
                        Blocks.CRYING_OBSIDIAN, Blocks.OBSIDIAN,
                        Blocks.SCULK, Blocks.SCULK_CATALYST, Blocks.SCULK_SENSOR,
                        Blocks.SCULK_SHRIEKER, Blocks.SCULK_VEIN,
                        Blocks.AMETHYST_BLOCK, Blocks.AMETHYST_CLUSTER, Blocks.BUDDING_AMETHYST,
                        Blocks.LARGE_AMETHYST_BUD, Blocks.MEDIUM_AMETHYST_BUD, Blocks.SMALL_AMETHYST_BUD,
                        Blocks.DRIPSTONE_BLOCK, Blocks.POINTED_DRIPSTONE,
                        Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.PRISMARINE_BRICK_SLAB,
                        Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_BRICK_STAIRS,
                        Blocks.PRISMARINE_WALL, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.END_PORTAL
                )
                .forceAddTag(BlockTags.GOLD_ORES).forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES).forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES);

        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.PAXEL_MODE_LOG_AXE)
                .forceAddTag(BlockTags.LOGS);
        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.PAXEL_MODE_WOOD_AXE)
                .add(
                        Blocks.ACACIA_WOOD, Blocks.BIRCH_WOOD, Blocks.CHERRY_WOOD, Blocks.DARK_OAK_WOOD,
                        Blocks.JUNGLE_WOOD, Blocks.MANGROVE_WOOD, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD
                );
        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.PAXEL_MODE_DIRT_SHOVEL)
                .add(
                        Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT,
                        Blocks.MYCELIUM, Blocks.PODZOL, Blocks.ROOTED_DIRT
                );
        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.PAXEL_MODE_DIRT_HOE)
                .add(
                        Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.DIRT_PATH
                );


        //METEORITE PARADIGM
        getOrCreateTagBuilder(TagKey.of(
                RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_5")))
                .forceAddTag(OFAAFO_Tags.Blocks.METEORITE_PARADIGM);

        getOrCreateTagBuilder(OFAAFO_Tags.Blocks.CAELUSTRA_MINEABLE)
                .forceAddTag(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(BlockTags.AXE_MINEABLE)
                .forceAddTag(BlockTags.SHOVEL_MINEABLE)
                .forceAddTag(BlockTags.HOE_MINEABLE);

    }
}
