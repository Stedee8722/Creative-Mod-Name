package net.stedee.creativemodname.compat.accessories;

import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.item.custom.PlushieItem;

import java.util.Objects;

public class AccessoriesInit {
    @Environment(EnvType.CLIENT)
    public static void initClient() {
        Registries.ITEM.stream()
                .filter(item -> item instanceof PlushieItem && !Objects.equals(item.getName(), Text.translatable("block.creativemodname.plush_acid")))
                .forEach(item -> AccessoriesRendererRegistry.registerRenderer(item, Renderer::new));
        AccessoriesRendererRegistry.registerRenderer(ModdedPlushieBlocks.PLUSH_ACID.asItem(), AcidRenderer::new);
    }

    @Environment(EnvType.CLIENT)
    public static class Renderer implements SimpleAccessoryRenderer {
        @Override
        public <M extends LivingEntity> void render(
                ItemStack stack,
                SlotReference reference,
                MatrixStack matrices,
                EntityModel<M> model,
                VertexConsumerProvider multiBufferSource,
                int light,
                float limbSwing,
                float limbSwingAmount,
                float partialTicks,
                float ageInTicks,
                float netHeadYaw,
                float headPitch
        ) {
            Entity entity = reference.entity();

            if (!(entity instanceof PlayerEntity player)) return;

            matrices.push();

            // Move to the back of the player (relative to model)
            if (model instanceof BipedEntityModel<?> bipedModel) {
                bipedModel.body.rotate(matrices); // Align with body
            }

            // Adjust position behind the player
            matrices.translate(0.0, 0.7, 0.3); // Y=up, Z=back
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(180));
            matrices.scale(0.4F, 0.4F, 0.4F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    stack,
                    ModelTransformationMode.FIXED,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    multiBufferSource,
                    entity.getWorld(),
                    0
            );

            matrices.pop();
        }

        @Override
        public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model, MatrixStack matrices) {
        }
    }

    @Environment(EnvType.CLIENT)
    public static class AcidRenderer implements SimpleAccessoryRenderer {
        @Override
        public <M extends LivingEntity> void render(
                ItemStack stack,
                SlotReference reference,
                MatrixStack matrices,
                EntityModel<M> model,
                VertexConsumerProvider multiBufferSource,
                int light,
                float limbSwing,
                float limbSwingAmount,
                float partialTicks,
                float ageInTicks,
                float netHeadYaw,
                float headPitch
        ) {
            Entity entity = reference.entity();

            if (!(entity instanceof PlayerEntity player)) return;

            matrices.push();

            // Move to the back of the player (relative to model)
            if (model instanceof BipedEntityModel<?> bipedModel) {
                bipedModel.body.rotate(matrices); // Align with body
            }

            // Adjust position behind the player
            matrices.translate(0.0, 0.5, 0.4); // Y=up, Z=back
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
            matrices.scale(0.4F, 0.4F, 0.4F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    stack,
                    ModelTransformationMode.FIXED,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    multiBufferSource,
                    entity.getWorld(),
                    0
            );

            matrices.pop();
        }

        @Override
        public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model, MatrixStack matrices) {
        }
    }
}
