package net.stedee.creativemodname.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.stedee.creativemodname.effect.custom.BloodlossEffect;

import java.util.Arrays;
import java.util.List;

public class CleaverItem extends AxeItem {
    protected RegistryEntry<StatusEffect>[] effects;
    protected boolean isUnbreakable;

    @SafeVarargs
    public CleaverItem(ToolMaterial toolMaterial, Settings settings, boolean isUnbreakable, RegistryEntry<StatusEffect>... effects) {
        super(toolMaterial, settings);
        this.effects = effects;
        this.isUnbreakable = isUnbreakable;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (isUnbreakable && stack.getComponents() != null && !stack.getComponents().contains(DataComponentTypes.UNBREAKABLE)) {
            stack.set(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true));
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        if (effects != null) {
            Arrays.stream(effects).forEach((effect) ->
                    target.addStatusEffect(new StatusEffectInstance(effect, effect.value() instanceof BloodlossEffect ? 6000 : 100, 0), attacker));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(getTranslationKey().replaceAll("item.", "tooltip.")));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return Ingredient.ofItems(Items.ROTTEN_FLESH).test(ingredient) || Ingredient.ofItems(Items.IRON_INGOT).test(ingredient);
    }
}
