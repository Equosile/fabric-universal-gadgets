package net.equosile.ofaafo.event;



import net.equosile.ofaafo.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class OFAAFO_TickEvent {

    //Global Unsigned Integer to Count the World Game Ticks
    private static int worldTick = 0;
    private static int tickCount = 0;
    private static int worldSecond = 0;
    private static int secondCount = 0;
    private static int minuteCount = 0;
    private static int hourCount = 0;
    private static int dayCount = 0;
    private static int monthCount = 0;

    //Global Time Lock
    private static int tempoLockA = 0;
    private static int tempoLockB = 0;

    //CUSTOME TICK HANDLER IS A GRAND EVENT SCHEDULER.
    //
    //    As for void return tasks, use Runnalbe.
    //
    //A: Singular Execution in Every 4 Ticks
    public static int counter_A = 0;
    private static final int MAX_A = 4;
    //B: Singular Execution in Every 1 Seconds
    public static int counter_B = 0;
    private static final int MAX_B = 20;
    //C: Batch Execution in Every 30 Seconds
    public static int counter_C = 0;
    private static final int MAX_C = 600;
    //D: Batch Execution in Every 1 Minute
    public static int counter_D = 0;
    private static final int MAX_D = 1200;
    //A_HALF: Legacy Redstone Interval
    public static int counter_A_HALF = 0;
    private static final int MAX_A_HALF = 2;
    //
    public static Queue<Runnable> OFAAFO_event_voidQueueA = new ArrayDeque<>();
    public static Queue<Runnable> OFAAFO_event_voidQueueB = new ArrayDeque<>();
    public static Queue<Runnable> OFAAFO_event_voidQueueC = new ArrayDeque<>();
    public static Queue<Runnable> OFAAFO_event_voidQueueD = new ArrayDeque<>();

    public static final int MAX_SIZE_QUEUE_C = 12;
    public static final int MAX_SIZE_QUEUE_D = 6;

    //CUSTOM TICK HANDLER IS A GRAND ITEM TRACKER SITUATED BY ItemTossEvent.
    //
    public static ArrayList<Object> overWatch_ItemTossEvent = new ArrayList<>();

    //Semi-permanent Scheduler:
    //   Once registered, it goes almost forever until it is destroyed.
    public static ArrayList<Runnable> QUEUE_PERSISTENT = new ArrayList<>();
    public static ArrayList<Object> QUEUE_PERSISTENT_INFO = new ArrayList<>();



    //Custom Debugger for Loading this Mod Event
    private static boolean tickHandlerReady = false;



    public static int getTickCount() {
        return worldTick;
    }
    public static int getSecondCount() {
        return worldSecond;
    }
    public static int getDayCount() {
        return dayCount;
    }

    //TESTING_MSG_DEBUGGER
    public static void tickSystemMessage(MinecraftServer server, String str_devMessage) {
        //List<ServerPlayerEntity> list_Players = server.getPlayerManager().getPlayerList();
        //for (ServerPlayerEntity player : list_Players) {
        //    // The second parameter is a boolean
        //    //     for whether the message should go to the action bar (true) or chat (false).
        //    player.sendMessage(Text.literal(str_devMessage), false);
        //}
        server.getPlayerManager().broadcast(Text.literal(str_devMessage), false);
    }

    //CUSTOM DATE SYSTEM
    public static String ordinal(int i) {
        String[] suffixes = new String[] {
                //0,    1,    2,    3,    4,    5,    6,    7,    8,    9
                "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"
        };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }
    public static void tickTimeReport(MinecraftServer server) {
        String str_month = ordinal(monthCount);
        String str_day = ordinal(dayCount);

        String str_debugger = "§cCurrent Time§r: " +
                str_month + " month " + str_day  + " day " +
                hourCount + " hour " + minuteCount + " minute " +
                secondCount + " second (" + worldTick + " world-tick)";
        tickSystemMessage(server, str_debugger);
    }

    //CUSTOM GAME RULE:
    //    SCOREBOARD:
    //        * TIMER SETTINGS
    //        * IN-GAME TERM COUNTER
    public static void gameRuleMaintenance(MinecraftServer server) {
        //Vanilla Scoreboard for In-game Use
        Scoreboard OFAAFO_SCOREBOARD = server.getScoreboard();

        //REDUNDANCY OF OBJECTIVES CAUSES FATAL ERROR.
        //    ALWAYS DOUBLE CHECK BEFORE REGISTERING ONE.
        List<String> check_list = List.of(
                "TICK", "SEC"
        );
        for (int i = 0; i < check_list.size(); i = i + 1) {
            String obj_SequentialNAME = check_list.get(i);
            if (OFAAFO_SCOREBOARD.getNullableObjective(obj_SequentialNAME) == null) {
                ScoreboardObjective OFAAFO_SB_OBJECTIVE_TEMP = OFAAFO_SCOREBOARD.addObjective(obj_SequentialNAME,
                        ScoreboardCriterion.DUMMY, Text.literal(obj_SequentialNAME), ScoreboardCriterion.RenderType.INTEGER);

                //OFAAFO_SCOREBOARD.setObjectiveSlot(ScoreboardDisplaySlot.LIST, OFAAFO_SB_OBJECTIVE_TEMP);
                //Back-port Shifting from 1.20.2 to 1.20.1
                //      e.g. ScoreboardDisplaySlot.LIST ---> 0
                //              0 == ENUM.LIST (or Player List)
                //              1 == ENUM.SIDEBAR
                //              2 == ENUM.BELOW_NAME
                OFAAFO_SCOREBOARD.setObjectiveSlot(0, OFAAFO_SB_OBJECTIVE_TEMP);
            }
        }

    }
    public static void scoreboardSetting(MinecraftServer server, String name_object, int setting) {

        Scoreboard OFAAFO_SCOREBOARD = server.getScoreboard();

        String str_OFAAFO_OBJECTIVE = name_object;
        ScoreboardObjective OFAAFO_OBJECTIVE = OFAAFO_SCOREBOARD.getNullableObjective(str_OFAAFO_OBJECTIVE);

        if (OFAAFO_OBJECTIVE != null) {
            OFAAFO_SCOREBOARD.getPlayerScore(name_object, OFAAFO_OBJECTIVE).setScore(setting);
        }
    }
    public static HashMap<String, Integer> termRegister = new HashMap<>();
    public static void scoreboardStopWatchSetTimer(MinecraftServer server, String unique_tempo_key) {
        Scoreboard OFAAFO_SCOREBOARD = server.getScoreboard();
        String name_object = "TICK";

        String str_OFAAFO_OBJECTIVE = name_object;
        ScoreboardObjective OFAAFO_OBJECTIVE = OFAAFO_SCOREBOARD.getNullableObjective(str_OFAAFO_OBJECTIVE);

        if (OFAAFO_OBJECTIVE != null) {
            ScoreboardPlayerScore OFAAFO_SCORE = OFAAFO_SCOREBOARD.getPlayerScore(name_object, OFAAFO_OBJECTIVE);

            termRegister.put(unique_tempo_key, OFAAFO_SCORE.getScore());
        }
    }
    public static boolean scoreboardPeriodCounter(MinecraftServer server, String unique_tempo_key, int setting) {
        Scoreboard OFAAFO_SCOREBOARD = server.getScoreboard();
        String name_object = "TICK";

        String str_OFAAFO_OBJECTIVE = name_object;
        ScoreboardObjective OFAAFO_OBJECTIVE = OFAAFO_SCOREBOARD.getNullableObjective(str_OFAAFO_OBJECTIVE);

        if (OFAAFO_OBJECTIVE != null) {
            ScoreboardPlayerScore OFAAFO_SCORE = OFAAFO_SCOREBOARD.getPlayerScore(name_object, OFAAFO_OBJECTIVE);
            int termStop = OFAAFO_SCORE.getScore();

            if (termRegister.get(unique_tempo_key) != null) {
                int termDifference = termStop - termRegister.get(unique_tempo_key);
                if (termDifference >= setting) {

                    termRegister.remove(unique_tempo_key);
                    return true;
                }
            } else {
                scoreboardStopWatchSetTimer(server, unique_tempo_key);
            }
        }

        return false;
    }
    public static void scoreboardStopWatchReset() {
        termRegister.clear();
    }

    //Loading Tasks into the Queue.
    public static int ofaafo_voidScheduleTaskA(Runnable task) {
        OFAAFO_event_voidQueueA.add(task);

        return OFAAFO_event_voidQueueA.size();
    }
    public static int ofaafo_voidScheduleTaskB(Runnable task) {
        OFAAFO_event_voidQueueB.add(task);

        return OFAAFO_event_voidQueueB.size();
    }
    public static int ofaafo_voidScheduleTaskC(Runnable task) {
        OFAAFO_event_voidQueueC.add(task);

        return OFAAFO_event_voidQueueC.size();
    }
    public static int ofaafo_voidScheduleTaskD(Runnable task) {
        OFAAFO_event_voidQueueD.add(task);

        return OFAAFO_event_voidQueueD.size();
    }

    public static int ofaafo_voidTaskQueueSizeA() {

        return OFAAFO_event_voidQueueA.size();
    }
    public static int ofaafo_voidTaskQueueSizeB() {

        return OFAAFO_event_voidQueueB.size();
    }
    public static int ofaafo_voidTaskQueueSizeC() {

        return OFAAFO_event_voidQueueC.size();
    }
    public static int ofaafo_voidTaskQueueSizeD() {

        return OFAAFO_event_voidQueueD.size();
    }

    public static boolean ofaafo_SecondPeriodicVoidExecutorA() {
        AtomicBoolean successfulVoidExecutorA = new AtomicBoolean(false);

        try {
            //Do NEVER use a custom multithreading here!
            //Minecraft has its own thread lock that breaks the entire game!
            //OFAAFO_voidExecutorA.scheduleAtFixedRate(
            //    () -> {
            if (!OFAAFO_event_voidQueueA.isEmpty()) {
                Runnable nextTask = OFAAFO_event_voidQueueA.poll();
                if (nextTask != null) {
                    try {
                        nextTask.run();
                        successfulVoidExecutorA.set(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //    }, 0, 1, TimeUnit.SECONDS
            //);  //Executes Every Second
        } catch (Exception e) {
            e.printStackTrace();
            //mod_ignitorA();
        }

        return successfulVoidExecutorA.get();
    }
    public static boolean ofaafo_SecondPeriodicVoidExecutorB() {
        AtomicBoolean successfulVoidExecutorB = new AtomicBoolean(false);

        try {

            if (!OFAAFO_event_voidQueueB.isEmpty()) {
                Runnable nextTask = OFAAFO_event_voidQueueB.poll();
                if (nextTask != null) {
                    try {
                        nextTask.run();
                        successfulVoidExecutorB.set(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //mod_ignitorB();
        }

        return successfulVoidExecutorB.get();
    }
    public static boolean ofaafo_HalfMinutePeriodicVoidExecutorC() {
        AtomicBoolean successfulVoidExecutorC = new AtomicBoolean(false);

        try {

            if (!OFAAFO_event_voidQueueC.isEmpty()) {
                Runnable nextTask = OFAAFO_event_voidQueueC.poll();
                if (nextTask != null) {
                    try {
                        nextTask.run();
                        successfulVoidExecutorC.set(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //mod_ignitorC();
        }

        return successfulVoidExecutorC.get();
    }
    public static boolean ofaafo_MinutePeriodicVoidExecutorD() {
        AtomicBoolean successfulVoidExecutorD = new AtomicBoolean(false);

        try {

            if (!OFAAFO_event_voidQueueD.isEmpty()) {
                Runnable nextTask = OFAAFO_event_voidQueueD.poll();
                if (nextTask != null) {
                    try {
                        nextTask.run();
                        successfulVoidExecutorD.set(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //mod_ignitorD();
        }

        return successfulVoidExecutorD.get();
    }

    //EVENT RELATION TO CORE A
    public static void addWatcherEvent(Object event) {
        overWatch_ItemTossEvent.add(event);
    }
    public static void settleWatcher() {
        if (!overWatch_ItemTossEvent.isEmpty()) {
            Object specimen_Event = overWatch_ItemTossEvent.remove(0);

            if (specimen_Event != null) {

                List<?> eventObjectList = (List<?>) specimen_Event;

                MinecraftServer server = (MinecraftServer) eventObjectList.get(0);
                World eventLevel = (World) eventObjectList.get(1);
                PlayerEntity userOfEvent = (PlayerEntity) eventObjectList.get(2);
                ItemEntity itemOfEvent = (ItemEntity) eventObjectList.get(3);
                double specimen_Y = (double) eventObjectList.get(4);
                int unique_Event_KEY = (int) eventObjectList.get(5);

                double yTest = itemOfEvent.getY();

                double test_Difference = Math.abs(specimen_Y - yTest);
                //Since Fabric is so crystal-clearly well optimised,
                //    10,000 times greater accuracy is needed than a case in Forge.
                //i.e. Forge is so infamously laggy so that 0.0000001 is enough to distinguish,
                //    whereas in Fabric, the dice as soon as tossed can be immediately evaluated,
                //
                double target_Accuracy = 0.01;

                //FOR DEBUGGING...
                //String debugger_Y_gap = "Y_GAP: " + test_Difference + " [ " + specimen_Y + " - " + yTest + " ]";
                //tickSystemMessage(debugger_Y_gap);

                String unique_TempoKey = userOfEvent.getName().getString() + "_dice" + unique_Event_KEY;
                //Mandatory Delay Before Action == targetPeriod
                int targetPeriod = 1;
                if (test_Difference < target_Accuracy &&
                        scoreboardPeriodCounter(server, unique_TempoKey, targetPeriod))
                {
                    //SETTLEMENT SUCCESS
                    OFAAFO_DiceEvent.rollingDice(eventLevel, userOfEvent, itemOfEvent);
                } else {
                    //SETTLEMENT FAILURE
                    //Debugger
                    //String str_debugger = "settle.fail.event_ID=" + unique_Event_KEY +
                    //        "-(" + elapsedPeriodBeyondTarget + ")=" + unique_TempoKey + "+" + test_Difference;
                    //tickSystemMessage(server, str_debugger);
                    //RECOVER THE SPECIMENS
                    addWatcherEvent(Arrays.asList(server, eventLevel, userOfEvent,
                            itemOfEvent, yTest, unique_Event_KEY));
                    //RETRIAL FOR LATER SETTLEMENT
                    ofaafo_voidScheduleTaskA(
                            () -> settleWatcher()
                    );
                }
            }

        }
    }



    public static void addPersistentEvent(Runnable event) { QUEUE_PERSISTENT.add(event); }
    public static int sizeOfPersistentEvent() {
        return QUEUE_PERSISTENT.size();
    }
    public static void addPersistentEventInfo(Object info_event) {
        QUEUE_PERSISTENT_INFO.add(info_event);
    }
    public static int sizeOfPersistentEventInfo() {
        return QUEUE_PERSISTENT_INFO.size();
    }
    // !!! RECURSIVE ALGORITHM == CANNOT BE BATCHED !!!
    public static void rotaPersistentEnvent(MinecraftServer server) {
        //Debugging...
        //tickSystemMessage(server, "problem_size_A: " + sizeOfPersistentEvent());
        //tickSystemMessage(server, "problem_size_B: " + sizeOfPersistentEventInfo());

        if (!QUEUE_PERSISTENT.isEmpty() && !QUEUE_PERSISTENT_INFO.isEmpty()) {
            if (sizeOfPersistentEvent() == sizeOfPersistentEventInfo()) {

                int num_EventLoopSize = sizeOfPersistentEvent();
                ArrayList<Runnable> temporary_QUEUE_PERSISTENT = new ArrayList<>();
                ArrayList<Object> temporary_QUEUE_PERSISTENT_INFO = new ArrayList<>();

                // INSTEAD, IT CAN BE BATCHED INTERNALLY.
                for (int i = 0; i < num_EventLoopSize; i = i + 1) {

                    Runnable specimen_Event = QUEUE_PERSISTENT.remove(0);
                    Object info_specimen_event = QUEUE_PERSISTENT_INFO.remove(0);
                    temporary_QUEUE_PERSISTENT.add(specimen_Event);
                    temporary_QUEUE_PERSISTENT_INFO.add(info_specimen_event);

                    if (specimen_Event != null && info_specimen_event != null) {

                        List<?> info_List = (List<?>) info_specimen_event;

                        MinecraftServer server_Event = (MinecraftServer) info_List.get(0);
                        String unique_KEY_EVENT = (String) info_List.get(1);
                        //int interval_Event = (int) info_List.get(2);
                        //BlockState blockState_Event = (BlockState) info_List.get(3);
                        World world_Event = (World) info_List.get(4);
                        BlockPos blockPos_Event = (BlockPos) info_List.get(5);

                        if (/*scoreboardPeriodCounter(server_Event, unique_KEY_EVENT, interval_Event)*/true) {
                            try {
                                specimen_Event.run();

                                //DON'T REPEAT THE SUCCESSFUL EVENTS AGAIN...
                                temporary_QUEUE_PERSISTENT.remove(0);
                                temporary_QUEUE_PERSISTENT_INFO.remove(0);
                                //... BUT THE NEXT EVENTS!
                                String[] parseEvent = unique_KEY_EVENT.split("~");
                                // parseEvent == "dipip_redstone~" + xEvent + "~" + yEvent + "~" + zEvent
                                //                 parseEvent[0]       [1]            [2]            [3]
                                String keywordEvent = parseEvent[0];
                                BlockState blockState_Event = world_Event.getBlockState(blockPos_Event);
                                if (keywordEvent.contains("monopip")) {
                                    OFAAFO_Dice_Monopip.monopip_Emit_Redstone(
                                            blockState_Event, world_Event, blockPos_Event
                                    );
                                } else if (keywordEvent.contains("dipip")) {

                                    //Debugging..
                                    //tickSystemMessage(server_Event, "new.task.dipip");

                                    OFAAFO_Dice_Dipip.dipip_Emit_Redstone(
                                            blockState_Event, world_Event, blockPos_Event
                                    );
                                } else if (keywordEvent.contains("tripip")) {
                                    OFAAFO_Dice_Tripip.tripip_Emit_Redstone(
                                            blockState_Event, world_Event, blockPos_Event
                                    );
                                } else if (keywordEvent.contains("tetrapip")) {
                                    OFAAFO_Dice_Tetrapip.tetrapip_Emit_Redstone(
                                            blockState_Event, world_Event, blockPos_Event
                                    );
                                } else if (keywordEvent.contains("pentapip")) {
                                    OFAAFO_Dice_Pentapip.pentapip_Emit_Redstone(
                                            blockState_Event, world_Event, blockPos_Event
                                    );
                                } else if (keywordEvent.contains("hexapip")) {
                                    OFAAFO_Dice_Hexapip.hexapip_Emit_Redstone(
                                            blockState_Event, world_Event, blockPos_Event
                                    );
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Go To Persistent Queue Sequence
                        }
                    }
                }
                //PERSISTENT QUEUE SEQUENCE
                //
                // TIMING IS NOT GOOD!
                //     RECOVERING THE STATES AND QUEUES...
                //     HOPEFULLY THERE WOULD BE GOOD LUCK NEXT TIME!
                //
                QUEUE_PERSISTENT.addAll(temporary_QUEUE_PERSISTENT);
                QUEUE_PERSISTENT_INFO.addAll(temporary_QUEUE_PERSISTENT_INFO);

            } else {
                //ERROR!
                //    LOST EVENT OR ITS INFORMATION!
                tickSystemMessage(server, "queue_size_ERROR_A: " + sizeOfPersistentEvent());
                tickSystemMessage(server, "queue_size_ERROR_B: " + sizeOfPersistentEventInfo());
                QUEUE_PERSISTENT.clear();
                QUEUE_PERSISTENT_INFO.clear();
            }
        }
    }



    private static void mod_loader(MinecraftServer server) {
        if (!tickHandlerReady) {
            tickSystemMessage(server, "§5OFAAFO§r: Loading OFAAFO...");
            //boolean tickCheckVoidSecondA = ofaafo_SecondPeriodicVoidExecutorA();
            //if (tickCheckVoidSecondA) {
            //    //tickSystemMessage("§5Void_A§r: Success!");
            //} else {
            //    //tickSystemMessage("§5Void_A§r: ...");
            //}
            mod_ignitorA();
            mod_ignitorB();
            mod_ignitorC();
            mod_ignitorD();

            tickHandlerReady = true;
            tickSystemMessage(server, "§5OFAAFO§r: OFAAFO is ready!");
        }
    }



    private static void mod_ignitorA() {
        boolean tickCheckVoidSecondA = ofaafo_SecondPeriodicVoidExecutorA();
        if (tickCheckVoidSecondA) {
            //tickSystemMessage("§5Void_A§r: Success!");
        } else {
            //tickSystemMessage("§5Void_A§r: ...");
        }
    }
    private static void mod_ignitorB() {
        boolean tickCheckVoidSecondB = ofaafo_SecondPeriodicVoidExecutorB();
        if (tickCheckVoidSecondB) {
            //tickSystemMessage("§5Void_B§r: Success!");
        } else {
            //tickSystemMessage("§5Void_B§r: ...");
        }
    }
    private static void mod_ignitorC() {
        boolean tickCheckVoidSecondC = ofaafo_HalfMinutePeriodicVoidExecutorC();
        if (tickCheckVoidSecondC) {
            //tickSystemMessage("§5Void_B§r: Success!");
        } else {
            //tickSystemMessage("§5Void_B§r: ...");
        }
    }
    private static void mod_ignitorD() {
        boolean tickCheckVoidSecondD = ofaafo_MinutePeriodicVoidExecutorD();
        if (tickCheckVoidSecondD) {
            //tickSystemMessage("§5Void_B§r: Success!");
        } else {
            //tickSystemMessage("§5Void_B§r: ...");
        }
    }



    //MAIN BODY of TICK EVENT HANDLER
    public static void onServerTickEvent(MinecraftServer server) {

        tickCount = tickCount + 1;
        worldTick = worldTick + 1;

        int int_lockA_busy = getTickCount();
        int int_lockB_busy = getTickCount();

        if (tickCount >= 20) {
            tickCount = 0;
            secondCount = secondCount + 1;
            worldSecond = worldSecond + 1;

            //LOGGER.info("WORLD_CLOCK: >> {}", getSecondCount());

            //NEW METHOD FOR PERIODIC EVENT
            //    EXAMPLE: THIS SHOULD YIELD A MESSAGE EVERY 4 SECONDS.
            //if (scoreboardPeriodCounter(server, "test", 80)) {
            //    String str_debugger = "independent_periodic_event.out_of_scheduler: " + getSecondCount();
            //    tickSystemMessage(server, str_debugger);
            //}

        } else {
            // Loading the mod for the first time as server startup...
            if (!tickHandlerReady) {
                if (int_lockA_busy != tempoLockA && int_lockB_busy != tempoLockB) {

                    mod_loader(server);
                    tempoLockA = int_lockA_busy;
                    tempoLockB = int_lockB_busy;

                }
            }
        }
        if (secondCount >= 60) {
            secondCount = 0;
            minuteCount = minuteCount + 1;

            //String str_debugger = "Current Minute: " + minuteCount;
            //tickSystemMessage(str_debugger);

        } else {
            // IF secondCount < 60 THEN:
        }
        if (minuteCount >= 60) {
            minuteCount = 0;
            hourCount = hourCount + 1;
        }
        if (hourCount >= 24) {
            hourCount = 0;
            dayCount = dayCount + 1;
            tickTimeReport(server);
        }
        if (dayCount >= 30) {
            dayCount = 0;
            monthCount = monthCount + 1;
        }
        if (worldTick >= 2147483646) {
            tickTimeReport(server);

            worldTick = 0;
            worldSecond = 0;
            monthCount = 0;
        }

        //DUAL LOCK
        tempoLockA = int_lockA_busy;
        tempoLockB = int_lockB_busy;

        counter_A_HALF = counter_A_HALF + 1;
        counter_A = counter_A + 1;
        counter_B = counter_B + 1;
        counter_C = counter_C + 1;
        counter_D = counter_D + 1;

        gameRuleMaintenance(server);
        scoreboardSetting(server, "TICK", getTickCount());

        //ALL CORES WORKLOADS
        //    CORE A_HALF: 2 TICK
        if (counter_A_HALF >= MAX_A_HALF) {

            //

            counter_A_HALF = 0;
        }
        //    CORE A: 4 TICK
        //        * CORE A WORKLOAD
        //        * Rapid Execution with High Priority
        if (counter_A >= MAX_A) {

            mod_ignitorA();

            counter_A = 0;
        }
        //    CORE B: 1 SEC == 20 TICK
        //        * Lower Priority than Core A
        if (counter_B >= MAX_B) {

            mod_ignitorB();
            scoreboardSetting(server, "SEC", getSecondCount());
            rotaPersistentEnvent(server);

            counter_B = 0;
        }
        //    CORE C: 30 SEC == 600 TICK
        //        * Lower Priority than Core B
        //        * BATCH EXECUTION
        if (counter_C >= MAX_C) {

            int temp_QueueC_Size = ofaafo_voidTaskQueueSizeC();
            if (temp_QueueC_Size > 0 && MAX_SIZE_QUEUE_C >= temp_QueueC_Size) {
                for (int i = 0; i < temp_QueueC_Size; i = i + 1) {
                    mod_ignitorC();
                }
            } else if (temp_QueueC_Size > MAX_SIZE_QUEUE_C) {
                for (int i = 0; i < MAX_SIZE_QUEUE_C; i = i + 1) {
                    mod_ignitorC();
                }
            } else {
                //IF temp_QueueC_Size <= 0
                //DO NOTHING.
            }

            counter_C = 0;
        }
        //    CORE D: 60 SEC == 1200 TICK
        //        * Slow Execution with Low Priority
        //        * BATCH EXECUTION
        if (counter_D >= MAX_D) {

            scoreboardStopWatchReset();

            int temp_QueueD_Size = ofaafo_voidTaskQueueSizeD();
            if (temp_QueueD_Size > 0 && MAX_SIZE_QUEUE_D >= temp_QueueD_Size) {
                for (int i = 0; i < temp_QueueD_Size; i = i + 1) {
                    mod_ignitorD();
                }
            } else if (temp_QueueD_Size > MAX_SIZE_QUEUE_D) {
                for (int i = 0; i < MAX_SIZE_QUEUE_D; i = i + 1) {
                    mod_ignitorD();
                }
            } else {
                //IF temp_QueueD_Size <= 0
                //DO NOTHING.
            }

            counter_D = 0;
        }



    }



}
