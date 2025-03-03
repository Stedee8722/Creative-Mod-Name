package net.stedee.creativemodname.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.stedee.creativemodname.networking.packet.MakeSoundC2SPacket;
import net.stedee.creativemodname.sound.ModdedSounds;

import java.util.Objects;
import java.util.Random;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_SOUNDS = "key.category.creativemodname.sounds";
    public static final String KEY_GOAT_SCREAMS = "key.creativemodname.goat_screams";
    public static final String KEY_CAT_MEOWS = "key.creativemodname.cat_meows";
    public static final String KEY_LAMB_BAAHS = "key.creativemodname.lamb_baahs";
    public static final String KEY_RAT_SQUEAKS = "key.creativemodname.rat_squeaks";

    public static KeyBinding goatScreamKey;
    public static KeyBinding catMeowsKey;
    public static KeyBinding lambBaahsKey;
    public static KeyBinding ratSqueaksKey;
    public static long lastTime = 0;
    public static final long cooldown = 120;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime < cooldown) {
                return;
            }
            if (goatScreamKey.isPressed()) {
                ClientPlayNetworking.send(new MakeSoundC2SPacket(ModdedSounds.GOAT_SCREAMS, 1F));
                lastTime = currentTime;
            }
            if (ratSqueaksKey.isPressed()) {
                ClientPlayNetworking.send(new MakeSoundC2SPacket(ModdedSounds.RAT_SQUEAKS, 1F));
                lastTime = currentTime;
            }
            if (lambBaahsKey.isPressed()) {
                if (minecraftClient.player != null && Objects.equals(minecraftClient.player.getName().getString(), "sossh3d")) {
                    ClientPlayNetworking.send(new MakeSoundC2SPacket(ModdedSounds.CUTE_LAMB_BAAHS, 1F));
                    lastTime = currentTime;
                    return;
                }
                ClientPlayNetworking.send(new MakeSoundC2SPacket(ModdedSounds.LAMB_BAAHS, 1F));
                lastTime = currentTime;
            }
            if (catMeowsKey.isPressed()) {
                float min = 0.75F;
                float max = 1.25F;
                Random r = new Random();
                ClientPlayNetworking.send(new MakeSoundC2SPacket(ModdedSounds.CAT_MEOWS, min + r.nextFloat() * (max - min)));
                lastTime = currentTime;
            }
        });
    }

    public static void register() {
        goatScreamKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_GOAT_SCREAMS,
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                KEY_CATEGORY_SOUNDS
        ));
        catMeowsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_CAT_MEOWS,
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                KEY_CATEGORY_SOUNDS
        ));
        lambBaahsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_LAMB_BAAHS,
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                KEY_CATEGORY_SOUNDS
        ));
        ratSqueaksKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_RAT_SQUEAKS,
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                KEY_CATEGORY_SOUNDS
        ));

        registerKeyInputs();
    }
}
