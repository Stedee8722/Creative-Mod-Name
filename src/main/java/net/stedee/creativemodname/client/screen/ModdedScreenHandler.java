package net.stedee.creativemodname.client.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.client.screen.custom.PlushBackpackScreenHandler;

public class ModdedScreenHandler {
    public static final ScreenHandlerType<PlushBackpackScreenHandler> PLUSH_BACKPACK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(CreativeModName.MOD_ID, "plush_backpack_screen_handler"),
                    new ExtendedScreenHandlerType<>(PlushBackpackScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        CreativeModName.LOGGER.info("Registering Screen Handlers for " + CreativeModName.MOD_ID);
    }
}
