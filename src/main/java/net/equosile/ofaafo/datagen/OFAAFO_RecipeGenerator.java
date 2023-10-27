package net.equosile.ofaafo.datagen;



import net.equosile.ofaafo.block.OFAAFO_Blocks;
import net.equosile.ofaafo.item.OFAAFO_Items;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;

public class OFAAFO_RecipeGenerator extends FabricRecipeProvider {

    public OFAAFO_RecipeGenerator(FabricDataOutput output) {
        super(output);
    }



    @Override
    public void generate(RecipeExporter exporter) {



        Identifier id_recipe_METEORITE = new Identifier("recipe_custom_meteorite_main");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, OFAAFO_Items.METEORITE)
                .pattern("NN")
                .pattern("NN")
                .input('N', Items.NETHERITE_BLOCK)
                .criterion(hasItem(Items.NETHERITE_BLOCK), conditionsFromItem(Items.NETHERITE_BLOCK))
                .offerTo(exporter, id_recipe_METEORITE);

        Identifier id_recipe_SUPERNOVA = new Identifier("recipe_custom_supernova_main");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, OFAAFO_Blocks.SUPERNOVA)
                .pattern("MM")
                .pattern("MM")
                .input('M', OFAAFO_Items.METEORITE)
                .criterion(hasItem(OFAAFO_Items.METEORITE), conditionsFromItem(OFAAFO_Items.METEORITE))
                .offerTo(exporter, id_recipe_SUPERNOVA);

        Identifier id_recipe_SUPERNOVA_etc = new Identifier("recipe_custom_supernova_etc");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, OFAAFO_Blocks.SUPERNOVA)
                .pattern("ABC")
                .pattern("DEF")
                .pattern("GPG")
                .input('A', OFAAFO_Blocks.DICE_MONOPIP)
                .input('B', OFAAFO_Blocks.DICE_DIPIP)
                .input('C', OFAAFO_Blocks.DICE_TRIPIP)
                .input('D', OFAAFO_Blocks.DICE_TETRAPIP)
                .input('E', OFAAFO_Blocks.DICE_PENTAPIP)
                .input('F', OFAAFO_Blocks.DICE_HEXAPIP)
                .input('G', Items.GOLD_INGOT)
                .input('P', OFAAFO_Items.METEORITE)
                .criterion(hasItem(OFAAFO_Items.METEORITE), conditionsFromItem(OFAAFO_Items.METEORITE))
                .offerTo(exporter, id_recipe_SUPERNOVA_etc);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, OFAAFO_Items.METEORITE,
                RecipeCategory.MISC, OFAAFO_Blocks.SUPERNOVA);


        Identifier id_recipe_CAELUSTRA = new Identifier("recipe_custom_caelustra");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, OFAAFO_Items.CAELUSTRA)
                .pattern("PDA")
                .pattern("EML")
                .pattern("HGS")
                .input('M', OFAAFO_Items.METEORITE)
                .input('D', Items.DIAMOND)
                .input('G', Items.GOLD_INGOT)
                .input('E', Items.EMERALD)
                .input('L', Items.LAPIS_LAZULI)
                .input('P', Items.NETHERITE_PICKAXE)
                .input('A', Items.NETHERITE_AXE)
                .input('S', Items.NETHERITE_SHOVEL)
                .input('H', Items.NETHERITE_HOE)
                .criterion(hasItem(OFAAFO_Items.METEORITE), conditionsFromItem(OFAAFO_Items.METEORITE))
                .offerTo(exporter, id_recipe_CAELUSTRA);


        offerSmelting(exporter, List.of(OFAAFO_Blocks.DICE_MONOPIP, OFAAFO_Blocks.DICE_DIPIP,
                        OFAAFO_Blocks.DICE_TRIPIP, OFAAFO_Blocks.DICE_TETRAPIP,
                        OFAAFO_Blocks.DICE_PENTAPIP, OFAAFO_Blocks.DICE_HEXAPIP),
                RecipeCategory.MISC, Items.NETHERITE_BLOCK, 7.0F, 400, "meteorite_recipe_group");
        offerBlasting(exporter, List.of(OFAAFO_Blocks.DICE_MONOPIP, OFAAFO_Blocks.DICE_DIPIP,
                        OFAAFO_Blocks.DICE_TRIPIP, OFAAFO_Blocks.DICE_TETRAPIP,
                        OFAAFO_Blocks.DICE_PENTAPIP, OFAAFO_Blocks.DICE_HEXAPIP),
                RecipeCategory.MISC, Items.NETHERITE_BLOCK, 17.0F, 200, "meteorite_recipe_group");

        offerSmelting(exporter, List.of(OFAAFO_Blocks.ENIGMA),
                RecipeCategory.MISC, OFAAFO_Items.METEORITE, 7.0F, 400, "meteorite_recipe_group");
        offerBlasting(exporter, List.of(OFAAFO_Blocks.ENIGMA),
                RecipeCategory.MISC, OFAAFO_Items.METEORITE, 17.0F, 200, "meteorite_recipe_group");


        Identifier id_recipe_ENCHANTED_GOLDEN_APPLE = new Identifier("recipe_custom_enchanted_golden_apple");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ENCHANTED_GOLDEN_APPLE)
                .pattern("ABC")
                .pattern("DEF")
                .pattern("GPG")
                .input('A', OFAAFO_Blocks.DICE_MONOPIP)
                .input('B', OFAAFO_Blocks.DICE_DIPIP)
                .input('C', OFAAFO_Blocks.DICE_TRIPIP)
                .input('D', OFAAFO_Blocks.DICE_TETRAPIP)
                .input('E', OFAAFO_Blocks.DICE_PENTAPIP)
                .input('F', OFAAFO_Blocks.DICE_HEXAPIP)
                .input('G', Items.GOLD_INGOT)
                .input('P', Items.APPLE)
                .criterion(hasItem(Items.GOLDEN_APPLE), conditionsFromItem(Items.GOLDEN_APPLE))
                .offerTo(exporter, id_recipe_ENCHANTED_GOLDEN_APPLE);


        Identifier id_recipe_DIVINE_APPLE = new Identifier("recipe_custom_divine_apple_main");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, OFAAFO_Items.DIVINE_APPLE)
                .pattern("DGD")
                .pattern("GAG")
                .pattern("DGD")
                .input('A', Items.APPLE)
                .input('D', Items.DIAMOND)
                .input('G', Items.GOLDEN_APPLE)
                .criterion(hasItem(Items.GOLDEN_APPLE), conditionsFromItem(Items.GOLDEN_APPLE))
                .offerTo(exporter, id_recipe_DIVINE_APPLE);

        Identifier id_recipe_DIVINE_APPLE_shapeless = new Identifier("recipe_custom_divine_apple_shapeless");
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, OFAAFO_Items.DIVINE_APPLE, 1)
                .input(Items.ENCHANTED_GOLDEN_APPLE, 3)
                .criterion(hasItem(Items.ENCHANTED_GOLDEN_APPLE), conditionsFromItem(Items.ENCHANTED_GOLDEN_APPLE))
                .offerTo(exporter, id_recipe_DIVINE_APPLE_shapeless);



    }
}
