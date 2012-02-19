package to.joe.j2mc.resslots;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.core.J2MC_Manager;

public class J2MC_ReservedSlots extends JavaPlugin implements Listener {

    private int softLimit;

    @Override
    public void onDisable() {
        this.getLogger().info("Reserved slots module disabled");
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.softLimit = this.getConfig().getInt("softlimit", 30);
        this.getLogger().info("Reserved slots module enabled");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        if (this.getServer().getOnlinePlayers().length >= this.softLimit) {
            final boolean isAdmin = J2MC_Manager.getPermissions().isAdmin(event.getName());
            final boolean isDonator = J2MC_Manager.getPermissions().hasFlag(event.getName(), 'd');
            if (!isAdmin && !isDonator) {
                event.disallow(Result.KICK_OTHER, "Server full! For a reserved slot see donate.joe.to");
            }
        }
    }
}
