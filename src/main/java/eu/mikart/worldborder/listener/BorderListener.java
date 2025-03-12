package eu.mikart.worldborder.listener;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import eu.mikart.worldborder.WorldBorderPlugin;

@AllArgsConstructor
public class BorderListener implements Listener {
	private final WorldBorderPlugin plugin;

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.getSettings().isEnforce()) {
			WorldBorder border = event.getTo().getWorld().getWorldBorder();
			if (!border.isInside(event.getTo())) {
				event.getPlayer().teleportAsync(clampToBorder(event.getTo()));
				event.getPlayer().sendMessage(Component.text("You are unable to go that way.").color(NamedTextColor.RED));
			}
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (plugin.getSettings().isEnforce()) {
			WorldBorder border = event.getTo().getWorld().getWorldBorder();
			if (!border.isInside(event.getTo())) {
				event.setTo(clampToBorder(event.getTo()));
				event.getPlayer().sendMessage(Component.text("You are unable to go that way.").color(NamedTextColor.RED));
			}
		}
	}

	public Location clampToBorder(Location location) {
		WorldBorder border = location.getWorld().getWorldBorder();
		double borderSize = border.getSize();
		int centerX = border.getCenter().getBlockX();
		int centerZ = border.getCenter().getBlockZ();
		int x = location.getBlockX();
		int z = location.getBlockZ();

		Location clampedLoc = location.clone();

		if (x >= centerX + (borderSize / 2))
			clampedLoc.setX((borderSize / 2) - centerX - 1);

		if (x <= centerX - (borderSize / 2))
			clampedLoc.setX((-borderSize / 2) + centerX + 1);

		if (z >= centerZ + (borderSize / 2))
			clampedLoc.setZ((borderSize / 2) - centerZ - 1);

		if (z <= centerZ - (borderSize / 2))
			clampedLoc.setZ((-borderSize / 2) + centerZ + 1);

		return clampedLoc;
	}
}
