package top.evanechecssss.qte.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

/**
 * \* User: Evanechecssss
 * \* https://bio.link/evanechecssss
 * \* Data: 26.10.2021
 * \* Description:
 * \
 */
public class KeyCodes extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getName() {
        return "key_codes";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(ITextComponent.Serializer.jsonToComponent("{\"text\":\"KEY CODES\",\"bold\":true,\"color\":\"light_purple\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://minecraft.fandom.com/el/wiki/Key_codes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Link to site with key codes\"}}"));
    }


}