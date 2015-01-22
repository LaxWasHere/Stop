package net.awesomepowered.stop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by John on 1/21/2015.
 */
public class Stop extends JavaPlugin implements Listener {

    public static Stop instance;
    public boolean isGo;

    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        isGo = false;
        new StopTimer(this, getConfig().getInt("StopTime")).runTaskTimer(this, 20, 20);
    }

    public void onCommand(PlayerCommandPreprocessEvent ev) {
        if (ev.getMessage().toLowerCase().startsWith("/stop") && !isGo) {
            ev.getPlayer().sendMessage(ChatColor.RED + "The server cannot be stopped yet!");
            ev.setCancelled(true);
            return;
        }
        isGo = false;
        doRealStop(ev.getPlayer());
    }

    public void doRealStop(Player p) {
        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + "won the stop contests!");
        Bukkit.broadcastMessage(ChatColor.RED + "Server restarting.");
        for (String cmds : getConfig().getStringList("Commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds.replace("%PLAYER%", p.getName()));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(this, 20);
    }

    public void enableStop() {
        isGo = true;
    }

}
