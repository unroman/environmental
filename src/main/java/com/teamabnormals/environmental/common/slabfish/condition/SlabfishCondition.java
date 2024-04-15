package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Predicate;

public interface SlabfishCondition extends Predicate<SlabfishConditionContext> {
	Codec<SlabfishCondition> CODEC = ExtraCodecs.lazyInitializedCodec(() -> EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS_REGISTRY.get().getCodec().dispatch(SlabfishCondition::getType, SlabfishConditionType::getCodec));

	@Override
	boolean test(SlabfishConditionContext context);

	SlabfishConditionType getType();
}
