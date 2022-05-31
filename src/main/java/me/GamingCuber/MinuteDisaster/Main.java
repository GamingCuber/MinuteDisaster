package me.GamingCuber.MinuteDisaster;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.GamingCuber.MinuteDisaster.Files.MessageFile;

public class Main extends JavaPlugin {

	private MessageFile MessageFile;
	public Server Serv;
	public Inventory ClickGame;
	public Material GoalBlock;
	public String GoalWord;
	public boolean CompletedClickGame;
	public boolean CompletedMessageGame;
	public boolean CanEat = true;
	public boolean CanAttack = true;
	public boolean MessageGameActive;

	@Override
	public void onEnable() {

		if (!getDataFolder().exists()) {

			getDataFolder().mkdirs();

		}
		// this thing should go at the very end
		this.MessageFile = new MessageFile(this);
	}

	@Override
	public void onDisable() {

		MessageFile.save();

	}

}
