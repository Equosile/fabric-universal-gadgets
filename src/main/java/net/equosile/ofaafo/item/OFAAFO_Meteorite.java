package net.equosile.ofaafo.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OFAAFO_Meteorite extends Item {

    //The Maximum cookTime is 32767 (ref. FuelRegistryImpl).
    private static int burnTime = 32767;
    public OFAAFO_Meteorite(Settings settings) {
        super(settings);
        this.burnTime = burnTime;
    }

    public static int getBurnTime() {
        return burnTime;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.meteorite.tooltip.shift.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.meteorite.tooltip.shift.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.meteorite.tooltip.shift.line_3"));
            tooltip.add(Text.translatable("tooltip.ofaafo.meteorite.tooltip.shift.line_4"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.meteorite.tooltip.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.meteorite.tooltip.line_2"));
        }
        //super.appendTooltip(stack, world, tooltip, context);
    }
}
