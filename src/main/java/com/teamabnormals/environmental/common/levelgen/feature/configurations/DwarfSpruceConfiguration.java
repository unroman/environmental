package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record DwarfSpruceConfiguration(int density, double noiseToCountRatio) implements FeatureConfiguration {
	public static final Codec<DwarfSpruceConfiguration> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.INT.fieldOf("density").forGetter((config) -> config.density), Codec.DOUBLE.fieldOf("noise_to_count_ratio").forGetter((config) -> config.noiseToCountRatio)).apply(builder, DwarfSpruceConfiguration::new);
	});

	public DwarfSpruceConfiguration(int density, double noiseToCountRatio) {
		this.density = density;
		this.noiseToCountRatio = noiseToCountRatio;
	}

	@Override
	public int density() {
		return this.density;
	}

	public double noiseToCountRatio() {
		return this.noiseToCountRatio;
	}
}