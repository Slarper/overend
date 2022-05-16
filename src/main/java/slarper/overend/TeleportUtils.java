package slarper.overend;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class TeleportUtils {
    public static boolean canTeleportToEnd(PlayerEntity player){
        if (player.getWorld() instanceof ServerWorld) {
            RegistryKey<World> key = player.getWorld().getRegistryKey();
            return key == World.OVERWORLD && player.getY() > 448;
        }
        return false;
    }

    public static boolean canTeleportToOverworld(PlayerEntity player){
        if (player.getWorld() instanceof ServerWorld) {
            RegistryKey<World> key = player.getWorld().getRegistryKey();
            return key == World.END && player.getY() < -56;
        }
        return false;
    }

    public static void teleportToEnd(PlayerEntity player){
        MinecraftServer server = player.getWorld().getServer();
        if (server != null){
            ServerWorld dest = server.getWorld(World.END);
            TeleportTarget target = new TeleportTarget(
                    player.getPos().add(0, -448, 0),
                    null,
                    player.getYaw(),
                    player.getPitch()
            );

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200));
            FabricDimensions.teleport(player, dest, target);
        }
    }

    public static void teleportToOverworld(PlayerEntity player){
        MinecraftServer server = player.getWorld().getServer();
        if (server != null){
            ServerWorld dest = server.getWorld(World.OVERWORLD);
            TeleportTarget target = new TeleportTarget(
                    player.getPos().add(0, 448, 0),
                    null,
                    player.getYaw(),
                    player.getPitch()
            );

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200));
            FabricDimensions.teleport(player, dest, target);
        }
    }

}
