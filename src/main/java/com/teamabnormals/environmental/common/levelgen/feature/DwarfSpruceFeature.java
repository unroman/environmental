package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.DwarfSpruceTallBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DwarfSpruceFeature extends Feature<NoneFeatureConfiguration> {

	public DwarfSpruceFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		if (level.isEmptyBlock(origin) && EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState().canSurvive(level, origin)) {
			if (context.random().nextInt(3) == 0 || !level.isEmptyBlock(origin)) {
				level.setBlock(origin, EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState(), 2);
			} else {
				level.setBlock(origin, EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DwarfSpruceTallBlock.HALF, DoubleBlockHalf.LOWER), 2);
				level.setBlock(origin.above(), EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DwarfSpruceTallBlock.HALF, DoubleBlockHalf.UPPER), 2);
			}
			return true;
		}
		return false;
	}
}