package com.example.simplecit;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Random;

public class CITBakedModel implements BakedModel {
    private final BakedModel parent;
    private final Sprite overrideSprite;

    public CITBakedModel(BakedModel parent, Identifier textureId) {
        this.parent = parent;
        var spriteId = new net.minecraft.client.util.SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, textureId);
        this.overrideSprite = spriteId.getSprite();
    }

    @Override
    public List<net.minecraft.client.render.model.BakedQuad> getQuads(net.minecraft.block.BlockState state, net.minecraft.util.math.Direction face, Random random) {
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
    @Override
    public Sprite getParticleSprite() { return overrideSprite != null ? overrideSprite : parent.getParticleSprite(); }
    @Override
    public ModelTransformation getTransformation() { return parent.getTransformation(); }
    @Override
    public BakedModel overrideProperties(net.minecraft.client.render.model.ItemOverrides overrides) { return this; }
}
