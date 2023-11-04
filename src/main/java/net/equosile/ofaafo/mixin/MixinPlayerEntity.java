package net.equosile.ofaafo.mixin;



import net.equosile.ofaafo.event.OFAAFO_ItemTossCallback;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {

    @Inject(
            method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onDropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        MinecraftServer server = player.getServer();
        World world = player.getWorld();
        Hand hand = player.getActiveHand();
        //Dropped Item on Event but specifically at the Return (ItemEntity)
        ItemEntity item = cir.getReturnValue();

        ActionResult result = OFAAFO_ItemTossCallback.ItemTossCallback.EVENT.invoker().onItemToss(server, world, player, hand, item);

        if (result == ActionResult.FAIL) {
            cir.setReturnValue(null);
            cir.cancel();
        }
    }


}
