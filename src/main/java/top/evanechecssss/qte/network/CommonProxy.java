package top.evanechecssss.qte.network;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import top.evanechecssss.qte.QTE;
import top.evanechecssss.qte.command.CommandQTE;
import top.evanechecssss.qte.command.CommandSets;
import top.evanechecssss.qte.command.KeyCodes;
import top.evanechecssss.qte.init.KeySounds;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new CommandQTE());
        MinecraftForge.EVENT_BUS.register(new KeySounds.Register());
        QTE.getNetwork().registerMessage(MessageCommand.MessageCommandHandler.class, MessageCommand.class, 0, Side.SERVER);
    }


    public void init(FMLInitializationEvent event)
    {

    }
    public void server(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandQTE());
        event.registerServerCommand(new KeyCodes());
        event.registerServerCommand(new CommandSets());
    }
}
