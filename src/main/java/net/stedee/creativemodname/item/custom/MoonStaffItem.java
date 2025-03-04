package net.stedee.creativemodname.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.enchantment.ModdedEnchantments;
import net.stedee.creativemodname.entity.custom.StaffFireballEntity;
import net.stedee.creativemodname.util.EnchantmentsUtil;

import java.util.List;

public class MoonStaffItem extends SwordItem {
    public MoonStaffItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(getTranslationKey().replaceAll("item.", "tooltip.")));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack item = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1F, 1F);
        CreativeModName.LOGGER.info("Level: {}", EnchantmentsUtil.getLevel(item, ModdedEnchantments.FIREBALL_JUMPING));
        user.getItemCooldownManager().set(item.getItem(), user.isCreative() || EnchantmentsUtil.getLevel(item, ModdedEnchantments.FIREBALL_JUMPING) > 0 ? 10 : 200);
        if (!world.isClient()) {
            Vec3d look = user.getRotationVec(1F);

            double d1 = look.x * 0.1D;
            double d2 = look.y * 0.1D;
            double d3 = look.z * 0.1D;
            Vec3d vec31 = new Vec3d(d1, d2, d3);

            FireballEntity fireball = new StaffFireballEntity(item, world, user, vec31, 1 + EnchantmentsUtil.getLevel(item, ModdedEnchantments.BIGGER_FIREBALL));
            fireball.setPos(user.getX() + look.x * 1, user.getY() + 1.25, user.getZ() + look.z * 1);
            fireball.setVelocity(look);
            world.spawnEntity(fireball);
            item.damage(3, user, LivingEntity.getSlotForHand(hand));
        }
        return TypedActionResult.success(item);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return Ingredient.ofItems(Items.IRON_INGOT).test(ingredient);
    }
}
