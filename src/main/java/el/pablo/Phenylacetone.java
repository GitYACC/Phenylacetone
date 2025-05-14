package el.pablo;

import el.pablo.registry.Components;
import el.pablo.registry.Items;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Phenylacetone implements ModInitializer {
	public static final String MOD_ID = "phenylacetone";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Heisenberg");
		Items.initialize();
		Components.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}