package dalabi.button.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Button;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import dalabi.button.Config;
import net.md_5.bungee.api.ChatColor;

public class FireCMD implements CommandExecutor {

	Plugin plugin;
	Config config;

	public FireCMD(Plugin plugin, Config config) {
		this.plugin = plugin;
		this.config = config;
	}

	public void sendMSG(Player player, String msg) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	@SuppressWarnings("deprecation")
	public void buttonScheduler(Block block, Button button, int ticks) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				int data = block.getData();
				data = data & ~0x8;
				block.setData((byte) data);
				updateButtonState(block, button);
			}
		}, ticks);
	}

	@SuppressWarnings("deprecation")
	public void buttonUpdate(Block block) {
		int data = block.getData();
		data = data | 0x8;
		block.setData((byte) data);
	}

	public void updateButtonState(Block block, Button button) {
		Block supportBlock = block.getRelative(button.getAttachedFace());
		BlockState initialSupportState = supportBlock.getState();
		BlockState supportState = supportBlock.getState();
		supportState.setType(Material.AIR);
		supportState.update(true, false);
		initialSupportState.update(true);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "reload":
					if (!player.hasPermission(config.getReloadPermission())) {
						sendMSG(player, config.getNoCommandPermissionMessage());
						return false;
					}
					config.reload();
					sendMSG(player, config.getConfigReloadedMessage());
					break;
				default:
					sendMSG(player, config.getInvalidArgsMessage());
				}
			} else {
				if (!player.hasPermission(config.getUsePermisison())) {
					sendMSG(player, config.getNoCommandPermissionMessage());
					return false;
				}
				if (config.getButtonBlock(player.getUniqueId()) == null) {
					sendMSG(player, config.getNoButtonFoundMessage());
					return false;
				}
				Block block = config.getButtonBlock(player.getUniqueId());
				if (block.getType() == Material.STONE_BUTTON) {
					Button button = (Button) block.getState().getData();
					buttonUpdate(block);
					updateButtonState(block, button);
					buttonScheduler(block, button, 21);
					sendMSG(player, config.getButtonPressedMSG().replace("%x%", String.valueOf(block.getX())).replace("%y%", String.valueOf(block.getY()))
							.replace("%z%", String.valueOf(block.getZ())));
					return false;
				}
				if (block.getType() == Material.WOOD_BUTTON) {
					Button button = (Button) block.getState().getData();
					buttonUpdate(block);
					updateButtonState(block, button);
					buttonScheduler(block, button, 31);
					sendMSG(player, config.getButtonPressedMSG().replace("%x%", String.valueOf(block.getX())).replace("%y%", String.valueOf(block.getY()))
							.replace("%z%", String.valueOf(block.getZ())));
					return false;
				}
				sendMSG(player, config.getNoButtonFoundMessage());
			}
			return false;
		}
		return true;
	}
}
