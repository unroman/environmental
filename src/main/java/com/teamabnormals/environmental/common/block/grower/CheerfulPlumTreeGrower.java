package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class CheerfulPlumTreeGrower extends AbstractTreeGrower {

	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return beehive ? EnvironmentalConfiguredFeatures.CHEERFUL_PLUM_BEES_005 : EnvironmentalConfiguredFeatures.CHEERFUL_PLUM;
	}
}