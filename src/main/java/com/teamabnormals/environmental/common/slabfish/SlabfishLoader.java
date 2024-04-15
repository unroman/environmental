package com.teamabnormals.environmental.common.slabfish;

import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishSweaters;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SlabfishLoader {

	public static Optional<SlabfishType> getSlabfishType(Registry<SlabfishType> registry, SlabfishConditionContext context) {
		return getSlabfishType(registry, __ -> true, context);
	}

	public static Optional<SlabfishType> getSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, SlabfishConditionContext context) {
		return registry.stream().filter(slabfishType -> !registry.getKey(slabfishType).equals(EnvironmentalSlabfishTypes.SWAMP.location()) && predicate.test(slabfishType) && slabfishType.test(context)).max(Comparator.comparingInt(SlabfishType::priority));
	}

	public static Optional<SweaterType> getSweaterType(Registry<SweaterType> registry, ItemStack stack) {
		return registry.stream().filter(sweaterType -> sweaterType != registry.get(EnvironmentalSlabfishSweaters.EMPTY) && sweaterType.test(stack)).findFirst();
	}

	public static Optional<BackpackType> getBackpackType(Registry<BackpackType> registry, ItemStack stack) {
		return registry.stream().filter(backpackType -> backpackType.test(stack)).findFirst();
	}

	public static Optional<SlabfishType> getRandomSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, RandomSource random) {
		return Util.getRandomSafe(registry.stream().filter(predicate).collect(Collectors.toList()), random);
	}
}
