package net.equosile.ofaafo.event;



import net.equosile.ofaafo.block.OFAAFO_Supernova;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class OFAAFO_TickEvent {

    //Global Unsigned Integer to Count the World Game Ticks
    private static int worldTick = 0;
    private static int tickCount = 0;
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
    //As for void return tasks, use Runnalbe.
    //A: Singular Execution in Every 2 Seconds
    //B: Singular Execution in Every 3 Seconds
    //C: Batch Execution in Every 30 Seconds
    //D: Batch Execution in Every 1 Minute
    public static Queue<Runnable> OFAAFO_event_voidQueueA = new ArrayDeque<>();
    public static Queue<Runnable> OFAAFO_event_voidQueueB = new ArrayDeque<>();
    public static Queue<Runnable> OFAAFO_event_voidQueueC = new ArrayDeque<>();
    public static Queue<Runnable> OFAAFO_event_voidQueueD = new ArrayDeque<>();

    public static final int MAX_SIZE_QUEUE_C = 12;
    public static final int MAX_SIZE_QUEUE_D = 6;

    //CUSTOM TICK HANDLER IS A GRAND ITEM TRACKER SITUATED BY ItemTossEvent.
    //
    public static ArrayList<Object> overWatch_ItemTossEvent = new ArrayList<>();



    //Custom Debugger for Loading this Mod Event
    private static boolean tickHandlerReady = false;



    public static int getTickCount() {
        return worldTick;
    }
    public static int getSecondCount() {
        return secondCount;
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

                World eventLevel = (World) eventObjectList.get(0);
                PlayerEntity userOfEvent = (PlayerEntity) eventObjectList.get(1);
                ItemEntity itemOfEvent = (ItemEntity) eventObjectList.get(2);
                double specimen_Y = (double) eventObjectList.get(3);

                double yTest = itemOfEvent.getY();

                double test_Difference = Math.abs(specimen_Y - yTest);
                //Since Fabric is so crystal-clearly well optimised,
                //    10,000 times greater accuracy is needed than a case in Forge.
                //i.e. Forge is so infamously laggy so that 0.0000001 is enough to distinguish,
                //    whereas in Fabric, the dice as soon as tossed can be immediately evaluated,
                //
                double target_Accuracy = 0.000000000000001;

                //FOR DEBUGGING...
                //String debugger_Y_gap = "Y_GAP: " + test_Difference + " [ " + specimen_Y + " - " + yTest + " ]";
                //tickSystemMessage(debugger_Y_gap);

                if (test_Difference < target_Accuracy) {
                    //SETTLEMENT SUCCESS
                    OFAAFO_DiceEvent.rollingDice(eventLevel, userOfEvent, itemOfEvent);

                } else {
                    //SETTLEMENT FAILURE
                    //RECOVER THE SPECIMENS
                    addWatcherEvent(Arrays.asList(eventLevel, userOfEvent, itemOfEvent, yTest));
                    //RETRIAL FOR LATER SETTLEMENT
                    ofaafo_voidScheduleTaskA(
                            () -> settleWatcher()
                    );
                }
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

            //LOGGER.info("WORLD_CLOCK: >> {}", getSecondCount());

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

            // CORE D WORKLOAD
            //Slow Execution with Low Priority
            //CORE D is about BATCH EXECUTION.
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

        } else {

            // CORE A WORKLOAD
            if (secondCount % 2 == 0) {
                //Rapid Execution with High Priority
                mod_ignitorA();

            }

            // CORE B WORKLOAD
            if (secondCount % 3 == 0) {
                //Lower Priority than Core A
                mod_ignitorB();

            }

            // CORE C WORKLOAD
            if (secondCount % 30 == 0) {
                //Lower Priority than Core B
                //CORE C is about BATCH EXECUTION.
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
            }

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
            monthCount = 0;
        }

        //DUAL LOCK
        tempoLockA = int_lockA_busy;
        tempoLockB = int_lockB_busy;



    }



}
