package me.GamingCuber.MinuteDisaster.Listeners;

import me.GamingCuber.MinuteDisaster.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private Main plugin;

    public InventoryClickListener(Main plugin) {

        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getInventory().equals(plugin.ClickGame)) {

            e.setCancelled(true);

            if (e.getCurrentItem().getType().equals(plugin.GoalBlock)) {

                plugin.CompletedClickGame = true;
                for (Player p : e.getWhoClicked().getWorld().getPlayers()) {

                    p.closeInventory();

                }

            }

        } else {

            e.setCancelled(false);

        }

    }

}
