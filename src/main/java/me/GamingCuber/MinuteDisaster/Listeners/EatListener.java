package me.GamingCuber.MinuteDisaster.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.GamingCuber.MinuteDisaster.Main;

public class EatListener implements Listener {

	private Main plugin;

	public EatListener(Main plugin) {

		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);

	}

	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {

		if (plugin.CanEat) {

			e.setCancelled(false);

		} else {

			e.setCancelled(true);

		}

	}

}
