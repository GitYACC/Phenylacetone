package el.pablo;

import el.pablo.registry.Components;
import el.pablo.registry.Items;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PhenylacetoneClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
			if (!stack.isOf(Items.CIGARETTE)) return;

			int count = stack.getOrDefault(Components.USAGE_COUNT_COMPONENT, 0);
			lines.add(Text.translatable(
							"item.phenylacetone.cigarette.info", count)
					.formatted(Formatting.GRAY));
		});
	}
}