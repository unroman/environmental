package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class WisteriaTree extends AbstractTreeGrower {
	private final WisteriaColor color;

	public WisteriaTree(WisteriaColor colorIn) {
		color = colorIn;
	}

	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return this.getFeatureForColor(this.color, beehive);
	}

	private ResourceKey<ConfiguredFeature<?, ?>> getFeatureForColor(WisteriaColor color, boolean beehive) {
		return switch (color) {
			case PURPLE -> beehive ? EnvironmentalConfiguredFeatures.PURPLE_WISTERIA_BEES_005 : EnvironmentalConfiguredFeatures.PURPLE_WISTERIA;
			case WHITE -> beehive ? EnvironmentalConfiguredFeatures.WHITE_WISTERIA_BEES_005 : EnvironmentalConfiguredFeatures.WHITE_WISTERIA;
			case PINK -> beehive ? EnvironmentalConfiguredFeatures.PINK_WISTERIA_BEES_005 : EnvironmentalConfiguredFeatures.PINK_WISTERIA;
			case BLUE -> beehive ? EnvironmentalConfiguredFeatures.BLUE_WISTERIA_BEES_005 : EnvironmentalConfiguredFeatures.BLUE_WISTERIA;
		};
	}
}