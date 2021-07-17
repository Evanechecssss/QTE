package top.evanechecssss.qte.command;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import top.evanechecssss.qte.util.RecordUtil;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSets extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("cs", "commsets");
    }

    @Override
    public String getName() {
        return "command_set";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command_set.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String name = args[0];
        Pattern p = Pattern.compile("\\|/[^\\|]+\\|", Pattern.MULTILINE);
        StringBuilder command = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            command.append(args[i]).append(" ");
        }
        Matcher matcher = p.matcher(command);
        List<String> list = Lists.newLinkedList();
        while (matcher.find()) {
            String f = command.substring(matcher.start(0), matcher.end(0));
            list.add(f);
        }

        try {
            RecordUtil.createCSFile(name, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
