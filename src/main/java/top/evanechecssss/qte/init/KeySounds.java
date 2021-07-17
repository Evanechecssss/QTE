package top.evanechecssss.qte.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import top.evanechecssss.qte.QTE;

public class KeySounds {
    public static final SoundEvent PRESS = Register.register(new ResourceLocation(QTE.MODID, "i"));


    public static class Register {

        private static SoundEvent register(ResourceLocation location) {
            return new SoundEvent(location).setRegistryName("i");
        }

        @SubscribeEvent
        public void register(RegistryEvent.Register<SoundEvent> e) {
            ForgeRegistries.SOUND_EVENTS.register(PRESS);
        }
    }
}
