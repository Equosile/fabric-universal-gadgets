package net.equosile.ofaafo.util;



import net.equosile.ofaafo.OFAAFO;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class OFAAFO_Tags {

    public static class Blocks {

        public static final TagKey<Block> CAELUSTRA_DETECTOR =
                createCustomBlockTag("caelustra_detector");
        public static final TagKey<Block> CAELUSTRA_MINEABLE =
                createCustomBlockTag("mineable/caelustra");
        public static final TagKey<Block> METEORITE_PARADIGM =
                createCustomBlockTag("meteorite_paradigm");



        public static final TagKey<Block> PAXEL_MODE_LOG_AXE =
                createCustomBlockTag("paxel_mode_log_axe");
        public static final TagKey<Block> PAXEL_MODE_WOOD_AXE =
                createCustomBlockTag("paxel_mode_wood_axe");
        public static final TagKey<Block> PAXEL_MODE_DIRT_SHOVEL =
                createCustomBlockTag("paxel_mode_dirt_shovel");
        public static final TagKey<Block> PAXEL_MODE_DIRT_HOE =
                createCustomBlockTag("paxel_mode_dirt_hoe");



        private static TagKey<Block> createCustomBlockTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(OFAAFO.MODID, name));
        }
        private static TagKey<Block> createCommonBlockTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", name));
        }

    }

    public static class Items {

        private static TagKey<Item> createCustomBlockTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(OFAAFO.MODID, name));
        }
        private static TagKey<Item> createCommonBlockTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
        }

    }

}
