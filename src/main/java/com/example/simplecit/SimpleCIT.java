package com.example.simplecit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class SimpleCitMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
            .registerReloadListener(CITResourceReloader.INSTANCE);
    }
}
