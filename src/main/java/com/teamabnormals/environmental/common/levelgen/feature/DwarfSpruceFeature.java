package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.DwarfSpruceHeadBlock;
import com.teamabnormals.environmental.common.block.DwarfSprucePlantBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
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
		if (level.isEmptyBlock(origin) && isDirt(level.getBlockState(origin.below()))) {
			int i = context.random().nextInt(3);
			if (i == 0 && level.isEmptyBlock(origin.above()) && level.isEmptyBlock(origin.above(2))) {
				level.setBlock(origin, EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get().defaultBlockState().setValue(DwarfSprucePlantBlock.BOTTOM, true), 2);
				level.setBlock(origin.above(), EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get().defaultBlockState().setValue(DwarfSprucePlantBlock.BOTTOM, false), 2);
				level.setBlock(origin.above(2), EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState().setValue(DwarfSpruceHeadBlock.TOP, true), 2);
			} else if (i <= 1 && level.isEmptyBlock(origin.above())) {
				level.setBlock(origin, EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get().defaultBlockState().setValue(DwarfSprucePlantBlock.BOTTOM, true), 2);
				level.setBlock(origin.above(), EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState().setValue(DwarfSpruceHeadBlock.TOP, true), 2);
			} else {
				level.setBlock(origin, EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState(), 2);
			}
			return true;
		}
		return false;
	}
}