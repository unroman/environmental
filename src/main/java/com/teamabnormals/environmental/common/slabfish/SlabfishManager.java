package com.teamabnormals.environmental.common.slabfish;

import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.fml.LogicalSide;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * <p>Manages all slabfish for both sides.</p>
 *
 * @author Ocelot
 */
public interface SlabfishManager {
	/**
	 * Fetches the slabfish manager for the specified side.
	 *
	 * @param side The logical side to get the slabfish manager for
	 * @return The slabfish manager for that side
	 */
	static SlabfishManager get(LogicalSide side) {
		return side.isClient() ? ClientSlabfishManager.INSTANCE : SlabfishLoader.instance;
	}

	/**
	 * Same as {@link #get(LogicalSide)} but provides ease of access by using a world instead of {@link LogicalSide}.
	 *
	 * @param world The world to get the logical side from
	 * @return The slabfish manager for that side
	 */
	static SlabfishManager get(LevelReader world) {
		return world.isClientSide() ? ClientSlabfishManager.INSTANCE : SlabfishLoader.instance;
	}

	/**
	 * Checks through all slabfish types for a slabfish conditions that succeed in the current context.
	 *
	 * @param context The context of the slabfish
	 * @return The slabfish that that was selected to be the best fit for the context
	 */
	default Optional<SlabfishType> getSlabfishType(Registry<SlabfishType> registry, SlabfishConditionContext context) {
		return this.getSlabfishType(registry, __ -> true, context);
	}

	/**
	 * Checks through all slabfish types for a slabfish conditions that succeed in the current context.
	 *
	 * @param predicate The predicate to determine what kinds of slabfish to allow
	 * @param context   The context of the slabfish
	 * @return The slabfish that that was selected to be the best fit for the context
	 */
	Optional<SlabfishType> getSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, SlabfishConditionContext context);

	/**
	 * Checks the sweater types for a sweater using the specified stack.
	 *
	 * @param stack The stack to test against the sweater types
	 * @return The sweater type using that stack or {@link #EMPTY_SWEATER} if that item has no sweater type
	 */
	Optional<SweaterType> getSweaterType(Registry<SweaterType> registry, ItemStack stack);

	/**
	 * Checks the backpack types for a backpack using the specified stack.
	 *
	 * @param stack The stack to test against the backpack types
	 * @return The backpack type using that stack or {@link #BROWN_BACKPACK} if that item has no backpack type
	 */
	Optional<BackpackType> getBackpackType(Registry<BackpackType> registry, ItemStack stack);

	/**
	 * Fetches a random slabfish type by the specified {@link Predicate}.
	 *
	 * @param predicate The predicate to use when searching for a slabfish type
	 * @param random    The random to use for the index
	 * @return A random slabfish type by that rarity or {@link #DEFAULT_SLABFISH} if there were no results
	 */
	Optional<SlabfishType> getRandomSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, RandomSource random);
}
