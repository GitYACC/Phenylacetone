package el.pablo.registry;

import com.mojang.serialization.Codec;
import el.pablo.Phenylacetone;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Components {
    public static void initialize() {
        Phenylacetone.LOGGER.info("Registering {} components", Phenylacetone.MOD_ID);
    }

    public static final ComponentType<Integer> USAGE_COUNT_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Phenylacetone.MOD_ID, "usage_count"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
}
