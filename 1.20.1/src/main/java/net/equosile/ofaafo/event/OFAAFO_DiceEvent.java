package net.equosile.ofaafo.event;

import net.equosile.ofaafo.block.OFAAFO_Blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.security.SecureRandom;
import java.util.Arrays;


//implements PlayerDropItemCallback
public class OFAAFO_DiceEvent {

    public static void registerDiceEvent() {
        OFAAFO_ItemTossCallback.ItemTossCallback.EVENT.register((server, world, player, hand, item) -> {
            //When a dice is tossed from a player hand...
            //Hooked by Custom Event Handler ---> OFAAFO_ItemTossCallBack

            dice_Event_Main(server, world, player, hand, item);

            return ActionResult.PASS;
        });
    }

    //Main Body of the Registered Dice Event
    public static void dice_Event_Main(MinecraftServer server, World world, PlayerEntity player, Hand hand, ItemEntity item) {

        // !!! CAUTION !!!
        // PLEASE MAKE SURE THAT THE EVENT IS MEANT TO BELONG TO SERVER SIDE ONLY!
        if (!world.isClient()) {
            if (hand == Hand.MAIN_HAND) {

                if (item != null) {
                    ItemStack stackOnEvent = item.getStack();

                    if (isDice(stackOnEvent)) {

                        double yInit = item.getY();
                        int unique_Event_KEY = OFAAFO_TickEvent.getTickCount();

                        //Debugger
                        //player.sendMessage(Text.literal("registerDiceEvent.activation"));

                        OFAAFO_TickEvent.addWatcherEvent(Arrays.asList(server, world, player,
                                item, yInit, unique_Event_KEY));

                        OFAAFO_TickEvent.ofaafo_voidScheduleTaskA(
                                () -> OFAAFO_TickEvent.settleWatcher()
                        );

                    }
                }

            }
        }

    }



    public static boolean isDice(ItemStack stack) {

        if (stack.isOf(OFAAFO_Blocks.DICE_MONOPIP.asItem())) {
            return true;
        } else if (stack.isOf(OFAAFO_Blocks.DICE_DIPIP.asItem())) {
            return true;
        } else if (stack.isOf(OFAAFO_Blocks.DICE_TRIPIP.asItem())) {
            return true;
        } else if (stack.isOf(OFAAFO_Blocks.DICE_TETRAPIP.asItem())) {
            return true;
        } else if (stack.isOf(OFAAFO_Blocks.DICE_PENTAPIP.asItem())) {
            return true;
        } else if (stack.isOf(OFAAFO_Blocks.DICE_HEXAPIP.asItem())) {
            return true;
        } else {
            return false;
        }

    }

    public static void rollingDice(World world, PlayerEntity player, ItemEntity item) {

        String nameOfUser = player.getName().getString();

        ItemStack diceStackA = new ItemStack(OFAAFO_Blocks.DICE_MONOPIP);
        ItemStack diceStackB = new ItemStack(OFAAFO_Blocks.DICE_DIPIP);
        ItemStack diceStackC = new ItemStack(OFAAFO_Blocks.DICE_TRIPIP);
        ItemStack diceStackD = new ItemStack(OFAAFO_Blocks.DICE_TETRAPIP);
        ItemStack diceStackE = new ItemStack(OFAAFO_Blocks.DICE_PENTAPIP);
        ItemStack diceStackF = new ItemStack(OFAAFO_Blocks.DICE_HEXAPIP);


        SecureRandom obfuscatedSeed = new SecureRandom();
        // Generating a random number between 1 and 6, inclusive.
        int i_rollingDice = obfuscatedSeed.nextInt(6) + 1;
        double xOut = item.getX() + 0.5;
        double yOut = item.getY() + 0.5;
        double zOut = item.getZ() + 0.5;

        switch (i_rollingDice) {
            case 1:
                //eventLevel.addFreshEntity(new ItemEntity(eventLevel,
                //        xOut, yOut, zOut,
                //        CustomBlockEventSupernova.getItemStack_DiceA()));
                world.spawnEntity(
                        new ItemEntity(world, xOut, yOut, zOut, diceStackA)
                );
                break;
            case 2:
                world.spawnEntity(
                        new ItemEntity(world, xOut, yOut, zOut, diceStackB)
                );
                break;
            case 3:
                world.spawnEntity(
                        new ItemEntity(world, xOut, yOut, zOut, diceStackC)
                );
                break;
            case 4:
                world.spawnEntity(
                        new ItemEntity(world, xOut, yOut, zOut, diceStackD)
                );
                break;
            case 5:
                world.spawnEntity(
                        new ItemEntity(world, xOut, yOut, zOut, diceStackE)
                );
                break;
            case 6:
                world.spawnEntity(
                        new ItemEntity(world, xOut, yOut, zOut, diceStackF)
                );
                break;
            default:
                player.sendMessage(Text.literal("§eDice§r: Error!"));
        }

        item.remove(Entity.RemovalReason.DISCARDED);
        String msgScoreSetup = "The roll of §a" + nameOfUser +
                "§r is a §b" + i_rollingDice + "§r.";
        player.sendMessage(Text.literal(msgScoreSetup));


    }


}
