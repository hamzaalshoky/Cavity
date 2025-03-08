package net.itshamza.cavity.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    public static final String CATEGORY = "key.categories.mymod";
    public static final KeyMapping CHARGE_KEY = new KeyMapping(
            "key.mymod.charge", // Localization key
            InputConstants.Type.KEYSYM, // Key type
            GLFW.GLFW_KEY_R, // Default key (R)
            CATEGORY // Category name
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CHARGE_KEY);
    }

    // Handle key press
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class KeyInputHandler {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (CHARGE_KEY.isDown()) {
                ClientModEvents.sendChargePacket();
            }
        }
    }

    // Send a packet to the server when the key is pressed
    private static void sendChargePacket() {
        PacketHandler.INSTANCE.sendToServer(new ChargePacket());
    }
}
