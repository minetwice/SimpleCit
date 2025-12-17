package com.example.simplecit;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CITManager {
    private static final List<CITRule> RULES = new ArrayList<>();

    public static void clearRules() {
        RULES.clear();
    }

    public static void addRule(CITRule rule) {
        RULES.add(rule);
    }

    public static Identifier getOverrideTexture(ItemStack stack) {
        for (CITRule rule : RULES) {
            if (rule.matches(stack.getItem(), stack.getNbt())) {
                return rule.texture;
            }
        }
        return null;
    }
}
