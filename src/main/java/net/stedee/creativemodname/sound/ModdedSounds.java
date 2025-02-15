package net.stedee.creativemodname.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;

public class ModdedSounds {
    public static final SoundEvent PLUSHIE_SQUEAKS = registerSoundEvent("plushie_squeaks");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(CreativeModName.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        CreativeModName.LOGGER.info("Registering Mod Sounds for " + CreativeModName.MOD_ID);
    }
}
