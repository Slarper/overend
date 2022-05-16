package slarper.overend.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slarper.overend.TeleportUtils;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void teleport(CallbackInfo ci){
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (TeleportUtils.canTeleportToEnd(player)){
            TeleportUtils.teleportToEnd(player);
        } else if(TeleportUtils.canTeleportToOverworld(player)){
            TeleportUtils.teleportToOverworld(player);
        }
    }
}
