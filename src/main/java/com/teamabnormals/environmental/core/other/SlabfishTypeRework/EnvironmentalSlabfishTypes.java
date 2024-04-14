package com.teamabnormals.environmental.core.other.SlabfishTypeRework;

import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishRarity;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class EnvironmentalSlabfishTypes {
	public static final ResourceKey<SlabfishTypeEntry> DESERT = createKey("desert");

	public static void bootstrap(BootstapContext<SlabfishTypeEntry> context) {
		context.register(DESERT, new SlabfishTypeEntry(
				Component.translatable(Util.makeDescriptionId("slabfish.type", DESERT.location())),
				SlabfishRarity.UNCOMMON, null, 100, false, true
		));
	}

	public static ResourceKey<SlabfishTypeEntry> createKey(String name) {
		return ResourceKey.create(EnvironmentalDataPackRegistries.SLABFISH_TYPE, new ResourceLocation(Environmental.MOD_ID, name));
	}

}
