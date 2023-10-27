package net.equosile.ofaafo.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class OFAAFO_Dice_Hexapip extends ExperienceDroppingBlock {

    private final IntProvider experience = UniformIntProvider.create(7, 17);

    public OFAAFO_Dice_Hexapip(Settings settings, IntProvider experience) {
        super(settings, experience);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.dice_hexapip.tooltip.shift"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.dice_hexapip.tooltip"));
        }
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit)
    {

        //SERVER + CLIENT + MAIN_HAND + OFF_HAND
        if (hand == Hand.MAIN_HAND) {
            if (player.isSneaking()) {

                double xOut = pos.getX() + 0.5;
                double yOut = pos.getY() + 1.5;
                double zOut = pos.getZ() + 0.5;

                DustParticleEffect dpe_Supernova_Dice =
                        new DustParticleEffect(new Vector3f(1.0F, 1.0F, 1.0F),1.0F);

                world.addParticle(dpe_Supernova_Dice, xOut, yOut, zOut, 0, -0.5, 0);

                //SERVER + MAIN_HAND
                if (!world.isClient()) {
                    //CONCURRENT EVENT AT SERVER PERSPECTIVE
                    //
                    world.breakBlock(pos, true);

                }
            }
        }

        return ActionResult.SUCCESS;
    }



}
