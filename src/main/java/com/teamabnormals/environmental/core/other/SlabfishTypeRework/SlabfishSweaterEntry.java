package com.teamabnormals.environmental.core.other.SlabfishTypeRework;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public record SlabfishSweaterEntry(Component displayName, Optional<Holder<Item>> item, Optional<TagKey<Item>> tagKey) {
	public static final Codec<SlabfishSweaterEntry> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ExtraCodecs.COMPONENT.fieldOf("displayName").forGetter(entry -> entry.displayName),
				RegistryFixedCodec.create(Registries.ITEM).optionalFieldOf("item").forGetter(entry -> entry.item),
				TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(entry -> entry.tagKey)
		).apply(instance, SlabfishSweaterEntry::new);
	});

	public static SlabfishSweaterEntry create(Component displayName, ItemLike item) {
		return new SlabfishSweaterEntry(displayName, Optional.of(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem())), Optional.empty());
	}

	public static SlabfishSweaterEntry create(Component displayName, TagKey<Item> tag) {
		return new SlabfishSweaterEntry(displayName, Optional.empty(), Optional.of(tag));
	}
}
