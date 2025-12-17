package com.example.simplecit;

import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Random;

public class CITBakedModel implements BakedModel {
    private final BakedModel parent;
    private final Sprite overrideSprite;

    public CITBakedModel(BakedModel parent, Identifier textureId) {
        this.parent = parent;
        SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, textureId);
        // ✅ SAFETY: Don't crash if texture missing — fallback to parent
        Sprite sprite = spriteId.getSprite();
        this.overrideSprite = (sprite != null && sprite.getContents() != null) ? sprite : parent.getParticleSprite();
    }

    @Override
    public List<BakedQuad> getQuads(net.minecraft.block.BlockState state, net.minecraft.util.math.Direction face, Random random) {
        return parent.getQuads(state, face, random);
    }

    @Override
    public boolean useAmbientOcclusion() { return parent.useAmbientOcclusion(); }
    @Override
    public boolean hasDepth() { return parent.hasDepth(); }
    @Override
    public boolean isSideLit() { return parent.isSideLit(); }
    @Override
    public boolean isBuiltin() { return parent.isBuiltin(); }

    // ✅ SAFETY: Never return null
    @Override
    public Sprite getParticleSprite() {
        return overrideSprite != null ? overrideSprite : parent.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() { return parent.getTransformation(); }
    @Override
    public BakedModel overrideProperties(ItemOverrides overrides) { return this; }
}
