package eu.mikart.worldborder.commands;

import eu.mikart.worldborder.Border;
import eu.mikart.worldborder.WorldBorderPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.william278.desertwell.about.AboutMenu;
import net.william278.desertwell.util.Version;
import net.william278.uniform.BaseCommand;
import net.william278.uniform.Command;

import java.util.List;
import java.util.Map;

import net.william278.uniform.CommandUser;
import net.william278.uniform.Permission;
import org.jetbrains.annotations.NotNull;

public class BorderCommand extends Command {

	public BorderCommand() {
		super("worldborder", List.of("border"), "WorldBorder command for managing world borders.", Permission.defaultTrue("worldborder.command.about"), ExecutionScope.ALL);
	}

	@Override
	public void provide(@NotNull BaseCommand<?> command) {
		command.setDefaultExecutor((ctx) -> {
			final CommandUser user = command.getUser(ctx.getSource());
			AboutMenu menu = AboutMenu.builder()
					.title(Component.text("WorldBorder"))
					.description(Component.text("A worldborder manipulation plugin"))
					.version(Version.fromString(WorldBorderPlugin.getInstance().getPluginMeta().getVersion()))
					.credits("Original Author", AboutMenu.Credit.of("Echo24h"))
					.credits("Maintainer & Rewriter", AboutMenu.Credit.of("ArikSquad"))
					.buttons(AboutMenu.Link.of("https://github.com/ArikSquad/WorldBorder/issues").text("Issues").icon("❌").color(TextColor.color(0xff9f0f)))
					.build();

			user.getAudience().sendMessage(menu.toComponent());
		});

		command.addSubCommand("reload", (sub) -> {
			sub.addSyntax((ctx) -> {
				final CommandUser user = command.getUser(ctx.getSource());
				WorldBorderPlugin.getInstance().reloadPlugin();
				user.getAudience().sendMessage(Component.text("WorldBorder configuration reloaded!", NamedTextColor.GREEN));
			});
			sub.setPermission(Permission.defaultIfOp("worldborder.command.reload"));
		});

		command.addSubCommand("list", (sub) -> {
			sub.addSyntax((ctx) -> {
				final CommandUser user = command.getUser(ctx.getSource());
				Map<String, Border> borders = WorldBorderPlugin.getInstance().getSettings().getBorders();
				if (borders.isEmpty()) {
					user.getAudience().sendMessage(Component.text("No borders are configured.", NamedTextColor.GRAY));
					return;
				}

				user.getAudience().sendMessage(Component.text("Configured Borders:", NamedTextColor.GREEN, TextDecoration.BOLD));
				borders.forEach((worldName, border) -> {
					String message = String.format("- %s: size=%d, center=(%d, %d)",
							worldName, border.borderSize(), border.centerX(), border.centerZ());
					user.getAudience().sendMessage(Component.text(message, NamedTextColor.YELLOW));
				});
			});
			sub.setPermission(Permission.defaultIfOp("worldborder.command.list"));
		});
	}
}
