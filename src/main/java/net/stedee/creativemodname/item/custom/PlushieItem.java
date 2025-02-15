package net.stedee.creativemodname.item.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.stedee.creativemodname.sound.ModdedSounds;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlushieItem extends BlockItem implements Equipment {
    public PlushieItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack item = user.getStackInHand(hand);
        if (!world.isClient()) {
            play_squeak(world, user, hand);
            return TypedActionResult.consume(item);
        }
        return TypedActionResult.consume(item);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(getTranslationKey().replaceAll("block.", "tooltip.")));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 10000;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() != null && context.getPlayer().isSneaking()) {
            return ActionResult.PASS;
        }
        ActionResult result = super.useOnBlock(context);
        if (result != ActionResult.FAIL) {
            return result;
        }
        return ActionResult.PASS;
    }

    private void play_squeak(@NotNull World world, @NotNull PlayerEntity user, Hand hand) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModdedSounds.PLUSHIE_SQUEAKS, SoundCategory.PLAYERS, 1f, 1f);
        user.setCurrentHand(hand);
    }
}
