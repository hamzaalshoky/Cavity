package net.itshamza.cavity.sound;

import net.itshamza.cavity.Cavity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Cavity.MOD_ID);

    public static final RegistryObject<SoundEvent> FLESH_BLOCK_BREAK = registerSoundEvents("flesh_block_break");
    public static final RegistryObject<SoundEvent> FLESH_BLOCK_STEP = registerSoundEvents("flesh_block_step");



    public static final RegistryObject<SoundEvent> SMALL_IDLE = registerSoundEvents("small_idle");
    public static final RegistryObject<SoundEvent> SMALL_HURT = registerSoundEvents("small_hurt");
    public static final RegistryObject<SoundEvent> SMALL_DEATH = registerSoundEvents("small_death");

    public static final RegistryObject<SoundEvent> MEDIUM_IDLE = registerSoundEvents("medium_idle");
    public static final RegistryObject<SoundEvent> MEDIUM_HURT = registerSoundEvents("medium_hurt");
    public static final RegistryObject<SoundEvent> MEDIUM_DEATH = registerSoundEvents("medium_death");

    public static final RegistryObject<SoundEvent> LARGE_IDLE = registerSoundEvents("large_idle");
    public static final RegistryObject<SoundEvent> LARGE_HURT = registerSoundEvents("large_hurt");
    public static final RegistryObject<SoundEvent> LARGE_DEATH = registerSoundEvents("large_death");

    public static final ForgeSoundType FLESH_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.FLESH_BLOCK_BREAK, ModSounds.FLESH_BLOCK_STEP, ModSounds.FLESH_BLOCK_STEP,
            ModSounds.FLESH_BLOCK_BREAK, ModSounds.FLESH_BLOCK_STEP);


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(Cavity.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
