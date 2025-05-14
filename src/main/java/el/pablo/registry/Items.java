package el.pablo.registry;

import java.util.function.Function;

import el.pablo.Phenylacetone;
import el.pablo.registry.items.CigaretteItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Items {
    public static final Item CIGARETTE = register(
            "cigarette",
            CigaretteItem::new,
            new Item.Settings()
                    .component(Components.USAGE_COUNT_COMPONENT, 0)
    );

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Phenylacetone.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void initialize() {
        ItemGroupEvents
                .modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(Items.CIGARETTE));

        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(Items.CIGARETTE, 20 * 10);
        }));
    }
}
