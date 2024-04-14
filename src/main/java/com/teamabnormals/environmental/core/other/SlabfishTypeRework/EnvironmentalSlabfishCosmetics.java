package com.teamabnormals.environmental.core.other.SlabfishTypeRework;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class EnvironmentalSlabfishCosmetics {

	public static void bootstrapSweaters(BootstapContext<SlabfishCosmeticEntry> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<SlabfishCosmeticEntry> key = createSweaterKey(color.getName());
			context.register(key, SlabfishCosmeticEntry.create(Component.translatable(Util.makeDescriptionId("slabfish.sweater", key.location())), Sheep.ITEM_BY_DYE.get(color).asItem()));
		}
	}

	public static void bootstrapBackpacks(BootstapContext<SlabfishCosmeticEntry> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<SlabfishCosmeticEntry> key = createBackpackKey(color.getName());
			context.register(key, SlabfishCosmeticEntry.create(Component.translatable(Util.makeDescriptionId("slabfish.backpack", key.location())), color.getTag()));
		}
	}

	public static ResourceKey<SlabfishCosmeticEntry> createSweaterKey(String name) {
		return ResourceKey.create(EnvironmentalDataPackRegistries.SLABFISH_SWEATER, new ResourceLocation(Environmental.MOD_ID, name));
	}

	public static ResourceKey<SlabfishCosmeticEntry> createBackpackKey(String name) {
		return ResourceKey.create(EnvironmentalDataPackRegistries.SLABFISH_BACKPACK, new ResourceLocation(Environmental.MOD_ID, name));
	}
}
