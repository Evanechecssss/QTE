package top.evanechecssss.qte.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import top.evanechecssss.qte.init.KeySounds;

import java.nio.charset.StandardCharsets;

public class MessageCommand implements IMessage {
    public MessageCommand() {
    }

    String command;
    int length;
    boolean play;

    public MessageCommand(String command, boolean play) {
        this.command = command;
        this.length = command.length();
        this.play = play;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.length = buf.readInt();
        this.command = (String) buf.readCharSequence(length, StandardCharsets.UTF_8);
        this.play = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(length);
        buf.writeCharSequence(command, StandardCharsets.UTF_8);
        buf.writeBoolean(play);
    }

    public static class MessageCommandHandler implements IMessageHandler<MessageCommand, IMessage> {

        public void executeCommand(String command, MinecraftServer server, boolean play) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (play) {
                player.playSound(KeySounds.PRESS, 1, 1);
            }
            server.getCommandManager().executeCommand(server, command);
        }

        @Override
        public IMessage onMessage(MessageCommand message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                executeCommand(message.command, ctx.getServerHandler().player.getServer(), message.play);
            });
            return null;
        }
    }
}
