package com.teamabnormals.environmental.common.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public interface DwarfSpruceBlock {
	Item getTorch();

	BlockState getWithoutTorchesState(BlockState state);
}