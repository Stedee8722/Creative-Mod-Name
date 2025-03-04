package net.stedee.creativemodname.mixin;

import net.minecraft.server.world.ServerWorld;
import net.stedee.creativemodname.access.IServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class) // ServerWorld, MinecraftServer, etc
public class ServerWorldMixin implements IServerWorld {
    @Unique
    private long ticksUntilSomething;
    @Unique
    private Runnable task;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) { // Fix parameters as needed
        if (--this.ticksUntilSomething == 0L) {
            this.task.run();
            // If you want to repeat this, reset ticksUntilSomething here.
        }
    }

    @Override
    public void creativemodname$setTimer(long ticksUntilSomething, Runnable task) {
        this.ticksUntilSomething = ticksUntilSomething;
        this.task = task;
    }
}
