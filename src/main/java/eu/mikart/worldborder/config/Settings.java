package eu.mikart.worldborder.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import eu.mikart.worldborder.Border;

import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@SuppressWarnings("FieldMayBeFinal")
public class Settings {

	@Comment("Whether to enforce")
	private boolean enforce = false;

	@Comment("World borders configuration")
	private Map<String, Border> borders = new HashMap<>() {{
		put("world", new Border(1000, 0, 0));
		put("world_the_end", new Border(2000, 100, -200));
	}};

}