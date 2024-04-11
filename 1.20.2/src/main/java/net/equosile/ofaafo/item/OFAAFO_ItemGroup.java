package net.equosile.ofaafo.item;

import net.equosile.ofaafo.OFAAFO;
import net.equosile.ofaafo.block.OFAAFO_Blocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OFAAFO_ItemGroup {
    public static final ItemGroup METEORITE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(OFAAFO.MODID, "ofaafo_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ofaafo_group"))
                    .icon(() -> new ItemStack(OFAAFO_Items.METEORITE)).entries((displayContext, entries) -> {

                        //OFAAFO ITEM
                        entries.add(OFAAFO_Items.METEORITE);
                        entries.add(OFAAFO_Items.CAELUSTRA);
                        entries.add(OFAAFO_Items.DIVINE_APPLE);

                        //BLOCK ITEM
                        entries.add(OFAAFO_Blocks.SUPERNOVA);

                        //TEST BLOCK
                        //entries.add(OFAAFO_Blocks.ENIGMA);
                        //entries.add(OFAAFO_Blocks.DICE_MONOPIP);
                        //entries.add(OFAAFO_Blocks.DICE_DIPIP);
                        //entries.add(OFAAFO_Blocks.DICE_TRIPIP);
                        //entries.add(OFAAFO_Blocks.DICE_TETRAPIP);
                        //entries.add(OFAAFO_Blocks.DICE_PENTAPIP);
                        //entries.add(OFAAFO_Blocks.DICE_HEXAPIP);
                    }).build());

    public static void registerItemGroup() {
        //AUTOMATIC CONSTRUCTOR

    }

}
