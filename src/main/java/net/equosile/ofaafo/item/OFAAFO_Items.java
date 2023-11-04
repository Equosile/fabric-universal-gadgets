package net.equosile.ofaafo.item;

import net.equosile.ofaafo.OFAAFO;
import net.equosile.ofaafo.block.OFAAFO_Blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.item.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class OFAAFO_Items {

    //Items in OFAAFO
    public static final Item METEORITE = registerItem("meteorite",
            new OFAAFO_Meteorite(new FabricItemSettings()));

    //Tools in Items
    public static final Item CAELUSTRA = registerItem("caelustra",
            new OFAAFO_Caelustra(OFAAFO_ToolMaterials.METEORITE, 3, 2, new FabricItemSettings().fireproof()));

    //Foods in Items
    public static final Item DIVINE_APPLE = registerItem("divine_apple",
            new OFAAFO_DivineApple(new FabricItemSettings().food(OFAAFO_LavhanitComponents.template_DIVINE_APPLE)));



    //Item Group
    //Vanilla Ingredient for Test
    private static void itemGroupIngredients(FabricItemGroupEntries entries) {
        //entries.add(METEORITE);

        //BLOCK ITEM
        //entries.add(OFAAFO_Blocks.SUPERNOVA);
    }


    //Helper Method for Register
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(OFAAFO.MODID, name), item);
    }
    public static void registerOFAAFOItems() {
        //String MODID = OFAAFO.MODID;
        OFAAFO.LOGGER.info("Registering OFAAFO.Items... ");

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(OFAAFO_Items::itemGroupIngredients);
    }
}
