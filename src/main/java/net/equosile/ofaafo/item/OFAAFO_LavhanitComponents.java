package net.equosile.ofaafo.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class OFAAFO_LavhanitComponents {



    //FOOD
    public static final FoodComponent template_DIVINE_APPLE = new FoodComponent.Builder()
            .hunger(9).saturationModifier(1.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6666, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6666, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6666, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 6666, 6), 1.0F)
            .alwaysEdible().build();



}
