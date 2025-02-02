package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeleton {

	protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeleton> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
	private void populateDefaultEquipmentSlots(DifficultyInstance difficulty, CallbackInfo info) {
		if (Math.random() < 0.025F) {
			this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(EnvironmentalItems.THIEF_HOOD.get()));
			this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 1.0F;
		}
	}
}
