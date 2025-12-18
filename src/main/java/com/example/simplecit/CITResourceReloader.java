package com.example.simplecit;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class CITResourceReloader implements SimpleSynchronousResourceReloadListener {
    public static final CITResourceReloader INSTANCE = new CITResourceReloader();

    @Override
    public Identifier getFabricId() {
        return Identifier.of("simplecit", "cit_reloader");
    }

@Override
public void reload(ResourceManager manager) {
    CITManager.clearRules();
    
    Map<Identifier, Resource> resources = manager.findResources("optifine/cit", 
        id -> id.getPath().endsWith(".properties"));

    SimpleCIT.LOGGER.info("Found {} CIT property files", resources.size());

    resources.forEach((id, resource) -> {
        try (InputStream is = resource.getInputStream()) {
            Properties props = new Properties();
            props.load(is);

            SimpleCIT.LOGGER.info("Loading CIT: {}", id);

            String namespace = id.getNamespace();
            CITRule rule = CITRule.fromProperties(props, namespace);
            if (rule != null) {
                CITManager.addRule(rule);
                SimpleCIT.LOGGER.info("Added CIT rule with {} items", rule.items.size());
            } else {
                SimpleCIT.LOGGER.warn("Failed to parse CIT rule: {}", id);
            }
        } catch (IOException e) {
            SimpleCIT.LOGGER.error("Error loading CIT: {}", id, e);
        }
    });
    
    SimpleCIT.LOGGER.info("Loaded {} CIT rules total", CITManager.RULES.size());
}
