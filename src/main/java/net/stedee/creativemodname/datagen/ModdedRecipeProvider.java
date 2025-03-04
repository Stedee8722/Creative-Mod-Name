package net.stedee.creativemodname.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.item.ModdedItems;

import java.util.concurrent.CompletableFuture;

public class ModdedRecipeProvider extends FabricRecipeProvider {
    public ModdedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModdedPlushieBlocks.PLUSH_AYM, 1)
                .pattern(" # ")
                .pattern("SCS")
                .pattern("LWL")
                .input('#', Items.SOUL_LANTERN)
                .input('S', Items.SUGAR)
                .input('C', Items.COOKIE)
                .input('L', Items.STRING)
                .input('W', Items.WITHER_SKELETON_SKULL)
                .criterion(hasItem(Items.SOUL_LANTERN), conditionsFromItem(Items.SOUL_LANTERN))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .criterion(hasItem(Items.COOKIE), conditionsFromItem(Items.COOKIE))
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .criterion(hasItem(Items.WITHER_SKELETON_SKULL), conditionsFromItem(Items.WITHER_SKELETON_SKULL))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModdedPlushieBlocks.PLUSH_ACID, 1)
                .pattern("#P#")
                .pattern("KBM")
                .pattern("LGA")
                .input('#', Items.GOLD_NUGGET)
                .input('P', Items.PURPLE_DYE)
                .input('K', Items.PURPLE_WOOL)
                .input('B', Items.BLACK_DYE)
                .input('M', Items.MAGENTA_WOOL)
                .input('L', Items.LILY_OF_THE_VALLEY)
                .input('G', Items.GLASS_BOTTLE)
                .input('A', Items.ALLIUM)
                .criterion(hasItem(Items.GOLD_NUGGET), conditionsFromItem(Items.GOLD_NUGGET))
                .criterion(hasItem(Items.PURPLE_DYE), conditionsFromItem(Items.PURPLE_DYE))
                .criterion(hasItem(Items.PURPLE_WOOL), conditionsFromItem(Items.PURPLE_WOOL))
                .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                .criterion(hasItem(Items.MAGENTA_WOOL), conditionsFromItem(Items.MAGENTA_WOOL))
                .criterion(hasItem(Items.LILY_OF_THE_VALLEY), conditionsFromItem(Items.LILY_OF_THE_VALLEY))
                .criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE))
                .criterion(hasItem(Items.ALLIUM), conditionsFromItem(Items.ALLIUM))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModdedPlushieBlocks.PLUSH_RAT, 1)
                .pattern("  A")
                .pattern("BCD")
                .pattern("EFG")
                .input('A', Items.COOKED_BEEF)
                .input('B', Items.FEATHER)
                .input('C', Items.YELLOW_DYE)
                .input('D', Items.BLACK_DYE)
                .input('E', Items.PINK_DYE)
                .input('F', Items.WHITE_WOOL)
                .input('G', Items.PURPLE_DYE)
                .criterion(hasItem(Items.COOKED_BEEF), conditionsFromItem(Items.COOKED_BEEF))
                .criterion(hasItem(Items.FEATHER), conditionsFromItem(Items.FEATHER))
                .criterion(hasItem(Items.YELLOW_DYE), conditionsFromItem(Items.YELLOW_DYE))
                .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.PURPLE_DYE), conditionsFromItem(Items.PURPLE_DYE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModdedPlushieBlocks.PLUSH_HYPNO_RAT, 1)
                .input(Items.END_CRYSTAL)
                .input(ModdedPlushieBlocks.PLUSH_RAT)
                .criterion(hasItem(Items.END_CRYSTAL), conditionsFromItem(Items.END_CRYSTAL))
                .criterion(hasItem(ModdedPlushieBlocks.PLUSH_RAT), conditionsFromItem(ModdedPlushieBlocks.PLUSH_RAT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModdedItems.CLEAVER, 1)
                .pattern(" # ")
                .pattern("AIS")
                .pattern(" R ")
                .input('#', Items.SPIDER_EYE)
                .input('A', Items.AMETHYST_SHARD)
                .input('I', Items.IRON_AXE)
                .input('S', Items.SUGAR)
                .input('R', Items.ROTTEN_FLESH)
                .criterion(hasItem(Items.SPIDER_EYE), conditionsFromItem(Items.SPIDER_EYE))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .criterion(hasItem(Items.IRON_AXE), conditionsFromItem(Items.IRON_AXE))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
                .offerTo(recipeExporter);
    }
}
