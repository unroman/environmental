package com.teamabnormals.environmental.core.registry.slabfish;

import com.teamabnormals.environmental.common.slabfish.SlabfishCosmeticEntry;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class EnvironmentalSlabfishBackpacks {

	public static void bootstrap(BootstapContext<SlabfishCosmeticEntry> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<SlabfishCosmeticEntry> key = createKey(color.getName());
			context.register(key, SlabfishCosmeticEntry.create(
					Component.translatable(Util.makeDescriptionId("slabfish.backpack", key.location())),
					new ResourceLocation(key.location().getNamespace(), "backpack/" + key.location().getPath()),
					color.getTag()));
		}
	}

	public static ResourceKey<SlabfishCosmeticEntry> createKey(String name) {
		return ResourceKey.create(EnvironmentalRegistries.SLABFISH_BACKPACK, new ResourceLocation(Environmental.MOD_ID, name));
	}
}
