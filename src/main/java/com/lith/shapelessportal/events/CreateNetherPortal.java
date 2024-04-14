package com.lith.shapelessportal.events;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import com.lith.shapelessportal.Plugin;
import com.lith.shapelessportal.config.ConfigManager;
import com.lith.shapelessportal.utils.NetherPortalUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateNetherPortal implements Listener {
    private final Plugin plugin;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.FIRE)
            return;

        Player player = event.getPlayer();
        if (player == null)
            return;

        if (!ConfigManager.portalConfig.portalBlocks.contains(event.getBlockAgainst().getType()))
            return;

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!NetherPortalUtil.createNetherPortal(block, Axis.X))
                NetherPortalUtil.createNetherPortal(block, Axis.Z);
        });
    }
}
