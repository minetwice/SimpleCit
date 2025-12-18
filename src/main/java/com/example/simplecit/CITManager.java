package com.example.simplecit;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CITManager {
    // CHANGE: private se public kar diya
    public static final List<CITRule> RULES = new ArrayList<>();

    public static void clearRules() {
        RULES.clear();
    }

    public static void addRule(CITRule rule) {
        RULES.add(rule);
    }

    public static Identifier getOverrideTexture(ItemStack stack) {
        for (CITRule rule : RULES) {
            NbtCompound nbt = null;
            
            if (stack.contains(DataComponentTypes.CUSTOM_DATA)) {
                NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
                if (nbtComponent != null) {
                    nbt = nbtComponent.copyNbt();
                }
            }
            
            if (rule.matches(stack.getItem(), nbt)) {
                return rule.texture;
            }
        }
        return null;
    }
}
