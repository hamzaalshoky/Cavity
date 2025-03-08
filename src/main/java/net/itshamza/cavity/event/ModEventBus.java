package net.itshamza.cavity.event;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.custom.theoneandonly.RidableMonsterEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cavity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventBus {

    @SubscribeEvent
    public static void onPlayerDamaged(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getVehicle() instanceof RidableMonsterEntity ridableEntity) {
                event.setCanceled(true);
                ridableEntity.hurt(event.getSource(), event.getAmount());
            }
        }
    }
}
