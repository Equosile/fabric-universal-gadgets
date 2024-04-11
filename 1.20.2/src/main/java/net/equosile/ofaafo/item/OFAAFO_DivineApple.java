package net.equosile.ofaafo.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OFAAFO_DivineApple extends Item {

    public OFAAFO_DivineApple(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.shift.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.shift.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.shift.line_3"));
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.shift.line_4"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.divine_apple.tooltip.line_3"));
        }
    }

}
