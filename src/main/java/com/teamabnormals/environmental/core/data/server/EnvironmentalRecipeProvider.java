package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.boatload.core.data.server.BoatloadRecipeProvider;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalBlockFamilies;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.integration.boatload.EnvironmentalBoatTypes;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalRecipeProvider extends RecipeProvider {
	public static final ModLoadedCondition INCUBATION_LOADED = new ModLoadedCondition("incubation");
	public static final ModLoadedCondition BERRY_GOOD_LOADED = new ModLoadedCondition("berry_good");

	public EnvironmentalRecipeProvider(PackOutput output) {
		super(output);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		oneToOneConversionRecipe(consumer, Items.LIGHT_BLUE_DYE, BLUE_DELPHINIUM.get(), "light_blue_dye", 2);
		oneToOneConversionRecipe(consumer, Items.PINK_DYE, PINK_DELPHINIUM.get(), "pink_dye", 2);
		oneToOneConversionRecipe(consumer, Items.WHITE_DYE, WHITE_DELPHINIUM.get(), "white_dye", 2);
		oneToOneConversionRecipe(consumer, Items.PURPLE_DYE, PURPLE_DELPHINIUM.get(), "purple_dye", 2);
		oneToOneConversionRecipe(consumer, Items.BLUE_DYE, BLUEBELL.get(), "blue_dye");
		oneToOneConversionRecipe(consumer, Items.LIME_DYE, DIANTHUS.get(), "lime_dye");
		oneToOneConversionRecipe(consumer, Items.ORANGE_DYE, BIRD_OF_PARADISE.get(), "orange_dye", 2);
		oneToOneConversionRecipe(consumer, Items.PINK_DYE, CARTWHEEL.get(), "pink_dye");
		oneToOneConversionRecipe(consumer, Items.PURPLE_DYE, VIOLET.get(), "purple_dye");
		oneToOneConversionRecipe(consumer, Items.RED_DYE, RED_LOTUS_FLOWER.get(), "red_dye");
		oneToOneConversionRecipe(consumer, Items.WHITE_DYE, WHITE_LOTUS_FLOWER.get(), "white_dye");
		oneToOneConversionRecipe(consumer, Items.ORANGE_DYE, TASSELFLOWER.get(), "purple_dye");
		oneToOneConversionRecipe(consumer, Items.MAGENTA_DYE, MAGENTA_HIBISCUS.get(), "magenta_dye");
		oneToOneConversionRecipe(consumer, Items.ORANGE_DYE, ORANGE_HIBISCUS.get(), "orange_dye");
		oneToOneConversionRecipe(consumer, Items.PINK_DYE, PINK_HIBISCUS.get(), "pink_dye");
		oneToOneConversionRecipe(consumer, Items.YELLOW_DYE, YELLOW_HIBISCUS.get(), "yellow_dye");
		oneToOneConversionRecipe(consumer, Items.RED_DYE, RED_HIBISCUS.get(), "red_dye");
		oneToOneConversionRecipe(consumer, Items.PURPLE_DYE, PURPLE_HIBISCUS.get(), "purple_dye");

		WoodworksRecipeProvider.leafPileRecipes(consumer, HIBISCUS_LEAVES.get(), HIBISCUS_LEAF_PILE.get(), Environmental.MOD_ID);

		foodCookingRecipes(consumer, EnvironmentalItems.DUCK.get(), EnvironmentalItems.COOKED_DUCK.get());
		foodCookingRecipes(consumer, EnvironmentalItems.VENISON.get(), EnvironmentalItems.COOKED_VENISON.get());
		conditionalNineBlockStorageRecipes(consumer, INCUBATION_LOADED, RecipeCategory.MISC, EnvironmentalItems.DUCK_EGG.get(), RecipeCategory.DECORATIONS, DUCK_EGG_CRATE.get());
		conditionalNineBlockStorageRecipes(consumer, BERRY_GOOD_LOADED, RecipeCategory.FOOD, EnvironmentalItems.CHERRIES.get(), RecipeCategory.DECORATIONS, CHERRY_CRATE.get());
		nineBlockStorageRecipes(consumer, RecipeCategory.MISC, EnvironmentalItems.CATTAIL_FLUFF.get(), RecipeCategory.BUILDING_BLOCKS, CATTAIL_FLUFF_BLOCK.get());

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING).requires(EnvironmentalItems.CATTAIL_FLUFF.get(), 7).unlockedBy("has_cattail_seeds", has(EnvironmentalItems.CATTAIL_FLUFF.get())).save(consumer, getModConversionRecipeName(Items.STRING, EnvironmentalItems.CATTAIL_FLUFF.get()));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EnvironmentalItems.CHERRY_PIE.get()).requires(Ingredient.of(EnvironmentalItemTags.FRUITS_CHERRY), 3).requires(Items.SUGAR).requires(Tags.Items.EGGS).unlockedBy("has_plum", has(EnvironmentalItemTags.FRUITS_CHERRY)).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH.get(), 4).define('W', Items.WHEAT).define('G', Blocks.GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_grass", has(Blocks.GRASS)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH.get(), 6).define('W', Items.WHEAT).define('G', Blocks.TALL_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_tall_grass", has(Blocks.TALL_GRASS)).save(consumer, getModConversionRecipeName(GRASS_THATCH.get(), Blocks.GRASS));
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH.get(), 8).define('W', Items.WHEAT).define('G', GIANT_TALL_GRASS.get()).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_giant_tall_grass", has(GIANT_TALL_GRASS.get())).save(consumer, getModConversionRecipeName(GRASS_THATCH.get(), GIANT_TALL_GRASS.get()));
		generateRecipes(consumer, EnvironmentalBlockFamilies.GRASS_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CATTAIL_THATCH.get(), 4).define('#', CATTAIL.get()).pattern("##").pattern("##").unlockedBy("has_cattail", has(CATTAIL.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.CATTAIL_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DUCKWEED_THATCH.get(), 4).define('#', DUCKWEED.get()).pattern("##").pattern("##").unlockedBy("has_duckweed", has(DUCKWEED.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DUCKWEED_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DIRT_BRICKS.get(), 4).define('#', Blocks.DIRT).pattern("##").pattern("##").unlockedBy(getHasName(Blocks.DIRT), has(Blocks.DIRT)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_SLAB.get(), DIRT_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_STAIRS.get(), DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, DIRT_BRICK_WALL.get(), DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICKS.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_SLAB.get(), Blocks.DIRT, 2);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_STAIRS.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, DIRT_BRICK_WALL.get(), Blocks.DIRT);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DIRT_TILES.get(), 4).define('#', DIRT_BRICKS.get()).pattern("##").pattern("##").unlockedBy(getHasName(DIRT_BRICKS.get()), has(DIRT_BRICKS.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_TILE_FAMILY);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB.get(), DIRT_TILES.get(), 2);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS.get(), DIRT_TILES.get());
		stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL.get(), DIRT_TILES.get());
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILES.get(), DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB.get(), DIRT_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS.get(), DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL.get(), DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILES.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB.get(), Blocks.DIRT, 2);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL.get(), Blocks.DIRT);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.MUD).define('#', EnvironmentalItems.MUD_BALL.get()).pattern("##").pattern("##").unlockedBy("has_mud_ball", has(EnvironmentalItems.MUD_BALL.get())).save(consumer, new ResourceLocation(Environmental.MOD_ID, RecipeBuilder.getDefaultRecipeId(Blocks.MUD).getPath()));
		oneToOneConversionRecipeBuilder(EnvironmentalItems.MUD_BALL.get(), Blocks.MUD, 4).group("mud_ball").save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EnvironmentalItems.MUD_BALL.get(), 16).requires(BlueprintItemTags.BUCKETS_WATER).requires(Blocks.DIRT, 4).group("mud_ball").unlockedBy("has_dirt", has(Blocks.DIRT)).save(consumer, getModConversionRecipeName(EnvironmentalItems.MUD_BALL.get(), Blocks.DIRT));
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SLABFISH_EFFIGY.get()).define('#', Blocks.MUD_BRICKS).define('S', Blocks.MUD_BRICK_SLAB).pattern(" S ").pattern("S#S").unlockedBy("has_mud_bricks", has(Blocks.MUD_BRICKS)).save(consumer);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.PACKED_MUD), RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUD.get().asItem(), 0.1F, 200).unlockedBy("has_packed_mud", has(Blocks.PACKED_MUD)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.SMOOTH_MUD_FAMILY);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUD_SLAB.get(), SMOOTH_MUD.get(), 2);
		chiseled(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUD_BRICKS.get(), Blocks.MUD_BRICK_SLAB);
		stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUD_BRICKS.get(), Blocks.MUD_BRICKS);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.BROWN_WOOL).define('#', EnvironmentalItems.YAK_HAIR.get()).pattern("##").pattern("##").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer, new ResourceLocation(Environmental.MOD_ID, getItemName(Blocks.BROWN_WOOL)));
		nineBlockStorageRecipes(consumer, RecipeCategory.MISC, EnvironmentalItems.YAK_HAIR.get(), RecipeCategory.BUILDING_BLOCKS, YAK_HAIR_BLOCK.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, YAK_HAIR_RUG.get()).define('#', EnvironmentalItems.YAK_HAIR.get()).pattern("###").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, EnvironmentalItems.YAK_PANTS.get()).define('#', Items.LEATHER).define('B', YAK_HAIR_BLOCK.get()).pattern("BBB").pattern("# #").pattern("# #").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer);

		generateRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY);
		planksFromLogs(consumer, WILLOW_PLANKS.get(), EnvironmentalItemTags.WILLOW_LOGS, 4);
		woodFromLogs(consumer, WILLOW_WOOD.get(), WILLOW_LOG.get());
		woodFromLogs(consumer, STRIPPED_WILLOW_WOOD.get(), STRIPPED_WILLOW_LOG.get());
		hangingSign(consumer, STRIPPED_WILLOW_LOG.get(), WILLOW_HANGING_SIGNS.getFirst().get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HANGING_WILLOW_LEAVES.get(), 3).define('#', WILLOW_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(WILLOW_LEAVES.get()), has(WILLOW_LEAVES.get())).save(consumer);
		WoodworksRecipeProvider.leafPileRecipes(consumer, WILLOW_LEAVES.get(), WILLOW_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.WILLOW);
		WoodworksRecipeProvider.baseRecipes(consumer, WILLOW_PLANKS.get(), WILLOW_SLAB.get(), WILLOW_BOARDS.get(), WILLOW_BOOKSHELF.get(), Blocks.ACACIA_STAIRS, WILLOW_LADDER.get(), WILLOW_BEEHIVE.get(), WILLOW_CHEST.get(), TRAPPED_WILLOW_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY, EnvironmentalItemTags.WILLOW_LOGS, WILLOW_BOARDS.get(), WILLOW_LADDER.get(), Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY);
		planksFromLogs(consumer, PINE_PLANKS.get(), EnvironmentalItemTags.PINE_LOGS, 4);
		woodFromLogs(consumer, PINE_WOOD.get(), PINE_LOG.get());
		woodFromLogs(consumer, STRIPPED_PINE_WOOD.get(), STRIPPED_PINE_LOG.get());
		WoodworksRecipeProvider.leafPileRecipes(consumer, PINE_LEAVES.get(), PINE_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.PINE);
		WoodworksRecipeProvider.baseRecipes(consumer, PINE_PLANKS.get(), PINE_SLAB.get(), PINE_BOARDS.get(), PINE_BOOKSHELF.get(), Blocks.DARK_OAK_STAIRS, PINE_LADDER.get(), PINE_BEEHIVE.get(), PINE_CHEST.get(), TRAPPED_PINE_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY, EnvironmentalItemTags.PINE_LOGS, PINE_BOARDS.get(), PINE_LADDER.get(), Environmental.MOD_ID);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, WAXED_PINECONE.get()).requires(PINECONE.get()).requires(Items.HONEYCOMB).unlockedBy(getHasName(PINECONE.get()), has(PINECONE.get())).save(consumer, getConversionRecipeName(WAXED_PINECONE.get(), Items.HONEYCOMB));

		generateRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY);
		planksFromLogs(consumer, WISTERIA_PLANKS.get(), EnvironmentalItemTags.WISTERIA_LOGS, 4);
		woodFromLogs(consumer, WISTERIA_WOOD.get(), WISTERIA_LOG.get());
		woodFromLogs(consumer, STRIPPED_WISTERIA_WOOD.get(), STRIPPED_WISTERIA_LOG.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PINK_HANGING_WISTERIA_LEAVES.get(), 3).define('#', PINK_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(PINK_WISTERIA_LEAVES.get()), has(PINK_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BLUE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', BLUE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(BLUE_WISTERIA_LEAVES.get()), has(BLUE_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PURPLE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', PURPLE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(PURPLE_WISTERIA_LEAVES.get()), has(PURPLE_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, WHITE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', WHITE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(WHITE_WISTERIA_LEAVES.get()), has(WHITE_WISTERIA_LEAVES.get())).save(consumer);
		WoodworksRecipeProvider.leafPileRecipes(consumer, PINK_WISTERIA_LEAVES.get(), PINK_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.leafPileRecipes(consumer, BLUE_WISTERIA_LEAVES.get(), BLUE_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.leafPileRecipes(consumer, PURPLE_WISTERIA_LEAVES.get(), PURPLE_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.leafPileRecipes(consumer, WHITE_WISTERIA_LEAVES.get(), WHITE_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.leafPileRecipes(consumer, WISTERIA_LEAVES.get(), WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.WISTERIA);
		WoodworksRecipeProvider.baseRecipes(consumer, WISTERIA_PLANKS.get(), WISTERIA_SLAB.get(), WISTERIA_BOARDS.get(), WISTERIA_BOOKSHELF.get(), Blocks.BAMBOO_STAIRS, WISTERIA_LADDER.get(), WISTERIA_BEEHIVE.get(), WISTERIA_CHEST.get(), TRAPPED_WISTERIA_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY, EnvironmentalItemTags.WISTERIA_LOGS, WISTERIA_BOARDS.get(), WISTERIA_LADDER.get(), Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY);
		planksFromLogs(consumer, PLUM_PLANKS.get(), EnvironmentalItemTags.PLUM_LOGS, 4);
		woodFromLogs(consumer, PLUM_WOOD.get(), PLUM_LOG.get());
		woodFromLogs(consumer, STRIPPED_PLUM_WOOD.get(), STRIPPED_PLUM_LOG.get());
		leafPileRecipes(consumer, PLUM_LEAVES.get(), PLUM_LEAF_PILE.get());
		leafPileRecipes(consumer, CHEERFUL_PLUM_LEAVES.get(), CHEERFUL_PLUM_LEAF_PILE.get());
		leafPileRecipes(consumer, MOODY_PLUM_LEAVES.get(), MOODY_PLUM_LEAF_PILE.get());
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.PLUM);
		WoodworksRecipeProvider.baseRecipes(consumer, PLUM_PLANKS.get(), PLUM_SLAB.get(), PLUM_BOARDS.get(), PLUM_BOOKSHELF.get(), Blocks.BIRCH_STAIRS, PLUM_LADDER.get(), PLUM_BEEHIVE.get(), PLUM_CHEST.get(), TRAPPED_PLUM_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY, EnvironmentalItemTags.PLUM_LOGS, PLUM_BOARDS.get(), PLUM_LADDER.get(), Environmental.MOD_ID);

		ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Blocks.CAKE).define('A', BlueprintItemTags.BUCKETS_MILK).define('B', Items.SUGAR).define('C', Items.WHEAT).define('E', Tags.Items.EGGS).pattern("AAA").pattern("BEB").pattern("CCC").unlockedBy("has_egg", has(Items.EGG)).save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.PUMPKIN_PIE).requires(BlueprintItemTags.PUMPKINS).requires(Items.SUGAR).requires(Tags.Items.EGGS).unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).unlockedBy("has_pumpkin", has(BlueprintItemTags.PUMPKINS)).save(consumer);
	}

	public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory itemCategory, ItemLike item, RecipeCategory storageCategory, ItemLike storage) {
		nineBlockStorageRecipes(consumer, itemCategory, item, storageCategory, storage, Environmental.MOD_ID + ":" + getSimpleRecipeName(storage), null, Environmental.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	public static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group) {
		oneToOneConversionRecipe(consumer, output, input, group, 1);
	}

	public static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group, int count) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count).requires(input).group(group).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input));
	}

	public static ShapelessRecipeBuilder oneToOneConversionRecipeBuilder(RecipeCategory category, ItemLike output, ItemLike input, int count) {
		return ShapelessRecipeBuilder.shapeless(category, output, count).requires(input).unlockedBy(getHasName(input), has(input));
	}

	public static ShapelessRecipeBuilder oneToOneConversionRecipeBuilder(ItemLike output, ItemLike input, int count) {
		return oneToOneConversionRecipeBuilder(RecipeCategory.MISC, output, input, count);
	}

	public static void foodCookingRecipes(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.FOOD, output, 0.35F, 200).unlockedBy(getHasName(input), has(input)).save(consumer);
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), RecipeCategory.FOOD, output, 0.35F, 100).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_smoking");
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(input), RecipeCategory.FOOD, output, 0.35F, 600).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_campfire_cooking");
	}

	public static void leafPileRecipes(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafPile) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, leafPile, 4).requires(leaves).group("leaf_pile").unlockedBy(getHasName(leaves), has(leaves)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, leaves).define('#', leafPile).pattern("##").pattern("##").group("leaves").unlockedBy(getHasName(leafPile), has(leafPile)).save(consumer, getModConversionRecipeName(leaves, leafPile));
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike output, ItemLike input) {
		stonecutterResultFromBase(consumer, category, output, input, 1);
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike output, ItemLike input, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input) + "_stonecutting");
	}

	protected static ResourceLocation getModConversionRecipeName(ItemLike output, ItemLike input) {
		return new ResourceLocation(Environmental.MOD_ID, getConversionRecipeName(output, input));
	}

	public static void conditionalNineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeCategory itemCategory, ItemLike item, RecipeCategory storageCategory, ItemLike storage) {
		conditionalNineBlockStorageRecipes(consumer, condition, itemCategory, item, storageCategory, storage, Environmental.MOD_ID + ":" + getSimpleRecipeName(storage), null, Environmental.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	protected static void conditionalNineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeCategory itemCategory, ItemLike item, RecipeCategory storageCategory, ItemLike storage, String storageLocation, @Nullable String itemGroup, String itemLocation, @Nullable String storageGroup) {
		conditionalRecipe(consumer, condition, itemCategory, ShapelessRecipeBuilder.shapeless(itemCategory, item, 9).requires(storage).group(storageGroup).unlockedBy(getHasName(storage), has(storage)), new ResourceLocation(itemLocation));
		conditionalRecipe(consumer, condition, storageCategory, ShapedRecipeBuilder.shaped(storageCategory, storage).define('#', item).pattern("###").pattern("###").pattern("###").group(itemGroup).unlockedBy(getHasName(item), has(item)), new ResourceLocation(storageLocation));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeCategory category, RecipeBuilder recipe) {
		conditionalRecipe(consumer, condition, category, recipe, RecipeBuilder.getDefaultRecipeId(recipe.getResult()));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeCategory category, RecipeBuilder recipe, ResourceLocation id) {
		ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + category.getFolderName() + "/" + id.getPath())).build(consumer, id);
	}
}