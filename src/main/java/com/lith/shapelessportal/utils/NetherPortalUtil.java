package com.lith.shapelessportal.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import java.util.Arrays;
import org.bukkit.block.data.Orientable;

public class NetherPortalUtil {
    public static boolean createNetherPortal(Block block, Axis axis, int maxSize) {
        final List<BlockFace> searchDirections;
        final List<BlockFace> alternateDirections;

        if (axis == Axis.X) {
            searchDirections = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.WEST, BlockFace.EAST);
            alternateDirections = Arrays.asList(BlockFace.NORTH, BlockFace.SOUTH);
        } else if (axis == Axis.Y) {
            searchDirections = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH);
            alternateDirections = Arrays.asList(BlockFace.EAST, BlockFace.WEST);
        } else
            return false;

        final Set<Block> blocks = new HashSet<>();
        final Set<Block> searched = new HashSet<>();

        blocks.add(block);
        searched.add(block);

        for (BlockFace face : searchDirections)
            if (!checkPortalCreationBlock(block.getRelative(face), blocks, searched, searchDirections, maxSize))
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

    public static boolean checkPortalBlockType(Block block) {
        Material material = block.getType();

        return material == Material.OBSIDIAN || material == Material.CRYING_OBSIDIAN;
    }

    private static boolean checkPortalCreationBlock(
            final Block block, Set<Block> blocks, Set<Block> searched,
            List<BlockFace> searchDirections, int maxSize) {
        if (blocks.size() > maxSize)
            return false;

        if (searched.contains(block))
            return true;

        searched.add(block);

        if (block.isEmpty()) {
            blocks.add(block);

            for (BlockFace face : searchDirections)
                if (!checkPortalCreationBlock(
                        block.getRelative(face), blocks,
                        searched, searchDirections, maxSize))
                    return false;

            return true;
        } else
            return checkPortalBlockType(block);
    }
}
