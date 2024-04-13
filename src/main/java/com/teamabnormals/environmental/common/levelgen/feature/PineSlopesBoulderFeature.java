package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PineSlopesBoulderFeature extends Feature<BlockStateConfiguration> {

	public PineSlopesBoulderFeature(Codec<BlockStateConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
		BlockStateConfiguration config = context.config();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();

		while (true) {
			if (pos.getY() <= 3)
				return false;
			else if (!level.isEmptyBlock(pos.below())) {
				int i1 = random.nextInt(2);
				if (random.nextInt(4) == 0)
					i1 += 1;

				boolean filledwithemeralds = random.nextInt(100) == 0;
				boolean hasemeralds = random.nextInt(7) == 0 || filledwithemeralds;

				Set<BlockPos> blockpositions = new HashSet<>();

				for (int l = 0; l < 3; ++l) {
					int i = i1 + random.nextInt(2);
					int j = i1 + random.nextInt(2);
					int k = i1 + random.nextInt(2);
					float f = (float) (i + j + k) * 0.333F + 0.5F;

					for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-i, -j, -k), pos.offset(i, j, k))) {
						if (blockpos.distSqr(pos) <= (double) (f * f)) {
							if (hasemeralds)
								blockpositions.add(blockpos.immutable());
							level.setBlock(blockpos, config.state, 4);
						}
					}

					pos = pos.offset(-(i1 + 1) + random.nextInt(2 + i1 * 2), -random.nextInt(2), -(i1 + 1) + random.nextInt(2 + i1 * 2));
					if (level.isEmptyBlock(pos.below()))
						break;
				}

				if (hasemeralds) {
					MutableBlockPos mutable = new MutableBlockPos();
					List<BlockPos> insidepositions = new ArrayList<>();

					for (BlockPos blockpos : blockpositions) {
						boolean inside = true;
						if (inside) {
							for (Direction direction : Direction.values())
								if (!blockpositions.contains(mutable.setWithOffset(blockpos, direction)))
									inside = false;
						}
						if (inside)
							insidepositions.add(blockpos);
					}

					if (filledwithemeralds) {
						for (BlockPos blockpos : insidepositions)
							level.setBlock(blockpos, Blocks.EMERALD_ORE.defaultBlockState(), 4);
					} else {
						int emeralds = 2 + random.nextInt(2);
						for (int i = 0; i < emeralds; ++i) {
							if (insidepositions.isEmpty())
								break;
							level.setBlock(insidepositions.remove(random.nextInt(insidepositions.size())), Blocks.EMERALD_ORE.defaultBlockState(), 4);
						}
					}
				}

				return true;
			}

			pos = pos.below();
		}
	}
}