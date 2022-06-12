package me.GamingCuber.MinuteDisaster.Commands;

import me.GamingCuber.MinuteDisaster.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

import java.awt.*;
import java.util.List;
import java.util.*;

public class MinuteDisasterCommand implements TabExecutor {

    private Main plugin;

    public MinuteDisasterCommand(Main plugin) {

        this.plugin = plugin;
        plugin.getCommand("disaster").setExecutor(this);

    }

    @Override
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        final Random rand = new Random();
        plugin.Serv = sender.getServer();
        Player psender = (Player) sender;
        final World w = psender.getWorld();
        String FirstArg = args[0];
        if (FirstArg.equalsIgnoreCase("start")) {

            // this is the first countdown runnable

            new BukkitRunnable() {

                int cnt = 11;

                @Override
                public void run() {

                    cnt--;

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

                        int cnt = 11;

                        public void run() {

                            cnt--;

                            if (cnt == 0) {

                                plugin.Serv.broadcastMessage(
                                        ChatColor.BOLD + "" + ChatColor.AQUA + "DISASTER HAS OCCURRED!");
                                this.cancel();

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

                    int Randomizer = rand.nextInt(15);
                    switch (Randomizer) {

                        case 0:
                            plugin.Serv.broadcastMessage(ChatColor.GREEN + "You're lucky! No disaster this minute.");
                            break;
                        case 1:
                            plugin.Serv.broadcastMessage(ChatColor.BLUE + "Eating has now been disabled!");
                            plugin.CanEat = true;
                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    plugin.Serv.broadcastMessage(ChatColor.AQUA + "You can now eat!");
                                    plugin.CanEat = false;

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

                            for (Player p : w.getPlayers()) {

                                plugin.ClickGame = plugin.Serv.createInventory(p, 54,
                                        "Click the " + plugin.GoalBlock.name().replace('_', ' ').toLowerCase() + "!");
                                for (int i = 0; i < 54; i++) {

                                    Material GeneratorBlock = ClickGameList.get(rand.nextInt(ClickGameList.size()));
                                    plugin.ClickGame.setItem(i, new ItemStack(GeneratorBlock));

                                }
                                p.openInventory(plugin.ClickGame);
                            }
                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    if (plugin.CompletedClickGame) {

                                        plugin.Serv.broadcastMessage(ChatColor.BLUE + "Congrats, you guys did it!");

                                    } else {

                                        for (Player p : w.getPlayers()) {

                                            p.setHealth(0.0);
                                            plugin.Serv.broadcastMessage(ChatColor.RED + "Sorry, you guys failed!");

                                        }

                                    }

                                }

                            }.runTaskLater(plugin, 5 * 20);



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
                            plugin.MessageGameActive = true;
                            ArrayList<Object> WordList = new ArrayList<>();
                            WordList.addAll(Arrays.asList(EntityType.values()));
                            WordList.addAll(Arrays.asList(Material.values()));
                            plugin.GoalWord = WordList.get(rand.nextInt(WordList.size())).toString().toLowerCase().replace('_', ' ');
                            plugin.Serv.broadcastMessage(ChatColor.AQUA + "You must send the message in chat " + ChatColor.GREEN + "\"" + plugin.GoalWord + "\"");
                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    plugin.MessageGameActive = false;

                                    if (plugin.CompletedMessageGame) {

                                        plugin.Serv.broadcastMessage(ChatColor.GREEN + "Congrats, you guys did it!");

                                    } else {

                                        plugin.Serv.broadcastMessage(ChatColor.RED + "Sorry, someone failed it!");
                                        for (Player p : w.getPlayers()) {

                                            p.setHealth(0.0);

                                        }

                                    }

                                }

                            }.runTaskLater(plugin, 7 * 20L);
                            break;
                        case 12:
                            for (Player p : w.getPlayers()) {

                                p.setFireTicks(30 * 20);

                            }
                            plugin.Serv.broadcastMessage(ChatColor.of(Color.orange) + "You have been set ablaze!");
                            break;
                        case 13:
                            for (Player p : w.getPlayers()) {

                                Location PlayerNorth = p.getLocation().subtract(0, 0, 8);
                                Location PlayerSouth = p.getLocation().add(0, 0, 8);
                                Location PlayerEast = p.getLocation().add(8, 0, 0);
                                Location PlayerWest = p.getLocation().subtract(8, 0, 0);
                                ArrayList<Location> LocationList = new ArrayList<>();
                                LocationList.add(PlayerNorth);
                                LocationList.add(PlayerSouth);
                                LocationList.add(PlayerEast);
                                LocationList.add(PlayerWest);
                                for (int i = 0; i < 4; i++) {

                                    EntityType[] EntityList = EntityType.values();
                                    EntityType RandomMob = EntityList[rand.nextInt(EntityList.length)];
                                    w.spawnEntity(LocationList.get(i), RandomMob);

                                }

                            }
                            break;
                        case 14:
                            plugin.Serv.broadcastMessage(ChatColor.DARK_RED + "The goblins have shuffled your inventory!");
                            for (Player p : w.getPlayers()) {

                                Inventory PlayerInventory = p.getInventory();
                                List<ItemStack> ItemList = Arrays.asList(PlayerInventory.getContents());
                                Collections.shuffle(ItemList);
                                ItemStack[] ShuffledList = ItemList.toArray(new ItemStack[0]);
                                PlayerInventory.setContents(ShuffledList);

                            }
                            break;
                        case 15:
                            if (rand.nextInt(9) == 8) {

                                plugin.Serv.broadcastMessage(ChatColor.RED + ":)");

                                for (Player p : w.getPlayers()) {
                                    // :)
                                    w.spawnEntity(p.getLocation(), EntityType.WARDEN);

                                }

                            }
                            plugin.Serv.broadcastMessage(ChatColor.AQUA + "Congrats! You guys got lucky, for now.");
                            break;

                    }

                    }

            }.runTaskTimer(plugin, 70 * 20L, 60 * 20L);

        } if (args[0].equalsIgnoreCase("stop")) {
            //code that manages the stop argument
            BukkitScheduler sched = plugin.Serv.getScheduler();
            sched.cancelTasks(plugin);
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "The disasters has stopped!");

        } if (args[0].equalsIgnoreCase("help")) {
            //code that manages the help argument
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "1. Nothing");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "2. No Eating");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "3. Spawns a Ravager");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "4. Spawns an Anvil 15 blocks above");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "5. Summons 10 Skeletons");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "6. Mining Fatigue is applied");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "7. Inventory Click Game");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "8. Removes Random item from Inventory");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "9. Nauseau is applied");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "10. Levitation is applied");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "11. Inability to attack");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "12. Chat Typing Game");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "13. All players are set ablaze");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "14. Random mob in each cardinal direction");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "15. Shuffles items in inventory");
            plugin.Serv.broadcastMessage(ChatColor.GREEN + "16. Nothing, sometimes :) ");

		}



        return false;
    }
}
