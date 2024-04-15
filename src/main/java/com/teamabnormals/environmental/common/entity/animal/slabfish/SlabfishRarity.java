package com.teamabnormals.environmental.common.entity.animal.slabfish;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;

/**
 * <p>Determines the different rarity types for slabfish.</p>
 */
public enum SlabfishRarity implements StringRepresentable {
	COMMON("common", ChatFormatting.GRAY, 1.0F),
	UNCOMMON("uncommon", ChatFormatting.GREEN, 0.55F),
	RARE("rare", ChatFormatting.AQUA, 0.15F),
	EPIC("epic", ChatFormatting.LIGHT_PURPLE, 0.08F),
	LEGENDARY("legendary", ChatFormatting.GOLD, 0.02F);

	public static final Codec<SlabfishRarity> CODEC = StringRepresentable.fromEnum(SlabfishRarity::values);

	private final String serializationKey;
	private final ChatFormatting color;
	private final float chance;

	SlabfishRarity(String key, ChatFormatting color, float chance) {
		this.serializationKey = key;
		this.color = color;
		this.chance = chance;
	}

	/**
	 * @return The {@link ChatFormatting} that should be used when displaying this rarity
	 */
	public ChatFormatting getFormatting() {
		return color;
	}

	/**
	 * @return The percentage chance of getting this rarity
	 */
	public float getChance() {
		return chance;
	}

	/**
	 * Fetches a rarity by a percentage chance.
	 *
	 * @param chance The chance randomly select a rarity
	 * @return The rarity associated with that chance
	 */
	public static SlabfishRarity byChance(float chance) {
		for (int i = values().length - 1; i > 0; i--)
			if (chance <= values()[i].getChance())
				return values()[i];
		return COMMON;
	}

	/**
	 * Checks the rarity types for a rarity with the specified id.
	 *
	 * @param id The id of the rarity
	 * @return The rarity by that id or {@link #COMMON} if there is no rarity by that id
	 */
	public static SlabfishRarity byId(int id) {
		if (id < 0 || id >= values().length)
			return COMMON;
		return values()[id];
	}

	public String getSerializationKey() {
		return this.serializationKey;
	}

	@Override
	public String getSerializedName() {
		return this.getSerializationKey();
	}
}
