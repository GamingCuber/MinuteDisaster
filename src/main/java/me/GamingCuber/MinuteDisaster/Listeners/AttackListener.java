package me.GamingCuber.MinuteDisaster.Listeners;

import me.GamingCuber.MinuteDisaster.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackListener implements Listener {

    private Main plugin;

    public AttackListener(Main plugin) {

        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {

        if (e.getDamager().getType().equals(EntityType.PLAYER) && !plugin.CanAttack) {

            e.setCancelled(true);

        } else {

            e.setCancelled(false);

        }

    }

}
