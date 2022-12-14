package me.mattdokn.hammers.listeners;

import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class HammerListener implements Listener {
    private static HashMap<UUID, BlockFace> cachedBlockFaces = new HashMap<>();
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
        // if tool is hammer
        if (tool.hasItemMeta() && tool.getItemMeta().hasCustomModelData() && tool.getItemMeta().getCustomModelData() == HammerUtils.IS_HAMMER) {
            // break blocks in 3x3 depending on block face?
            if (cachedBlockFaces.containsKey(e.getPlayer().getUniqueId())) {
                // break in direction of block face...
                e.getPlayer().sendMessage("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
        }

        //  &&
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            // save blockface
            cachedBlockFaces.put(e.getPlayer().getUniqueId(), e.getBlockFace());
        }
    }
}
