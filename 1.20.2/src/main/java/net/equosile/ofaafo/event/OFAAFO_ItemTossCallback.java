package net.equosile.ofaafo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

//Referencing MixinPlayerEntity...
public class OFAAFO_ItemTossCallback {
    //ORIGINAL CUSTOM ITEM TOSS EVENT HANDLER
    public interface ItemTossCallback {
        Event<ItemTossCallback> EVENT = EventFactory.createArrayBacked(ItemTossCallback.class,
                (listeners) -> (server, world, player, hand, item) -> {
                    for (ItemTossCallback listener : listeners) {
                        ActionResult result = listener.onItemToss(server, world, player, hand, item);
                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                }
        );

        ActionResult onItemToss(MinecraftServer server, World world, PlayerEntity player, Hand hand, ItemEntity item);
    }
}
