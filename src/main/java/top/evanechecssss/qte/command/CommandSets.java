package top.evanechecssss.qte.command;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import top.evanechecssss.qte.util.RecordUtil;

import java.io.FileNotFoundException;
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
        if (args.length == 0) {
            sender.sendMessage(new TextComponentTranslation(getUsage(sender)));
            return;
        }
        String name = args[0];
        if (args.length == 1) {
            sender.sendMessage(new TextComponentTranslation(getUsage(sender)));
            return;
        }
        if (args[1].equals("get")) {
            try {
                List<String> list = RecordUtil.parseCommands(name);
                list.forEach(s -> {
                    sender.sendMessage(new TextComponentString(s));
                });
                if (list.size() == 0) {
                    sender.sendMessage(new TextComponentTranslation("command_set.err"));
                }
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }
        Pattern p = Pattern.compile("\\|/[^\\|]+\\|", Pattern.MULTILINE);
        StringBuilder command = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            command.append(args[i]).append(" ");
        }
        Matcher matcher = p.matcher(command);
        List<String> list = Lists.newLinkedList();
        while (matcher.find()) {
            String f = command.substring(matcher.start(0), matcher.end(0));
            f = f.replace("|", "");
            list.add(f);
        }

        if (list.size() == 0) {
            sender.sendMessage(new TextComponentTranslation("command_set.notadd"));
            return;
        }
        sender.sendMessage(new TextComponentTranslation("command_set.add"));
        try {
            RecordUtil.removeCSFile(name);
            RecordUtil.createCSFile(name, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
