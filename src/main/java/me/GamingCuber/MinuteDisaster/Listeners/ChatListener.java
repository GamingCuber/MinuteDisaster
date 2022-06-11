package me.GamingCuber.MinuteDisaster.Listeners;

import me.GamingCuber.MinuteDisaster.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private Main plugin;

    public ChatListener(Main plugin) {

        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e) {

        if (plugin.MessageGameActive && e.getMessage().equals(plugin.GoalWord)) {

            e.getPlayer().sendMessage(ChatColor.GREEN + "Congrats, you did it!");

        }

    }

}
