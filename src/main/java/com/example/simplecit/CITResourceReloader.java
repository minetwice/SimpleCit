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
        // FIX: apply() method ab nahi hai, reload() use hota hai
        CITManager.clearRules();

        // FIX: findResources() ab Map<Identifier, Resource> return karta hai
        Map<Identifier, Resource> resources = manager.findResources("optifine/cit", 
            id -> id.getPath().endsWith(".properties"));

        resources.forEach((id, resource) -> {
            // FIX: resourceRef.getReader().read() ab nahi hai
            // resource.getInputStream() use karein
            try (InputStream is = resource.getInputStream()) {
                Properties props = new Properties();
                props.load(is);

                String namespace = id.getNamespace();
                CITRule rule = CITRule.fromProperties(props, namespace);
                if (rule != null) {
                    CITManager.addRule(rule);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
