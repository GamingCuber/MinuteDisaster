package me.GamingCuber.MinuteDisaster.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.GamingCuber.MinuteDisaster.Main;

public class MessageYML {

	private Main plugin;
	public File File;
	public FileConfiguration config;

	public MessageYML(Main plugin, String FileName) {

		this.plugin = plugin;
		this.File = new File(plugin.getDataFolder(), FileName);
		if (!File.exists()) {

			try {

				File.createNewFile();

			} catch (IOException e) {

				e.printStackTrace();

			}
			this.config = YamlConfiguration.loadConfiguration(File);
		}

	}

	public void save() {

		try {

			config.save(File);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
