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
import com.lith.shapelessportal.utils.NetherPortalUtil;

public class CreateNetherPortal implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.FIRE)
            return;

        Player player = event.getPlayer();
        if (player == null)
            return;

        if (!NetherPortalUtil.checkPortalBlockType(event.getBlockAgainst()))
            return;

        Bukkit.getScheduler().runTask(Plugin.plugin, () -> {
            if (!NetherPortalUtil.createNetherPortal(block, Axis.X))
                NetherPortalUtil.createNetherPortal(block, Axis.Z);
        });
    }
}
