package el.pablo.registry.items;

import el.pablo.registry.Components;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import java.util.List;

public class CigaretteItem extends Item {
    // increments in ticks (20 ticks = 1 second)
    private static final int HASTE_INC = 20 * 2; // +2s each puff
    private static final int NAUSEA_INC = 20; // +1s each puff

    public CigaretteItem(Settings settings) {
        super(settings);
    }

    /**
     * Callback function when the player right-clicks the CIGARETTE
     */
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int use_count = stack.getOrDefault(Components.USAGE_COUNT_COMPONENT, 0);

        // only run server side to not allow desync between client and server
        if (!world.isClient) {
            extendEffect(user, StatusEffects.HASTE, HASTE_INC);
            extendEffect(user, StatusEffects.NAUSEA, NAUSEA_INC);

            world.playSound(
                    null,
                    user.getX(), user.getY(), user.getZ(),
                    SoundEvents.BLOCK_CAMPFIRE_CRACKLE,
                    SoundCategory.PLAYERS,
                    1.0F, 1.0F
            );

            stack.set(Components.USAGE_COUNT_COMPONENT, ++use_count);

            // Decrement stack if in survival
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        // CONSUME prevents hand swinging motion
        return ActionResult.CONSUME;
    }

    /**
     * Adds {@code extra} ticks onto any existing effect of the same type.
     */
    private static void extendEffect(PlayerEntity player,
                                     RegistryEntry<StatusEffect> type,
                                     int extra) {

        StatusEffectInstance current = player.getStatusEffect(type);
        int newDuration = extra + (current != null ? current.getDuration() : 0);
        int amplifier = current != null ? current.getAmplifier() : 0;

        // This replaces the effect only if the new duration is longer (Fabric/vanilla merging rules)
        player.addStatusEffect(new StatusEffectInstance(type, newDuration, amplifier,
                false, false, true));
    }
}
