package net.itshamza.cavity.event;

import net.itshamza.cavity.entity.custom.theoneandonly.RidableMonsterEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ChargePacket {
    public ChargePacket() {}

    public static void encode(ChargePacket msg, FriendlyByteBuf buffer) {}

    public static ChargePacket decode(FriendlyByteBuf buffer) {
        return new ChargePacket();
    }

    public static void handle(ChargePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() != null && ctx.get().getSender().getVehicle() instanceof RidableMonsterEntity) {
                RidableMonsterEntity monster = (RidableMonsterEntity) ctx.get().getSender().getVehicle();
                monster.startCharging();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
