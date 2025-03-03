package net.stedee.creativemodname;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class CreativeModNameServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        CreativeModName.LOGGER.info("Hello from Server!");
    }
}
