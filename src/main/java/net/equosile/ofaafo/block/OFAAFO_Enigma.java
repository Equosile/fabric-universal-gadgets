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
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class OFAAFO_Enigma extends ExperienceDroppingBlock {

    private final IntProvider experience = UniformIntProvider.create(1237, 2474);
    public static final int enigma_MAX_ENERGY = 6;
    public static final IntProperty enigmaEnergy = IntProperty.of("enigma_energy", 0, enigma_MAX_ENERGY);



    public OFAAFO_Enigma(Settings settings, IntProvider experience) {
        super(settings, experience);
        this.setDefaultState(this.getDefaultState().with(enigmaEnergy, enigma_MAX_ENERGY));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(enigmaEnergy);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.enigma.tooltip.shift.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.enigma.tooltip.shift.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.enigma.tooltip.shift.line_3"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.enigma.tooltip.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.enigma.tooltip.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.enigma.tooltip.line_3"));
        }
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

                    DustParticleEffect dpe_Enigma_Dice =
                            new DustParticleEffect(new Vector3f(1.0F, 1.0F, 1.0F), 1.0F);

                    world.addParticle(dpe_Enigma_Dice, xOut, yOut, zOut, 0, -0.5, 0);

                    //CONCURRENT EVENT AT SERVER PERSPECTIVE
                    //

                    if (enigmaCheckState(state)) {
                        //enigmaEnergy = enigmaEnergy - 1;
                        enigmaMendState(state, world, pos);

                        //Only Way of Generating Dice for the First Time
                        OFAAFO_TickEvent.ofaafo_voidScheduleTaskB(
                                () ->
                                        world.spawnEntity(
                                                popDice("Enigma", world, xOut, yOut, zOut, player)
                                        )
                        );
                        OFAAFO_TickEvent.ofaafo_voidScheduleTaskC(
                                () ->
                                        enigmaRefillEnergy(state, world, pos)
                        );
                    } else {
                        complainingEnigma(player);
                    }

                }
            }
        }

        return ActionResult.SUCCESS;
    }



    public static void complainingEnigma(PlayerEntity player) {
        player.sendMessage(Text.literal("§eEnigma§r: ..."));
    }

    public static ItemEntity popDice(String subject, World world, double x, double y, double z, PlayerEntity player) {
        ItemStack energyPopA = new ItemStack(OFAAFO_Blocks.DICE_MONOPIP);
        ItemStack energyPopB = new ItemStack(OFAAFO_Blocks.DICE_DIPIP);
        ItemStack energyPopC = new ItemStack(OFAAFO_Blocks.DICE_TRIPIP);
        ItemStack energyPopD = new ItemStack(OFAAFO_Blocks.DICE_TETRAPIP);
        ItemStack energyPopE = new ItemStack(OFAAFO_Blocks.DICE_PENTAPIP);
        ItemStack energyPopF = new ItemStack(OFAAFO_Blocks.DICE_HEXAPIP);

        SecureRandom obfuscatedSeed = new SecureRandom();
        // Generating a random number between 1 and 6, inclusive.
        int i_rollingDice = obfuscatedSeed.nextInt(6) + 1;
        String msg_FromSubject = "§e" + subject + "§r: The roll of §a" + player.getName().getString() +
                "§r is §b" + i_rollingDice + "§r.";
        player.sendMessage(Text.literal(msg_FromSubject));

        switch (i_rollingDice) {
            case 1:
                return new ItemEntity(world, x, y, z, energyPopA);

            case 2:
                return new ItemEntity(world, x, y, z, energyPopB);

            case 3:
                return new ItemEntity(world, x, y, z, energyPopC);

            case 4:
                return new ItemEntity(world, x, y, z, energyPopD);

            case 5:
                return new ItemEntity(world, x, y, z, energyPopE);

            case 6:
                return new ItemEntity(world, x, y, z, energyPopF);

            default:
                String msgError_FromSubject = "§e" + subject + "§r: Error!";
                player.sendMessage(Text.literal(msgError_FromSubject));
                return new ItemEntity(world, x, y, z,
                        new ItemStack(Items.DIRT));
        }
    }



    public static Optional<IntProperty> getIntProperty(BlockState state, String propertyName) {
        for (Property<?> propertyComponent : state.getProperties()) {
            if (propertyComponent.getName().equals(propertyName) && propertyComponent instanceof IntProperty) {
                return Optional.of((IntProperty) propertyComponent);
            }
        }
        return Optional.empty();
    }

    public static boolean enigmaCheckState(BlockState state) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "enigma_energy");

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

    public static void enigmaMendState(BlockState state, World world, BlockPos pos) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "enigma_energy");

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

    public static void enigmaRefillEnergy(BlockState state, World world, BlockPos pos) {
        Optional<IntProperty> oldStateProperty = getIntProperty(state, "enigma_energy");

        if (oldStateProperty.isPresent()) {
            IntProperty specimenTemplate = oldStateProperty.get();
            List<Integer> valueCycle = new ArrayList<>(specimenTemplate.getValues());

            int maxValue = valueCycle.size() - 1;

            BlockState nextEnergy = state.with(specimenTemplate, valueCycle.get(maxValue));
            world.setBlockState(pos, nextEnergy);

        }
    }


}
