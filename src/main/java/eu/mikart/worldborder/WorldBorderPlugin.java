package eu.mikart.worldborder;

import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import net.william278.uniform.paper.PaperUniform;
import org.bukkit.plugin.java.JavaPlugin;
import eu.mikart.worldborder.commands.BorderCommand;
import eu.mikart.worldborder.config.Settings;
import eu.mikart.worldborder.listener.BorderListener;


@Getter
public final class WorldBorderPlugin extends JavaPlugin {
	@Getter
	private static WorldBorderPlugin instance;
	private BordersManager manager;
	private Settings settings;

	@Override
	public void onEnable() {
		instance = this;
		manager = new BordersManager(this);
		settings = YamlConfigurations.update(getDataPath().resolve("config.yml"), Settings.class);
		manager.createAllBorders();

		PaperUniform uniform = PaperUniform.getInstance(this);
		uniform.register(new BorderCommand());

		getServer().getPluginManager().registerEvents(new BorderListener(this), this);
	}

	public void reloadPlugin() {
		manager.removeAllBorders();
		settings = YamlConfigurations.update(getDataPath().resolve("config.yml"), Settings.class);
		manager.createAllBorders();
	}

}
