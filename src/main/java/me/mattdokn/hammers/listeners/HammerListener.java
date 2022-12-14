package me.mattdokn.hammers.listeners;

import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HammerListener implements Listener {
    private static final List<Vector> offsetX = new ArrayList<>() {{
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
    private final PotionEffect MINING_FATIGUE = new PotionEffect(PotionEffectType.SLOW_DIGGING, 160, 0);
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().isSneaking()) return;

        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
        // if tool is hammer
        if (isHammer(tool)) {
            // break blocks in 3x3 depending on block face?
            if (cachedBlockFaces.containsKey(e.getPlayer().getUniqueId())) {
                // break in direction of block face...
                BlockFace face = cachedBlockFaces.get(e.getPlayer().getUniqueId());
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

    private boolean isPreferredTool(ItemStack tool, Block block) {
        return block.getBlockData().isPreferredTool(tool);
    }
    private boolean isTileEntity(BlockState state) {
        return state instanceof TileState;
    }
    private boolean isHammer(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == HammerUtils.IS_HAMMER;
    }

    private void breakBlocks(Player player, ItemStack tool, Location pos, List<Vector> offsets) {
        for (Vector offset : offsets) {
            Block block = player.getWorld().getBlockAt(pos.getBlockX() + offset.getBlockX(), pos.getBlockY() + offset.getBlockY(), pos.getBlockZ() + offset.getBlockZ());
            if (!isPreferredTool(tool, block)) {
                continue;
            }
            if (isTileEntity(block.getState())) {
                continue;
            }
            block.breakNaturally(tool, true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (isHammer(e.getItem())) {
                e.getPlayer().addPotionEffect(MINING_FATIGUE);
            }
            // save block face
            cachedBlockFaces.put(e.getPlayer().getUniqueId(), e.getBlockFace());
        }
    }

    @EventHandler
    public void onHandChange(PlayerItemHeldEvent e) {
        ItemStack prevItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());

        if (isHammer(prevItem)) {
            e.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            //e.getPlayer().discoverRecipe(NamespacedKey.fromString("diamond_block"));
            e.getPlayer().discoverRecipes(HammerUtils.recipes);
        }
    }
}
