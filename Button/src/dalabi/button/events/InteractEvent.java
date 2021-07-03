package dalabi.button.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import dalabi.button.Config;

public class InteractEvent implements Listener {

	Config config;

	public InteractEvent(Config config) {
		this.config = config;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		Material clicked = event.getClickedBlock().getType();
		if (clicked != Material.STONE_BUTTON && clicked != Material.WOOD_BUTTON) {
			return;
		}
		config.setButtonBlock(event.getPlayer().getUniqueId(), event.getClickedBlock());
	}
}
