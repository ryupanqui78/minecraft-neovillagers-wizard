package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.helpers;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class UnenchantingHelper {
    
    private static Random rand = new Random();
    
    private static int calculateLevel1(int originalLevel, int maxLevel) {
        int level = originalLevel;
        final int index = UnenchantingHelper.rand.nextInt(180);
        if (index < 100) {
            level = maxLevel;
        }
        return level;
    }
    
    private static int calculateLevel2(int originalLevel, int maxLevel) {
        int level = originalLevel;
        final int index = UnenchantingHelper.rand.nextInt(240);
        if (index < 100) {
            level = maxLevel - 1;
        } else if (index < 180) {
            level = maxLevel;
        }
        return level;
    }
    
    private static int calculateLevel3(int originalLevel, int maxLevel) {
        int level = originalLevel;
        final int index = UnenchantingHelper.rand.nextInt(280);
        if (index < 100) {
            level = maxLevel - 2;
        } else if (index < 180) {
            level = maxLevel - 1;
        } else if ((index < 240) && (originalLevel > (maxLevel - 1))) {
            level = maxLevel;
        }
        return level;
    }
    
    private static int calculateLevel4(int originalLevel, int maxLevel) {
        int level = originalLevel;
        final int index = UnenchantingHelper.rand.nextInt(300);
        if (index < 100) {
            level = maxLevel - 3;
        } else if (index < 180) {
            level = maxLevel - 2;
        } else if ((index < 240) && (originalLevel > (maxLevel - 2))) {
            level = maxLevel - 1;
        } else if ((index < 280) && (originalLevel > (maxLevel - 1))) {
            level = maxLevel;
        }
        return level;
    }
    
    private static int calculatePowerOnCorner(Level pLevel, BlockPos pBlockPos, int l, int k) {
        int power = 0;
        if ((l != 0) && (k != 0)) {
            power += UnenchantingHelper.getPower(pLevel, pBlockPos.offset(l * 2, 0, k));
            power += UnenchantingHelper.getPower(pLevel, pBlockPos.offset(l * 2, 1, k));
            power += UnenchantingHelper.getPower(pLevel, pBlockPos.offset(l, 0, k * 2));
            power += UnenchantingHelper.getPower(pLevel, pBlockPos.offset(l, 1, k * 2));
        }
        return power;
    }
    
    public static int calculateTotalPower(Level pLevel, BlockPos pBlockPos) {
        int power = 0;
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if (((k != 0) || (l != 0)) && pLevel.isEmptyBlock(pBlockPos.offset(l, 0, k))
                        && pLevel.isEmptyBlock(pBlockPos.offset(l, 1, k))) {
                    power += UnenchantingHelper.getPower(pLevel, pBlockPos.offset(l * 2, 0, k * 2));
                    power += UnenchantingHelper.getPower(pLevel, pBlockPos.offset(l * 2, 1, k * 2));
                    power += UnenchantingHelper.calculatePowerOnCorner(pLevel, pBlockPos, l, k);
                }
            }
        }
        return power;
    }
    
    public static int defineLevel(Enchantment enchantment, int originalLevel, int totalPower) {
        int level = originalLevel;
        if ((totalPower <= 20) && (originalLevel > 4)) {
            level = UnenchantingHelper.calculateLevel1(originalLevel, enchantment.getMaxLevel() - 1);
        } else if ((totalPower <= 15) && (originalLevel > 3)) {
            level = UnenchantingHelper.calculateLevel2(originalLevel, enchantment.getMaxLevel() - 1);
        } else if ((totalPower <= 10) && (originalLevel > 2)) {
            level = UnenchantingHelper.calculateLevel3(originalLevel, enchantment.getMaxLevel() - 1);
        } else if ((totalPower <= 5) && (originalLevel > 1)) {
            level = UnenchantingHelper.calculateLevel4(originalLevel, enchantment.getMaxLevel() - 1);
        }
        return level > 0 ? level : 1;
    }
    
    private static float getPower(Level pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).getEnchantPowerBonus(pLevel, pPos);
    }
    
    private UnenchantingHelper() {
    }
}
