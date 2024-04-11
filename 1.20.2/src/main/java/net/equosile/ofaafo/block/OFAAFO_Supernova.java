package net.equosile.ofaafo.block;



import net.equosile.ofaafo.event.OFAAFO_TickEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OFAAFO_Supernova extends ExperienceDroppingBlock {

    private final IntProvider experience = UniformIntProvider.create(1237, 2474);
    public static final int supernova_MAX_ENERGY = 3;
    public static final IntProperty supernovaEnergy = IntProperty.of("supernova_energy", 0, supernova_MAX_ENERGY);

    public OFAAFO_Supernova(Settings settings, IntProvider experience) {
        super(settings, experience);
        this.setDefaultState(this.getDefaultState().with(supernovaEnergy, supernova_MAX_ENERGY));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(supernovaEnergy);
    }



    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.supernova.tooltip.shift.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.supernova.tooltip.shift.line_2"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.supernova.tooltip.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.supernova.tooltip.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.supernova.tooltip.line_3"));
        }
        //super.appendTooltip(stack, world, tooltip, options);
    }



    public static void complainingSupernova(PlayerEntity player) {
        player.sendMessage(Text.literal("§eSupernova§r: ..."));
    }
    public static Optional<IntProperty> getIntProperty(BlockState state, String propertyName) {
        for (Property<?> propertyComponent : state.getProperties()) {
            if (propertyComponent.getName().equals(propertyName) && propertyComponent instanceof IntProperty) {
                return Optional.of((IntProperty) propertyComponent);
            }
        }
        return Optional.empty();
    }

    public static boolean supernovaCheckState(BlockState state) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "supernova_energy");

        if (oldStateProperty.isPresent()) {
            IntProperty specimenTemplate = oldStateProperty.get();
            List<Integer> valueCycle = new ArrayList<>(specimenTemplate.getValues());

            int currentValue = valueCycle.indexOf(state.get(specimenTemplate));
            if (currentValue > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static void supernovaMendState(BlockState state, World world, BlockPos pos) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "supernova_energy");

        if (oldStateProperty.isPresent()) {
            IntProperty specimenTemplate = oldStateProperty.get();
            List<Integer> valueCycle = new ArrayList<>(specimenTemplate.getValues());

            int currentValue = valueCycle.indexOf(state.get(specimenTemplate));
            int mendedValue = currentValue - 1;
            if (mendedValue < 0) {
                mendedValue = 0;
            }

            BlockState nextEnergy = state.with(specimenTemplate, valueCycle.get(mendedValue));
            world.setBlockState(pos, nextEnergy);

        }
    }

    public static void supernovaRefillEnergy(BlockState state, World world, BlockPos pos) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "supernova_energy");

        if (oldStateProperty.isPresent()) {
            IntProperty specimenTemplate = oldStateProperty.get();
            List<Integer> valueCycle = new ArrayList<>(specimenTemplate.getValues());

            int maxValue = valueCycle.size() - 1;

            BlockState nextEnergy = state.with(specimenTemplate, valueCycle.get(maxValue));
            world.setBlockState(pos, nextEnergy);

        }
    }

    public static ItemEntity popEnigma(String subject, World world, double x, double y, double z, PlayerEntity player) {
        ItemStack energyPop = new ItemStack(OFAAFO_Blocks.ENIGMA);
        String msg_FromSubject = "§e" + subject + "§r: Puff...!";
        player.sendMessage(Text.literal(msg_FromSubject));
        return new ItemEntity(world, x, y, z, energyPop);
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit)
    {

        //SERVER + CLIENT + MAIN_HAND + OFF_HAND
        if (!world.isClient()) {
            //SERVER + MAIN_HAND + OFF_HAND
            if (player.isSneaking()) {
                if (hand == Hand.MAIN_HAND) {

                    double xOut = pos.getX() + 0.5;
                    double yOut = pos.getY() + 1.5;
                    double zOut = pos.getZ() + 0.5;

                    DustParticleEffect dpe_Supernova_Puff =
                            new DustParticleEffect(new Vector3f(1.0F, 1.0F, 1.0F), 1.0F);

                    world.addParticle(dpe_Supernova_Puff, xOut, yOut, zOut, 0, -0.5, 0);

                    //Debugger
                    //player.sendMessage(Text.literal("§eSupernova§r: Activation" +
                    //        "§r @ [ " + xOut + ", " + yOut + ", " + zOut + " ]"
                    //));

                    //CONCURRENT EVENT AT SERVER PERSPECTIVE
                    //

                    if (supernovaCheckState(state)) {
                        //energy_Supernova = energy_Supernova - 1;
                        supernovaMendState(state, world, pos);
                        //Debugger
                        //player.sendMessage(Text.literal("§eSupernova§r: " +
                        //        getEnergySupernova() + "/" + MAX_ENERGY_SUPERNOVA +
                        //        "§r @ [ " + xOut + ", " + yOut + ", " + zOut + " ]"
                        //));

                        //Only Way of Generating Dice for the First Time
                        OFAAFO_TickEvent.ofaafo_voidScheduleTaskB(
                                () ->
                                        world.spawnEntity(
                                                popEnigma("Supernova", world, xOut, yOut, zOut, player)
                                        )
                        );
                        OFAAFO_TickEvent.ofaafo_voidScheduleTaskD(
                                () ->
                                        supernovaRefillEnergy(state, world, pos)
                        );
                    } else {
                        complainingSupernova(player);
                    }

                }
            }
        }

        return ActionResult.SUCCESS;
    }



}
