package com.example.simplecit;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class CITBakedModel implements BakedModel {
    private final BakedModel parent;
    private final Sprite overrideSprite;

    public CITBakedModel(BakedModel parent, Identifier textureId) {
        this.parent = parent;
        SpriteIdentifier spriteId = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, textureId);
        this.overrideSprite = null;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
        return parent.getQuads(state, face, random);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return parent.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return parent.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return parent.isSideLit();
    }

    @Override
    public Sprite getParticleSprite() {
        return overrideSprite != null ? overrideSprite : parent.getParticleSprite();
    }
}
