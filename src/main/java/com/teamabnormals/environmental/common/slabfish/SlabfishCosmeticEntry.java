package com.teamabnormals.environmental.common.slabfish;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public record SlabfishCosmeticEntry(Component displayName, ResourceLocation texture, Optional<Holder<Item>> item, Optional<TagKey<Item>> tagKey) {
	public static final Codec<SlabfishCosmeticEntry> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ExtraCodecs.COMPONENT.fieldOf("displayName").forGetter(entry -> entry.displayName),
				ResourceLocation.CODEC.fieldOf("texture").forGetter(entry -> entry.texture),
				RegistryFixedCodec.create(Registries.ITEM).optionalFieldOf("item").forGetter(entry -> entry.item),
				TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(entry -> entry.tagKey)
		).apply(instance, SlabfishCosmeticEntry::new);
	});

	public static SlabfishCosmeticEntry create(Component displayName, ResourceLocation texture, ItemLike item) {
		return new SlabfishCosmeticEntry(displayName, texture, Optional.of(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem())), java.util.Optional.empty());
	}

	public static SlabfishCosmeticEntry create(Component displayName, ResourceLocation texture, TagKey<Item> tag) {
		return new SlabfishCosmeticEntry(displayName, texture, Optional.empty(), Optional.of(tag));
	}
}
