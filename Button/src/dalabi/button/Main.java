package dalabi.button;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dalabi.button.commands.FireCMD;
import dalabi.button.events.InteractEvent;

public class Main extends JavaPlugin {

	Config config;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		this.config = new Config(this);
		this.config.reload();
		getServer().getPluginManager().registerEvents(new InteractEvent(this.config), (Plugin) this);
		getCommand("fire").setExecutor((CommandExecutor) new FireCMD(this, this.config));
	}

}
