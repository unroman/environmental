package com.teamabnormals.environmental.core.other.SlabfishTypeRework;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class EnvironmentalSlabfishSweaters {

	public static void bootstrap(BootstapContext<SlabfishSweaterEntry> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<SlabfishSweaterEntry> key = createKey(color.getName());
			context.register(key, SlabfishSweaterEntry.create(Component.translatable(Util.makeDescriptionId("slabfish.sweater", key.location())), Sheep.ITEM_BY_DYE.get(color).asItem()));
		}
	}

	public static ResourceKey<SlabfishSweaterEntry> createKey(String name) {
		return ResourceKey.create(EnvironmentalDataPackRegistries.SLABFISH_SWEATER, new ResourceLocation(Environmental.MOD_ID, name));
	}

}
