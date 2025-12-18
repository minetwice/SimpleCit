package com.example.simplecit.mixin;

import com.example.simplecit.CITManager;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    
    @ModifyVariable(
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At("HEAD"),
        argsOnly = true
    )
    private BakedModel simplecit$overrideModel(BakedModel original, ItemStack stack) {
        Identifier overrideTexture = CITManager.getOverrideTexture(stack);
        if (overrideTexture != null) {
            // Return custom model with override texture
            return new com.example.simplecit.CITBakedModel(original, overrideTexture);
        }
        return original;
    }
}
