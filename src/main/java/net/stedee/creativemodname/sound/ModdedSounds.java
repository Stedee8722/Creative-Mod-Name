package net.stedee.creativemodname.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;

public class ModdedSounds {
    public static final SoundEvent PLUSHIE_SQUEAKS = registerSoundEvent("plushie_squeaks");
    public static final SoundEvent GOAT_SCREAMS = registerSoundEvent("goat_screams");
    public static final SoundEvent CAT_MEOWS = registerSoundEvent("cat_meows");
    public static final SoundEvent LAMB_BAAHS = registerSoundEvent("lamb_baahs");
    public static final SoundEvent CUTE_LAMB_BAAHS = registerSoundEvent("cute_lamb_baahs");
    public static final SoundEvent RAT_SQUEAKS = registerSoundEvent("rat_squeaks");
    public static final SoundEvent ITEM_POPS = registerSoundEvent("item_pops");
    public static final SoundEvent BACKPACK_OPENS = registerSoundEvent("backpack_opens");
    public static final SoundEvent BACKPACK_CLOSES = registerSoundEvent("backpack_closes");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(CreativeModName.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        CreativeModName.LOGGER.info("Registering Sounds for " + CreativeModName.MOD_ID);
    }
}
