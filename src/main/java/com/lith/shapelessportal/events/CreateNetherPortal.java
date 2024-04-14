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
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import java.util.Arrays;
import org.bukkit.block.data.Orientable;

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

        if (!plugin.configs.portalConfig.portalBlocks.contains(event.getBlockAgainst().getType()))
            return;

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!createNetherPortal(block, Axis.X))
                createNetherPortal(block, Axis.Z);
        });
    }

    private boolean createNetherPortal(Block block, Axis axis) {
        final List<BlockFace> searchDirections;
        final List<BlockFace> alternateDirections;

        if (axis == Axis.X) {
            searchDirections = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.WEST, BlockFace.EAST);
            alternateDirections = Arrays.asList(BlockFace.NORTH, BlockFace.SOUTH);
        } else if (axis == Axis.Z) {
            searchDirections = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH);
            alternateDirections = Arrays.asList(BlockFace.EAST, BlockFace.WEST);
        } else
            return false;

        final Set<Block> blocks = new HashSet<>();
        final Set<Block> searched = new HashSet<>();

        blocks.add(block);
        searched.add(block);

        for (BlockFace face : searchDirections)
            if (!checkPortalCreationBlock(block.getRelative(face), blocks, searched, searchDirections))
                return false;

        for (Block portal : blocks)
            for (BlockFace face : alternateDirections)
                if (portal.getRelative(face).getType() == Material.NETHER_PORTAL)
                    return false;

        BlockData portalData = Material.NETHER_PORTAL.createBlockData();
        ((Orientable) portalData).setAxis(axis);

        for (Block portal : blocks)
            portal.setBlockData(portalData, false);

        return true;
    }

    private boolean checkPortalCreationBlock(final Block block, Set<Block> blocks,
            Set<Block> searched, List<BlockFace> searchDirections) {
        if (blocks.size() > plugin.configs.portalConfig.maxPortalSize)
            return false;

        if (searched.contains(block))
            return true;

        searched.add(block);

        if (block.isEmpty()) {
            blocks.add(block);

            for (BlockFace face : searchDirections)
                if (!checkPortalCreationBlock(
                        block.getRelative(face), blocks,
                        searched, searchDirections))
                    return false;

            return true;
        } else
            return plugin.configs.portalConfig.portalBlocks.contains(block.getType());
    }
}
