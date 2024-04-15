package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishLoader;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishSweaters;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SlabfishBucketItem extends MobBucketItem {
	private static final Map<String, ResourceLocation> LOCATION_CACHE = new HashMap<>();
	private static final MutablePair<CompoundTag, ResourceLocation> SWEATER_TYPE_CACHE = new MutablePair<>(null, EnvironmentalSlabfishSweaters.EMPTY.location());

	public SlabfishBucketItem(Item.Properties builder) {
		super(EnvironmentalEntityTypes.SLABFISH::get, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, builder);
	}

	@Deprecated
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		CompoundTag tag = stack.getTag();
		if (tag != null) {
			if (tag.contains("SlabfishType", Tag.TAG_STRING)) {
				Registry<SlabfishType> registry = EnvironmentalRegistries.slabfishTypes(worldIn);

				SlabfishType slabfishType = registry.get(LOCATION_CACHE.computeIfAbsent(tag.getString("SlabfishType"), ResourceLocation::new));
				if (!registry.getKey(slabfishType).equals(EnvironmentalSlabfishTypes.SWAMP.location()))
					tooltip.add(slabfishType.displayName().copy().withStyle(ChatFormatting.ITALIC, SlabfishType.RARITIES.get(slabfishType.getRarity(worldIn)).getSecond()));
			}
			if (tag.contains("Age", Tag.TAG_ANY_NUMERIC) && tag.getInt("Age") < 0) {
				tooltip.add((Component.translatable("entity.environmental.slabfish.baby").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)));
			}
			if (tag.contains("BackpackType", Tag.TAG_STRING)) {
				Registry<BackpackType> registry = EnvironmentalRegistries.slabfishBackpacks(worldIn);
				BackpackType backpackType = registry.get(LOCATION_CACHE.computeIfAbsent(tag.getString("BackpackType"), ResourceLocation::new));
				tooltip.add(backpackType.displayName().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
			}

			Registry<SweaterType> sweaters = EnvironmentalRegistries.slabfishSweaters(worldIn);

			if (!tag.equals(SWEATER_TYPE_CACHE.getLeft())) {
				SWEATER_TYPE_CACHE.setLeft(tag);
				SWEATER_TYPE_CACHE.setRight(EnvironmentalSlabfishSweaters.EMPTY.location());

				ListTag list = tag.getList("Items", Tag.TAG_COMPOUND);
				for (int i = 0; i < list.size(); i++) {
					CompoundTag slotNbt = list.getCompound(i);
					int index = slotNbt.getByte("Slot") & 255;
					if (index == 0) {
						ItemStack slotStack = ItemStack.of(slotNbt);
						Optional<SweaterType> sweater = SlabfishLoader.getSweaterType(sweaters, slotStack);
						SWEATER_TYPE_CACHE.setRight(sweater.isPresent() ? sweaters.getKey(sweater.get()) : EnvironmentalSlabfishSweaters.EMPTY.location());
						break;
					}
				}
			}

			SweaterType type = sweaters.get(SWEATER_TYPE_CACHE.getRight());
			if (type != null && type.displayName().isPresent() && !EnvironmentalSlabfishSweaters.EMPTY.location().equals(SWEATER_TYPE_CACHE.getRight()))
				tooltip.add(type.displayName().get().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
		}
	}
}