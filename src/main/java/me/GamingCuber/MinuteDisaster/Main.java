package me.GamingCuber.MinuteDisaster;

import me.GamingCuber.MinuteDisaster.Commands.MinuteDisasterCommand;
import me.GamingCuber.MinuteDisaster.Listeners.AttackListener;
import me.GamingCuber.MinuteDisaster.Listeners.ChatListener;
import me.GamingCuber.MinuteDisaster.Listeners.EatListener;
import me.GamingCuber.MinuteDisaster.Listeners.InventoryClickListener;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

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
//TODO 6 and 14
        new MinuteDisasterCommand(this);
        new AttackListener(this);
        new ChatListener(this);
        new EatListener(this);
        new InventoryClickListener(this);

    }

    @Override
    public void onDisable() {



    }

}
