package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public class PineTreeGrower extends AbstractTreeGrower {

	@Override
	public boolean growTree(ServerLevel level, ChunkGenerator chunkgenerator, BlockPos pos, BlockState state, RandomSource random) {
		ResourceKey<ConfiguredFeature<?, ?>> key = this.shouldBeTallPine(level, pos) ? this.getConfiguredLargeFeature() : this.getConfiguredFeature(random, false);
		if (key == null) {
			return false;
		} else {
			Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(key).orElse(null);
			SaplingGrowTreeEvent event = ForgeEventFactory.blockGrowFeature(level, random, pos, holder);
			holder = event.getFeature();
			if (event.getResult() == Event.Result.DENY) return false;
			if (holder == null) {
				return false;
			} else {
				ConfiguredFeature<?, ?> configuredfeature = holder.value();
				BlockState blockstate = level.getFluidState(pos).createLegacyBlock();
				level.setBlock(pos, blockstate, 4);
				if (configuredfeature.place(level, chunkgenerator, random, pos)) {
					if (level.getBlockState(pos) == blockstate) {
						level.sendBlockUpdated(pos, state, blockstate, 2);
					}

					return true;
				} else {
					level.setBlock(pos, state, 4);
					return false;
				}
			}
		}
	}

	private boolean shouldBeTallPine(ServerLevel level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(Blocks.PODZOL);
	}

	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return EnvironmentalConfiguredFeatures.PINE;
	}

	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredLargeFeature() {
		return EnvironmentalConfiguredFeatures.TALL_PINE;
	}
}