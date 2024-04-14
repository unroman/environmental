package com.teamabnormals.environmental.core.other.SlabfishTypeRework;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DataPackRegistryEvent;

public final class EnvironmentalDataPackRegistries {
	public static final ResourceKey<Registry<SlabfishTypeEntry>> SLABFISH_TYPE = key("slabfish/type");
	public static final ResourceKey<Registry<SlabfishTypeEntry>> SLABFISH_BACKPACK = key("slabfish/backpack");
	public static final ResourceKey<Registry<SlabfishSweaterEntry>> SLABFISH_SWEATER = key("slabfish/sweater");

	public static void registerRegistries(DataPackRegistryEvent.NewRegistry event) {
	//	event.dataPackRegistry(SLABFISH_TYPE, SlabfishTypeEntry.CODEC);
		event.dataPackRegistry(SLABFISH_SWEATER, SlabfishSweaterEntry.CODEC);
	}

	private static <T> ResourceKey<Registry<T>> key(String name) {
		return ResourceKey.createRegistryKey(new ResourceLocation(Environmental.MOD_ID, name));
	}
}