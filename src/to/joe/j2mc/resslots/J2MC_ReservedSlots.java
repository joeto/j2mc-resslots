package to.joe.j2mc.resslots;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.core.J2MC_Manager;

public class J2MC_ReservedSlots extends JavaPlugin implements Listener {

    @Override
    public void onDisable() {
        this.getLogger().info("Reserved slots module disabled");
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info("Reserved slots module enabled");
    }

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPreLogin(PlayerLoginEvent event) {
		boolean isAdmin = J2MC_Manager.getPermissions().isAdmin(event.getPlayer().getName());
		boolean isDonator = J2MC_Manager.getPermissions().hasFlag(event.getPlayer().getName(), 'd');
		if(this.getServer().getOnlinePlayers().length >= this.getServer().getMaxPlayers()){
			if(!isAdmin && !isDonator){
				event.disallow(Result.KICK_FULL, "Server full! For a reserved slot see donate.joe.to");
			}else{
			    event.setResult(Result.ALLOWED);
				event.allow();
				this.getLogger().info("Allowed player " + event.getPlayer().getName() + " through");
			}
		}
	}
}
