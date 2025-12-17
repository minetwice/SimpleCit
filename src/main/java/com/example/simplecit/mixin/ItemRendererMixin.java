package com.example.simplecit.mixin;

import com.example.simplecit.CITManager;
import com.example.simplecit.CITBakedModel;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyReturnValue(method = "getHeldItemModel", at = @At("RETURN"))
    private BakedModel injectCITModel(BakedModel original, ItemStack stack) {
        Identifier tex = CITManager.getOverrideTexture(stack);
        if (tex == null) return original;

        return new CITBakedModel(original, tex);
    }
}
