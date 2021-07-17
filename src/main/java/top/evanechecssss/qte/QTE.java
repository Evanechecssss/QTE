package top.evanechecssss.qte;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import top.evanechecssss.qte.network.CommonProxy;

@Mod(modid = QTE.MODID, name = QTE.NAME, version = QTE.VERSION)
public class QTE {
    public static final String MODID = "qte", NAME = "QTE", VERSION = "1.0";
    private static Logger logger;
    @SidedProxy(serverSide = "top.evanechecssss.qte.network.CommonProxy", clientSide = "top.evanechecssss.qte.network.ClientProxy")
    private static CommonProxy proxy;
    private static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }
    @Mod.EventHandler
    public void server(FMLServerStartingEvent event) {
        proxy.server(event);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    public static Logger getLogger(){
        return logger;
    }
    public static SimpleNetworkWrapper getNetwork(){
        return network;
    }
}
