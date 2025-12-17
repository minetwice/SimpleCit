package com.example.simplecit;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.regex.Pattern;

public class CITRule {
    public final List<Item> items = new ArrayList<>();
    public String requiredNamePattern = null;
    public Identifier texture;

    public boolean matches(Item item, NbtCompound nbt) {
        if (!items.contains(item)) return false;

        if (requiredNamePattern != null && nbt != null) {
            NbtCompound display = nbt.getCompound("display");
            if (display.contains("Name", 8)) {
                try {
                    Text name = Text.Serialization.fromJson(display.getString("Name"), null);
                    if (name != null) {
                        String nameStr = name.getString();
                        return Pattern.compile(requiredNamePattern, Pattern.CASE_INSENSITIVE)
                                .matcher(nameStr).find();
                    }
                } catch (Exception ignored) {}
            }
            return false;
        }
        return true;
    }

    public static CITRule fromProperties(Properties props, String packName) {
        CITRule rule = new CITRule();

        String type = props.getProperty("type");
        if (!"item".equals(type)) return null;

        String itemsStr = props.getProperty("items");
        if (itemsStr != null) {
            for (String id : itemsStr.split(",")) {
                Identifier itemId = Identifier.tryParse(id.trim());
                if (itemId != null) {
                    Item item = Registries.ITEM.get(itemId);
                    if (item != null && item != Items.AIR) {
                        rule.items.add(item);
                    }
                }
            }
        }

        String nameCond = props.getProperty("nbt.display.Name");
        if (nameCond != null && nameCond.startsWith("ipattern:")) {
            String pat = nameCond.substring(9).replace("*", ".*").replace("?", ".");
            rule.requiredNamePattern = pat;
        }

        String tex = props.getProperty("texture");
        if (tex != null) {
            rule.texture = Identifier.of(packName, "textures/item/" + tex + ".png");
        }

        if (rule.items.isEmpty() || rule.texture == null) return null;
        return rule;
    }
}
