package me.mattdokn.hammers.listeners;

import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.TileState;
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
import org.jetbrains.annotations.NotNull;

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

    private static class HammerBreakBlockEvent extends BlockBreakEvent {
        public HammerBreakBlockEvent(@NotNull Block theBlock, @NotNull Player player) {
            super(theBlock, player);
        }
    }

    private final HashMap<UUID, BlockFace> cachedBlockFaces = new HashMap<>();
    //private final PotionEffect MINING_FATIGUE = new PotionEffect(PotionEffectType.MINING_FATIGUE, 160, 0);
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e instanceof HammerBreakBlockEvent) return;
        if (e.getPlayer().isSneaking()) return;

        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
        // if tool is hammer and is best mined by pickaxe and not tile entity
        if (isHammer(tool) && hasMineablePickaxeTag(e.getBlock().getType()) && !(e.getBlock().getState() instanceof TileState)) {
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

    private boolean hasMineablePickaxeTag(Material material) {
        return Tag.MINEABLE_PICKAXE.isTagged(material);
    }
    private boolean isHammer(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == HammerUtils.IS_HAMMER;
    }

    private void breakBlocks(Player player, ItemStack tool, Location pos, List<Vector> offsets) {
        for (Vector offset : offsets) {
            Block block = player.getWorld().getBlockAt(pos.getBlockX() + offset.getBlockX(), pos.getBlockY() + offset.getBlockY(), pos.getBlockZ() + offset.getBlockZ());
            if (!hasMineablePickaxeTag(block.getType())) {
                continue;
            }
            if (block.getState() instanceof TileState) {
                continue;
            }
            HammerBreakBlockEvent e = new HammerBreakBlockEvent(block, player);
            Bukkit.getPluginManager().callEvent(e);
            if (!e.isCancelled()) {
                block.breakNaturally(tool, true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            /*if (isHammer(e.getItem()) && e.getClickedBlock() != null && hasMineablePickaxeTag(e.getClickedBlock().getType())) {
                e.getPlayer().addPotionEffect(MINING_FATIGUE);
            }*/
            // save block face
            cachedBlockFaces.put(e.getPlayer().getUniqueId(), e.getBlockFace());
        }
    }

    @EventHandler
    public void onHandChange(PlayerItemHeldEvent e) {
        ItemStack prevItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());

        /*if (isHammer(prevItem)) {
            e.getPlayer().removePotionEffect(PotionEffectType.MINING_FATIGUE);
        }*/
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // e.getPlayer().setResourcePack("https://www.dropbox.com/s/rd46bh3c8q5jmen/HammersResourcePack.zip?dl=1", "54870a6b7ff0cf793ec53f95d2b0ce31eb57f640");
        if (!e.getPlayer().hasPlayedBefore()) {
            e.getPlayer().discoverRecipes(HammerUtils.recipes);
        }
    }
}
