package me.mattdokn.hammers.listeners;

import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HammerListener implements Listener {
    private static final List<Vector> offsetX = new ArrayList() {{
        add(new Vector(0, 1, -1));
        add(new Vector(0, 1, 0));
        add(new Vector(0, 1, 1));

        add(new Vector(0, 0, -1));
        add(new Vector(0, 0, 1));

        add(new Vector(0, -1, -1));
        add(new Vector(0, -1, 0));
        add(new Vector(0, -1, 1));
    }};
    private static final List<Vector> offsetY = new ArrayList<>() {{

        add(new Vector(1, 0, 1));
        add(new Vector(1, 0, 0));
        add(new Vector(1, 0, -1));
        add(new Vector(0, 0, -1));
        add(new Vector(0, 0, 1));
        add(new Vector(-1, 0, -1));
        add(new Vector(-1, 0, 0));
        add(new Vector(-1, 0, 1));
    }};
    private static final List<Vector> offsetZ = new ArrayList<>() {{
        add(new Vector(-1, 1, 0));
        add(new Vector(0, 1, 0));
        add(new Vector(1, 1, 0));
        add(new Vector(-1, 0, 0));
        add(new Vector(1, 0, 0));
        add(new Vector(-1, -1, 0));
        add(new Vector(0, -1, 0));
        add(new Vector(1, -1, 0));
    }};
    private final HashMap<UUID, BlockFace> cachedBlockFaces = new HashMap<>();
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().isSneaking()) return;

        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
        // if tool is hammer
        if (tool.hasItemMeta() && tool.getItemMeta().hasCustomModelData() && tool.getItemMeta().getCustomModelData() == HammerUtils.IS_HAMMER) {
            // break blocks in 3x3 depending on block face?
            if (cachedBlockFaces.containsKey(e.getPlayer().getUniqueId())) {
                // break in direction of block face...
                BlockFace face = cachedBlockFaces.get(e.getPlayer().getUniqueId());
                e.getPlayer().sendMessage(face.getDirection().toString());
                Vector dir = face.getDirection();

                if (dir.getX() != 0D) {
                    breakBlocks(e.getPlayer(), tool, e.getBlock().getLocation(), offsetX);
                } else if (dir.getY() != 0D) {
                    breakBlocks(e.getPlayer(), tool, e.getBlock().getLocation(), offsetY);
                } else if (dir.getZ() != 0D) {
                    breakBlocks(e.getPlayer(), tool, e.getBlock().getLocation(), offsetZ);
                }

            }
        }
    }

    private void breakBlocks(Player player, ItemStack tool, Location pos, List<Vector> offsets) {
        for (Vector offset : offsets) {
            Block block = player.getWorld().getBlockAt(pos.getBlockX() + offset.getBlockX(), pos.getBlockY() + offset.getBlockY(), pos.getBlockZ() + offset.getBlockZ());
            if (!block.isPreferredTool(tool)) continue;
            block.breakNaturally(tool, true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            // save block face
            cachedBlockFaces.put(e.getPlayer().getUniqueId(), e.getBlockFace());
        }
    }
}
