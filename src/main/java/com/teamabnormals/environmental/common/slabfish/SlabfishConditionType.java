package com.teamabnormals.environmental.common.slabfish;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition;

public class SlabfishConditionType {
	private final Codec<? extends SlabfishCondition> codec;

	public SlabfishConditionType(Codec<? extends SlabfishCondition> codec) {
		this.codec = codec;
	}

	public Codec<? extends SlabfishCondition> getCodec() {
		return codec;
	}
}