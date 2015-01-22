package net.awesomepowered.stop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by John on 1/21/2015.
 */
public class StopTimer extends BukkitRunnable {

    private int stopTime;
    private Stop plugin;

    public StopTimer(Stop plugin,int time) {
        this.stopTime = time*60;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.stopTime--;
        if (this.stopTime == 30) {
            Bukkit.broadcastMessage(ChatColor.RED + "Stop command will be enabled in 30 seconds!");
        }
        if (this.stopTime == 0) {
            Bukkit.broadcastMessage(ChatColor.RED + "You may now stop the server!");
            plugin.enableStop();
        }
    }
}
