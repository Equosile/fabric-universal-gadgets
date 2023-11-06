package net.equosile.ofaafo.block;

import net.equosile.ofaafo.event.OFAAFO_TickEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OFAAFO_Dice_Monopip extends ExperienceDroppingBlock {

    public static final int monopip_MAX_ENERGY = 1;
    public static final IntProperty monopipEnergy = IntProperty.of("monopip_energy", 0, monopip_MAX_ENERGY);
    public OFAAFO_Dice_Monopip(Settings settings, IntProvider experience) {
        super(settings, experience);
        this.setDefaultState(this.getDefaultState().with(monopipEnergy, monopip_MAX_ENERGY));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(monopipEnergy);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.dice_monopip.tooltip.shift"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.dice_monopip.tooltip"));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        //SERVER + CLIENT + MAIN_HAND + OFF_HAND
        if (!world.isClient()) {
            //CONCURRENT EVENT AT SERVER PERSPECTIVE
            //SERVER + MAIN_HAND + OFF_HAND
            if (hand == Hand.MAIN_HAND) {
                if (player.isSneaking()) {
                    monopip_Withdraw_Redstone(pos);

                    double xOut = pos.getX() + 0.5;
                    double yOut = pos.getY() + 1.5;
                    double zOut = pos.getZ() + 0.5;
                    DustParticleEffect dpe_Supernova_Dice =
                            new DustParticleEffect(new Vector3f(1.0F, 1.0F, 1.0F), 1.0F);
                    world.addParticle(dpe_Supernova_Dice, xOut, yOut, zOut, 0, -0.5, 0);

                    world.breakBlock(pos, true);
                } else {
                    monopip_Emit_Redstone(state, world, pos);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        //super.onBroken(world, pos, state);
        monopip_Withdraw_Redstone(pos);
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        //return super.getStrongRedstonePower(state, world, pos, direction);

        return monopipCheckState(state) ? monopip_MAX_ENERGY : 0;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        //return super.getWeakRedstonePower(state, world, pos, direction);

        return monopipCheckState(state) ? monopip_MAX_ENERGY : 0;
    }

    public static Optional<IntProperty> getIntProperty(BlockState state, String propertyName) {
        for (Property<?> propertyComponent : state.getProperties()) {
            if (propertyComponent.getName().equals(propertyName) && propertyComponent instanceof IntProperty) {
                return Optional.of((IntProperty) propertyComponent);
            }
        }
        return Optional.empty();
    }
    public static boolean monopipCheckState(BlockState state) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "monopip_energy");

        if (oldStateProperty.isPresent()) {
            IntProperty specimenTemplate = oldStateProperty.get();
            List<Integer> valueCycle = new ArrayList<>(specimenTemplate.getValues());

            int currentValue = valueCycle.indexOf(state.get(specimenTemplate));
            if (currentValue < valueCycle.size() - 1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    public static void monopipRotateState(BlockState state, World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock().equals(OFAAFO_Blocks.DICE_MONOPIP)) {
            Optional<IntProperty> oldStateProperty = getIntProperty(state, "monopip_energy");

            if (oldStateProperty.isPresent()) {
                IntProperty specimenTemplate = oldStateProperty.get();
                List<Integer> valueCycle = new ArrayList<>(specimenTemplate.getValues());

                int currentValue = valueCycle.indexOf(state.get(specimenTemplate));
                int mendedValue = currentValue - 1;
                if (mendedValue < 0) {
                    mendedValue = valueCycle.size() - 1;
                }

                BlockState nextEnergy = state.with(specimenTemplate, valueCycle.get(mendedValue));
                world.setBlockState(pos, nextEnergy);
                world.updateNeighborsAlways(pos, world.getBlockState(pos).getBlock());
            }
        } else {
            // This is of the client side where server operations don't work with.
            // This part cannot proceed to the following commands.
            // --(x)--> monopip_Withdraw_Redstone(world, pos);
        }
    }
    public static void monopip_Emit_Redstone(BlockState state, World world, BlockPos pos) {
        if (!world.isClient()) {
            MinecraftServer server = world.getServer();

            int xPos = pos.getX();
            int yPos = pos.getY();
            int zPos = pos.getZ();
            String unique_KEY_EVENT = "monopip_redstone~" + xPos + "~" + yPos + "~" + zPos;

            OFAAFO_TickEvent.addPersistentEventInfo(
                    Arrays.asList(server, unique_KEY_EVENT, 10, state, world, pos)
            );
            OFAAFO_TickEvent.addPersistentEvent(
                    () ->
                            monopipRotateState(state, world, pos)
            );
        }
    }
    public static void monopip_Withdraw_Redstone(BlockPos pos) {
        // This is eventually of the server operations.
        if (OFAAFO_TickEvent.sizeOfPersistentEvent() == OFAAFO_TickEvent.sizeOfPersistentEventInfo()) {
            int num_EventLoopSize = OFAAFO_TickEvent.sizeOfPersistentEvent();
            int index = -1;
            for (int i = 0; i < num_EventLoopSize; i = i + 1) {
                Object specimen_info = OFAAFO_TickEvent.QUEUE_PERSISTENT_INFO.get(i);

                if (specimen_info instanceof List) {
                    List<?> item = (List<?>) specimen_info;
                    int xEvent = pos.getX();
                    int yEvent = pos.getY();
                    int zEvent = pos.getZ();
                    String keyword = "monopip_redstone~" + xEvent + "~" + yEvent + "~" + zEvent;

                    if (item.contains(keyword)) {
                        index = i;
                        OFAAFO_TickEvent.QUEUE_PERSISTENT_INFO.remove(index);
                        OFAAFO_TickEvent.QUEUE_PERSISTENT.remove(index);
                        break;
                    }
                }
            }
        } else {
            //OFAAFO_TickEvent.QUEUE_PERSISTENT.clear();
            //OFAAFO_TickEvent.QUEUE_PERSISTENT_INFO.clear();
        }
    }

}
