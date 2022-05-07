package slarper.overend.mixin;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void teleport(CallbackInfo ci){
        if (this.getWorld() instanceof ServerWorld) {
            RegistryKey<World> key = this.getWorld().getRegistryKey();
            if ((key == World.OVERWORLD && this.getY() > 448)||(key == World.END && this.getY() < -56)){
                MinecraftServer server = this.getWorld().getServer();
                if (server != null){
                    ServerWorld dest = (key == World.OVERWORLD) ? server.getWorld(World.END) : server.getWorld(World.OVERWORLD);
                    TeleportTarget target = new TeleportTarget(
                            this.getPos().add(
                                    0,
                                    (key == World.OVERWORLD) ? -448 : 448,
                                    0
                            ),
                            null,
                            this.getYaw(),
                            this.getPitch()
                    );
                    FabricDimensions.teleport(this, dest, target);
                }
            }
        }
    }
}
