package com.example.simplecit;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CITResourceReloader implements SimpleSynchronousResourceReloadListener {
    public static final CITResourceReloader INSTANCE = new CITResourceReloader();

    @Override
    public Identifier getFabricId() {
        return Identifier.of("simplecit", "cit_reloader");
    }

    @Override
    public void apply(ResourceManager manager) {
        CITManager.clearRules();

        manager.findResources("optifine/cit", id -> id.getPath().endsWith(".properties"))
                .forEach((id, resourceRef) -> {
                    try (InputStream is = resourceRef.getReader().read()) {
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
