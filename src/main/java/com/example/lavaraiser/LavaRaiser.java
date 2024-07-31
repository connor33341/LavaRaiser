package com.example.lavaraiser;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LavaRaiser extends JavaPlugin {

    @Override
    public void onEnable() {
        // Schedule the task to run every 30 seconds (600 ticks)
        new BukkitRunnable() {
            @Override
            public void run() {
                raiseLava();
            }
        }.runTaskTimer(this, 0, 600);
    }

    private void raiseLava() {
        World world = Bukkit.getWorld("world"); // Change "world" to your world name if different
        if (world == null) return;

        int worldBorderSize = (int) world.getWorldBorder().getSize() / 2;
        int minX = -worldBorderSize;
        int maxX = worldBorderSize;
        int minZ = -worldBorderSize;
        int maxZ = worldBorderSize;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.LAVA) {
                        Block above = world.getBlockAt(x, y + 1, z);
                        if (above.getType() != Material.LAVA) {
                            above.setType(Material.LAVA);
                            if (above.getType() != Material.AIR) {
                                above.breakNaturally();
                            }
                        }
                    }
                }
            }
        }
    }
}
