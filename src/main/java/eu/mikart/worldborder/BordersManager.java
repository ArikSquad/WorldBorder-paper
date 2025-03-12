package eu.mikart.worldborder;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Map;

@AllArgsConstructor
public class BordersManager {
	private final WorldBorderPlugin plugin;

	public void createAllBorders() {
		for (Map.Entry<String, Border> entry : plugin.getSettings().getBorders().entrySet()) {
			createBorder(entry.getKey(), entry.getValue());
		}
	}

	public void removeAllBorders() {
		for (Map.Entry<String, Border> entry : plugin.getSettings().getBorders().entrySet()) {
			removeBorder(entry.getKey());
		}
	}

	private void removeBorder(String worldName) {
		World world = Bukkit.getWorld(worldName);
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			if (world != null) {
				org.bukkit.WorldBorder worldBorder = world.getWorldBorder();
				worldBorder.reset();
			}
		});
	}

	private void createBorder(String worldName, Border border) {
		World world = Bukkit.getWorld(worldName);
		if (world != null) {
			int borderSize = border.borderSize();
			int centerX = border.centerX();
			int centerZ = border.centerZ();

			org.bukkit.WorldBorder worldBorder = world.getWorldBorder();
			plugin.getServer().getScheduler().runTask(plugin, () -> {
				worldBorder.setSize(borderSize);
				worldBorder.setCenter(centerX, centerZ);
			});
		} else {
			plugin.getServer().getConsoleSender().sendMessage(
					MiniMessage.miniMessage().deserialize("<red>[WorldBorder] ERROR: Unable to create world border <yellow>" + worldName + "<red> because this world does not exist")
			);
		}
	}
}
