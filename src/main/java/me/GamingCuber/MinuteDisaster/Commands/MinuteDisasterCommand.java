package me.GamingCuber.MinuteDisaster.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import me.GamingCuber.MinuteDisaster.Main;
import me.GamingCuber.MinuteDisaster.Listeners.EatListener;
import net.md_5.bungee.api.ChatColor;

public class MinuteDisasterCommand implements TabExecutor {

	private Main plugin;

	public MinuteDisasterCommand(Main plugin) {

		this.plugin = plugin;
		plugin.getCommand("minutedisaster").setExecutor(this);

	}

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 1) {

			ArrayList<String> TabList = new ArrayList<>();
			TabList.add("start");
			TabList.add("stop");
			TabList.add("help");
			return TabList;

		}

		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Random rand = new Random();
		plugin.Serv = sender.getServer();
		Player psender = (Player) sender;
		World w = psender.getWorld();
		String FirstArg = args[0];
		if (FirstArg.equalsIgnoreCase("start")) {

			// this is the first countdown runnable
			new BukkitRunnable() {

				int cnt = 0;

				@Override
				public void run() {

					if (cnt == 1) {

						plugin.Serv.broadcastMessage(ChatColor.GREEN + "Starting in " + cnt + " second...");

					} else if (cnt == 0) {

						plugin.Serv.broadcastMessage(ChatColor.GREEN + "The disasters have started!");
						this.cancel();

					} else {

						plugin.Serv.broadcastMessage(ChatColor.GREEN + "Starting in " + cnt + " seconds...");

					}

				}

			}.runTaskTimer(plugin, 0L, 20L);

			// this is the countdown thing after the first one.

			new BukkitRunnable() {

				@Override
				public void run() {

					new BukkitRunnable() {

						int cnt = 10;

						public void run() {

							if (cnt == 0) {

								plugin.Serv.broadcastMessage(
										ChatColor.BOLD + "" + ChatColor.AQUA + "DISASTER HAS OCCURRED!");

							} else if (cnt == 1) {

								plugin.Serv.broadcastMessage(ChatColor.AQUA + "Disaster in 1 second...");

							} else {

								plugin.Serv.broadcastMessage(ChatColor.AQUA + "Disaster in " + cnt + " seconds...");

							}

						}

					}.runTaskTimer(plugin, 0L, 20L);

				}

			}.runTaskTimer(plugin, 60 * 20L, 60 * 20L);

			// this is the runnable that controls the disasters
			new BukkitRunnable() {

				@Override
				public void run() {

					int Randomizer = rand.nextInt(19);

					switch (Randomizer) {

					case 1:
						plugin.Serv.broadcastMessage(ChatColor.BLUE + "Eating has now been disabled!");
						plugin.CanEat = false;
						new BukkitRunnable() {

							@Override
							public void run() {

								plugin.Serv.broadcastMessage(ChatColor.AQUA + "You can now eat!");
								plugin.CanEat = true;

							}

						}.runTaskLater(plugin, 45 * 20L);
						break;
					case 2:
						for (Player p : w.getPlayers()) {

							w.spawnEntity(p.getLocation(), EntityType.RAVAGER);

						}
						plugin.Serv
								.broadcastMessage(ChatColor.BLUE + "A ravager has spawned in each of your locations!");
						break;
					case 3:
						for (Player p : w.getPlayers()) {

							p.getLocation().add(0, 15, 0).getBlock().setType(Material.ANVIL);

						}
						plugin.Serv.broadcastMessage(ChatColor.RED + "THE AIR STRIKE HAS STARTED!");
						break;
					case 4:
						for (Player p : w.getPlayers()) {

							for (int i = 0; i < 10; i++) {

								w.spawnEntity(p.getLocation(), EntityType.SKELETON);

							}
						}
						plugin.Serv.broadcastMessage(
								ChatColor.BLUE + "The skeletons have been dispatched to the enemy location!");
						break;
					case 5:
						for (Player p : w.getPlayers()) {

							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 3));

						}
						plugin.Serv
								.broadcastMessage(ChatColor.AQUA + "The elder guardians have cursed you! MUAHAHAHAHA");
						break;
					case 6:
						plugin.Serv.broadcastMessage(
								ChatColor.AQUA + "Click the Block quickly in 5 seconds, otherwise you die!");
						plugin.CompletedClickGame = false;
						for (Player p : w.getPlayers()) {

							ArrayList<Material> ClickGameList = new ArrayList<>();
							ClickGameList.add(Material.SPONGE);
							ClickGameList.add(Material.RAW_GOLD_BLOCK);
							ClickGameList.add(Material.HONEYCOMB_BLOCK);
							ClickGameList.add(Material.HORN_CORAL_BLOCK);
							ClickGameList.add(Material.GOLD_BLOCK);
							ClickGameList.add(Material.GOLD_INGOT);
							ClickGameList.add(Material.GOLD_NUGGET);
							ClickGameList.add(Material.GOLD_ORE);
							plugin.GoalBlock = ClickGameList.get(rand.nextInt(ClickGameList.size()));
							plugin.ClickGame = plugin.Serv.createInventory(p, 81,
									"Click the " + plugin.GoalBlock.name().replace('_', ' ').toLowerCase() + "!");
							for (int i = 0; i < ClickGameList.size(); i++) {

								Material GeneratorBlock = ClickGameList.get(rand.nextInt(ClickGameList.size()));
								plugin.ClickGame.setItem(i, new ItemStack(GeneratorBlock));

							}
							new BukkitRunnable() {

								@Override
								public void run() {

									if (plugin.CompletedClickGame) {

										plugin.Serv.broadcastMessage(ChatColor.BLUE + "Congrats, you guys did it!");
										return;

									} else {

										for (Player p : w.getPlayers()) {

											p.setHealth(0.0);
											plugin.Serv.broadcastMessage(ChatColor.RED + "Sorry, you guys failed!");

										}

									}

								}

							}.runTaskLater(plugin, 5 * 20);

						}

						break;
					case 7:
						for (Player p : w.getPlayers()) {

							Inventory PlayerInventory = p.getInventory();
							PlayerInventory.remove(
									PlayerInventory.getItem(rand.nextInt(PlayerInventory.getStorageContents().length)));

						}
						plugin.Serv.broadcastMessage(ChatColor.DARK_GREEN + "One of your items has been stolen!");
						break;
					case 8:
						for (Player p : w.getPlayers()) {

							p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30 * 20, 69));

						}
						plugin.Serv.broadcastMessage(ChatColor.AQUA + "Have fun :)");
						break;
					case 9:
						for (Player p : w.getPlayers()) {

							p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 15 * 20, 3));

						}
						plugin.Serv.broadcastMessage(ChatColor.AQUA + "You have been cursed by Wingardium Leviosa!");

						break;
					case 10:
						plugin.Serv.broadcastMessage(ChatColor.AQUA + "You cannot attack anything for 30 seconds!");
						plugin.CanAttack = false;
						new BukkitRunnable() {

							@Override
							public void run() {

								plugin.CanAttack = true;
								plugin.Serv.broadcastMessage(ChatColor.AQUA + "You can attack now!");

							}

						}.runTaskLater(plugin, 30 * 20);
						break;
					case 11:
						plugin.CompletedMessageGame = false;
						ArrayList<String> WordList = new ArrayList<>();
						plugin.GoalWord = WordList.get(rand.nextInt(WordList.size()));
						plugin.Serv.broadcastMessage(ChatColor.AQUA + "You must send the message in chat " + ChatColor.GREEN + "\"" + plugin.GoalWord + "\"");
						new BukkitRunnable() {

							@Override
							public void run() {

								if (plugin.CompletedMessageGame) {
									
									
									
								}
								
							}

						}.runTaskLater(plugin, 7 * 20L);
						break;

					}

				}

			}.runTaskTimer(plugin, 70 * 20L, 60 * 20L);

		}

		return false;
	}
}
