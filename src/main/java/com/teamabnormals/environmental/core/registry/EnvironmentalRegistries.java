package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DataPackRegistryEvent;

public final class EnvironmentalRegistries {
	public static final ResourceKey<Registry<SlabfishType>> SLABFISH_TYPE = key("slabfish/type");
	public static final ResourceKey<Registry<BackpackType>> SLABFISH_BACKPACK = key("slabfish/backpack");
	public static final ResourceKey<Registry<SweaterType>> SLABFISH_SWEATER = key("slabfish/sweater");

	public static void registerRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(SLABFISH_TYPE, SlabfishType.CODEC, SlabfishType.NETWORK_CODEC);
		event.dataPackRegistry(SLABFISH_BACKPACK, BackpackType.CODEC, BackpackType.NETWORK_CODEC);
		event.dataPackRegistry(SLABFISH_SWEATER, SweaterType.CODEC, SweaterType.CODEC);
	}

	private static <T> ResourceKey<Registry<T>> key(String name) {
		return ResourceKey.createRegistryKey(new ResourceLocation(Environmental.MOD_ID, name));
	}

	public static Registry<SlabfishType> slabfishTypes(Level level) {
		return level.registryAccess().registryOrThrow(SLABFISH_TYPE);
	}

	public static Registry<SweaterType> slabfishSweaters(Level level) {
		return level.registryAccess().registryOrThrow(SLABFISH_SWEATER);
	}

	public static Registry<BackpackType> slabfishBackpacks(Level level) {
		return level.registryAccess().registryOrThrow(SLABFISH_BACKPACK);
	}
}