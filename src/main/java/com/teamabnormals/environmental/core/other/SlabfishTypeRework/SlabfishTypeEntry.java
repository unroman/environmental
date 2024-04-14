package com.teamabnormals.environmental.core.other.SlabfishTypeRework;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.blueprint.common.codec.NullableFieldCodec;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishRarity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public record SlabfishTypeEntry(Component displayName, SlabfishRarity rarity, ResourceLocation customBackpack, int priority, boolean translucent, boolean tradeable) {
	public static final Codec<SlabfishTypeEntry> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ExtraCodecs.COMPONENT.fieldOf("displayName").forGetter(entry -> entry.displayName),
				SlabfishRarity.CODEC.fieldOf("rarity").forGetter(entry -> entry.rarity),
				ResourceLocation.CODEC.optionalFieldOf("custom_backpack", null).forGetter(entry -> entry.customBackpack),
				NullableFieldCodec.nullable("priority", Codec.INT, 100).forGetter(entry -> entry.priority),
				Codec.BOOL.optionalFieldOf("translucent", false).forGetter(entry -> entry.translucent),
				Codec.BOOL.optionalFieldOf("tradeable", true).forGetter(entry -> entry.tradeable)
		).apply(instance, SlabfishTypeEntry::new);
	});
}
