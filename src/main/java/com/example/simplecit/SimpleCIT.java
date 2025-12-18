package com.example.simplecit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Class name ko SimpleCitMod kar do (file name ke according)
public class SimpleCitMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simplecit");

    @Override
    public void onInitialize() {
        LOGGER.info("SimpleCIT Initializing...");
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
            .registerReloadListener(CITResourceReloader.INSTANCE);
        LOGGER.info("SimpleCIT Initialized!");
    }
}
