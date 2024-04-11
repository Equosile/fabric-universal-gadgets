package net.equosile.ofaafo.item;



import net.equosile.ofaafo.util.OFAAFO_Tags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OFAAFO_Caelustra extends MiningToolItem {

    //private int energy_Caelustra = 0;

    public OFAAFO_Caelustra(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, material, OFAAFO_Tags.Blocks.CAELUSTRA_MINEABLE, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_2"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_3"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_4"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_5"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_6"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_7"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_8"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.shift.line_9"));
        } else {
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.line_1"));
            tooltip.add(Text.translatable("tooltip.ofaafo.caelustra.tooltip.line_2"));
        }
    }



    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if(!context.getWorld().isClient()) {

            PlayerEntity player_user = context.getPlayer();
            BlockPos checkCaelustra = context.getBlockPos();
            Hand hand_OfUser = context.getHand();

            if (hand_OfUser == Hand.OFF_HAND) {

                if (player_user.isSneaking()) {
                    boolean checkSignificance = false;

                    int yInfo = checkCaelustra.getY();

                    if (yInfo < 252 && yInfo > -64) {
                        int yRelative = yInfo + 64;
                        for (int i = -4; i < yRelative; i = i + 1) {
                            BlockPos examPivot = checkCaelustra.down(i);
                            BlockPos examNorth = examPivot.north();
                            BlockPos examSouth = examPivot.south();
                            BlockPos examWest = examPivot.west();
                            BlockPos examEast = examPivot.east();

                            BlockState blockInformation = context.getWorld().getBlockState(examPivot);
                            BlockState northPivot = context.getWorld().getBlockState(examNorth);
                            BlockState southPivot = context.getWorld().getBlockState(examSouth);
                            BlockState westPivot = context.getWorld().getBlockState(examWest);
                            BlockState eastPivot = context.getWorld().getBlockState(examEast);

                            if (isIntriguing(blockInformation)) {
                                assessmentCaelustra(examPivot, player_user, blockInformation.getBlock());
                                checkSignificance = true;
                                break;
                            }
                            if (isIntriguing(northPivot)) {
                                assessmentCaelustra(examNorth, player_user, northPivot.getBlock());
                                checkSignificance = true;
                                break;
                            }
                            if (isIntriguing(southPivot)) {
                                assessmentCaelustra(examSouth, player_user, southPivot.getBlock());
                                checkSignificance = true;
                                break;
                            }
                            if (isIntriguing(westPivot)) {
                                assessmentCaelustra(examWest, player_user, westPivot.getBlock());
                                checkSignificance = true;
                                break;
                            }
                            if (isIntriguing(eastPivot)) {
                                assessmentCaelustra(examEast, player_user, eastPivot.getBlock());
                                checkSignificance = true;
                                break;
                            }

                        }

                        if (!checkSignificance) {
                            assessmentFailure(player_user);
                        }

                    }
                }

            } else {
                //MAIN_HAND_USAGE
                //If the hand of the user is not OFF_HAND, then the tool functions as Axe, Hoe, etc.

                if (player_user.isSneaking()) {
                    //MAIN_HAND + CROUCHING
                    //SPECIAL FUNCTION OF PAXEL THAT CAN BE A HOE TO CREATE FARMLAND
                    paxel_onCrouch(context);
                } else {
                    //ORDINARY FUNCTION OF PAXEL THAT CAN BE EITHER AXE OR SHOVEL
                    paxel_onUse(context);
                }

            }
        }

        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));

        return ActionResult.SUCCESS;
    }



    private void assessmentFailure(PlayerEntity player) {
        player.sendMessage(Text.translatable("item.ofaafo.caelustra.eval_fail"));
    }
    private void assessmentCaelustra(BlockPos area, PlayerEntity player, Block block) {
        player.sendMessage(Text.literal("§eCaelustra§r: " + block.getName().getString() +
                        "§r @ [ " + area.getX() + ", " + area.getY() + ", " + area.getZ() + " ]"
                ));
    }

    public boolean isIntriguing(BlockState blockState) {

        /*
        return blockState.getBlock() == Blocks.GOLD_ORE || blockState.getBlock() == Blocks.DEEPSLATE_GOLD_ORE ||
                blockState.getBlock() == Blocks.NETHER_GOLD_ORE || blockState.getBlock() == Blocks.RAW_GOLD_BLOCK ||
                blockState.getBlock() == Blocks.GOLD_BLOCK || blockState.getBlock() == Blocks.DIAMOND_ORE ||
                blockState.getBlock() == Blocks.DEEPSLATE_DIAMOND_ORE || blockState.getBlock() == Blocks.DIAMOND_BLOCK ||
                blockState.getBlock() == Blocks.ANCIENT_DEBRIS || blockState.getBlock() == Blocks.NETHER_QUARTZ_ORE ||
                blockState.getBlock() == Blocks.EMERALD_BLOCK || blockState.getBlock() == Blocks.EMERALD_ORE ||
                blockState.getBlock() == Blocks.DEEPSLATE_EMERALD_ORE || blockState.getBlock() == Blocks.SUSPICIOUS_GRAVEL ||
                blockState.getBlock() == Blocks.SUSPICIOUS_SAND || blockState.getBlock() == Blocks.CHEST ||
                blockState.getBlock() == Blocks.LAPIS_ORE || blockState.getBlock() == Blocks.DEEPSLATE_LAPIS_ORE ||
                blockState.getBlock() == Blocks.LAPIS_BLOCK || blockState.getBlock() == Blocks.REDSTONE_ORE ||
                blockState.getBlock() == Blocks.DEEPSLATE_REDSTONE_ORE || blockState.getBlock() == Blocks.REDSTONE_BLOCK ||
                blockState.getBlock() == Blocks.OBSIDIAN || blockState.getBlock() == Blocks.CRYING_OBSIDIAN ||
                blockState.getBlock() == Blocks.SCULK_CATALYST || blockState.getBlock() == Blocks.SCULK ||
                blockState.getBlock() == Blocks.SCULK_VEIN || blockState.getBlock() == Blocks.SCULK_SENSOR ||
                blockState.getBlock() == Blocks.SCULK_SHRIEKER || blockState.getBlock() == Blocks.AMETHYST_CLUSTER ||
                blockState.getBlock() == Blocks.AMETHYST_BLOCK || blockState.getBlock() == Blocks.LARGE_AMETHYST_BUD ||
                blockState.getBlock() == Blocks.MEDIUM_AMETHYST_BUD || blockState.getBlock() == Blocks.SMALL_AMETHYST_BUD ||
                blockState.getBlock() == Blocks.DRIPSTONE_BLOCK || blockState.getBlock() == Blocks.POINTED_DRIPSTONE ||
                blockState.getBlock() == Blocks.PRISMARINE || blockState.getBlock() == Blocks.PRISMARINE_BRICKS ||
                blockState.getBlock() == Blocks.PRISMARINE_BRICK_SLAB || blockState.getBlock() == Blocks.PRISMARINE_SLAB ||
                blockState.getBlock() == Blocks.PRISMARINE_WALL || blockState.getBlock() == Blocks.PRISMARINE_STAIRS ||
                blockState.getBlock() == Blocks.SPONGE || blockState.getBlock() == Blocks.WET_SPONGE ||
                blockState.getBlock() == Blocks.END_PORTAL || blockState.getBlock() == OFAAFO_Blocks.SUPERNOVA;

         */

        return blockState.isIn(OFAAFO_Tags.Blocks.CAELUSTRA_DETECTOR);

    }


    public static void paxel_onUse(ItemUsageContext context) {

        World eventLevel = context.getWorld();
        BlockPos eventBlockPos = context.getBlockPos();
        PlayerEntity eventPlayer = context.getPlayer();

        BlockState eventBlockState = eventLevel.getBlockState(eventBlockPos);
        Block eventBlock = eventBlockState.getBlock();

        //FUNCTION: AXE_LOG_MODE
        if (eventBlockState.isIn(OFAAFO_Tags.Blocks.PAXEL_MODE_LOG_AXE)) {
            //DEBUGGING...
            //eventPlayer.sendMessage(Text.literal("paxel_mode_axe.activation"));
            if (eventBlockState.isIn(BlockTags.OAK_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_OAK_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.ACACIA_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_ACACIA_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.BIRCH_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_BIRCH_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.CHERRY_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_CHERRY_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.DARK_OAK_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.JUNGLE_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.MANGROVE_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_MANGROVE_LOG.getDefaultState());
            }
            if (eventBlockState.isIn(BlockTags.SPRUCE_LOGS)) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState());
            }
        }

        //FUNCTION: AXE_WOOD_MODE
        if (eventBlockState.isIn(OFAAFO_Tags.Blocks.PAXEL_MODE_WOOD_AXE)) {
            if (eventBlock == Blocks.OAK_WOOD) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_OAK_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.ACACIA_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_ACACIA_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.BIRCH_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_BIRCH_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.CHERRY_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_CHERRY_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.DARK_OAK_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_DARK_OAK_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.JUNGLE_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_JUNGLE_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.MANGROVE_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_MANGROVE_WOOD.getDefaultState());
            }
            if (eventBlock == Blocks.SPRUCE_LOG) {
                eventLevel.setBlockState(eventBlockPos, Blocks.STRIPPED_SPRUCE_WOOD.getDefaultState());
            }
        }

        //FUNCTION: HOE_DIRT_MODE
        if (eventBlockState.isIn(OFAAFO_Tags.Blocks.PAXEL_MODE_DIRT_SHOVEL)) {
            eventLevel.setBlockState(eventBlockPos, Blocks.DIRT_PATH.getDefaultState());
        }

    }

    public static void paxel_onCrouch(ItemUsageContext context){

        World eventLevel = context.getWorld();
        BlockPos eventBlockPos = context.getBlockPos();
        PlayerEntity eventPlayer = context.getPlayer();

        BlockState eventBlockState = eventLevel.getBlockState(eventBlockPos);
        Block eventBlock = eventBlockState.getBlock();

        //FUNCTION: HOE_DIRT_MODE
        if (eventBlockState.isIn(OFAAFO_Tags.Blocks.PAXEL_MODE_DIRT_HOE)) {
            eventLevel.setBlockState(eventBlockPos, Blocks.FARMLAND.getDefaultState());
        }
    }


}
