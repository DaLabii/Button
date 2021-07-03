package dalabi.button;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class Config {

	Plugin plugin;

	// MESSAGES

	private String buttonPressed;

	public String getButtonPressedMSG() {
		return buttonPressed;
	}

	private String noButtonFound;

	public String getNoButtonFoundMessage() {
		return noButtonFound;
	}

	private String noCommandPermission;

	public String getNoCommandPermissionMessage() {
		return noCommandPermission;
	}

	private String configReloaded;

	public String getConfigReloadedMessage() {
		return configReloaded;
	}

	private String invalidArguments;

	public String getInvalidArgsMessage() {
		return invalidArguments;
	}

	// PERMISSIONS

	private String commandPermission;

	public String getUsePermisison() {
		return commandPermission;
	}

	private String reloadPermission;

	public String getReloadPermission() {
		return reloadPermission;
	}

	// STORAGE

	private Map<UUID, Block> buttons = new HashMap<>();

	public Block getButtonBlock(UUID user) {
		return buttons.getOrDefault(user, null);
	}

	public void setButtonBlock(UUID user, Block block) {
		this.buttons.put(user, block);
	}

	public Config(Plugin plugin) {
		this.plugin = plugin;
	}

	public void reload() {
		plugin.reloadConfig();
		this.buttonPressed = plugin.getConfig().getString("Messages.Button_Pressed");
		this.noButtonFound = plugin.getConfig().getString("Messages.Nothing_Found");
		this.noCommandPermission = plugin.getConfig().getString("Messages.No_Command_Permission");
		this.configReloaded = plugin.getConfig().getString("Messages.Config_Reloaded");
		this.invalidArguments = plugin.getConfig().getString("Messages.Invalid_Arguments");
		
		this.commandPermission = plugin.getConfig().getString("Permissions.Command");
		this.reloadPermission = plugin.getConfig().getString("Permissions.Reload");
	}

}
