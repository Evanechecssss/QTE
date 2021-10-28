package top.evanechecssss.qte.command;

import com.google.common.collect.Lists;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.command.TextComponentHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import top.evanechecssss.qte.QTE;
import top.evanechecssss.qte.network.MessageCommand;
import top.evanechecssss.qte.util.RecordUtil;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandQTE extends CommandBase {
    private static ArrayList<CommandQTE.KeyList> commandList = new ArrayList<>();
    boolean flag1 = true;
    boolean rel = true;

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("kc", "execute_k");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return "qte";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "qte.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 6) {
            sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, this.getUsage(sender)));
            return;
        }
        String setName = args[4];
        if (!RecordUtil.checkExist(setName)) {
            sender.sendMessage(new TextComponentString("Invalid name of set - " + setName));
        }
        try {
            int intCode = Integer.parseInt(args[0]);
            int tick = Integer.parseInt(args[1]);
            int delay = Integer.parseInt(args[2]);
            int clicks = Integer.parseInt(args[5]);
            boolean sound = args[3].equalsIgnoreCase("true");
            QTE.getLogger().warn(new KeyList(intCode, setName, tick, delay, sound, clicks).toString());
            addToList(new KeyList(intCode, setName, tick, delay, sound, clicks));
        } catch (NumberFormatException e) {
            sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, this.getUsage(sender)));
        }

    }

    public void addToList(KeyList keyList) {
        getCommandList().add(keyList);
    }

    public ArrayList<KeyList> getCommandList() {
        return commandList;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (getCommandList().size() == 0) return;
        for (Iterator<KeyList> iterator = getCommandList().iterator(); iterator.hasNext(); ) {
            KeyList list = iterator.next();
            if (list.getCode() == -100 || list.getCode() == -99 || list.getCode() == -98) {
                chekKeyMouse(list, iterator);
            } else {
                try {
                    chekKeyBoard(list, iterator);
                } catch (IndexOutOfBoundsException ignored) {
                    return;
                }
            }

        }

    }

    public void chekKeyMouse(KeyList list, Iterator<KeyList> iterator) {
        if ((Mouse.isButtonDown(0) && list.getCode() == -100) || (Mouse.isButtonDown(1) && list.getCode() == -99) || (Mouse.isButtonDown(2) && list.getCode() == -98)) {
            if (list.getStartTick() < list.getTick()) {
                if (list.getDelay() == 0) {
                    iterator.remove();
                    QTE.getNetwork().sendToServer(new MessageCommand(list.getCommand(), list.getPlaySound()));
                } else {
                    list.setDelay(list.getDelay() - 1);
                }
            }
        } else if (list.getStartTick() > list.getTick()) {
            iterator.remove();
        } else {
            list.setStartTick(list.getStartTick() + 1);
            list.setDelay(list.delayStart);
        }
    }

    public void chekKeyBoard(KeyList list, Iterator<KeyList> iterator) {

        if (Keyboard.isKeyDown(list.getCode())) {
            if (rel) {
                flag1 = true;
            }
            if (list.getStartTick() < list.getTick()) {
                if (list.getDelay() == 0 && list.getClicks() == 1) {
                    iterator.remove();
                    QTE.getNetwork().sendToServer(new MessageCommand(list.getCommand(), list.getPlaySound()));
                } else {
                    list.setDelay(list.getDelay() - 1);
                    if (flag1) {
                        list.setClicks(list.getClicks() - 1);
                        flag1 = false;
                        rel = false;
                    }
                }
            }

        } else if (list.getStartTick() > list.getTick()) {
            iterator.remove();
            rel = true;
        } else {
            list.setStartTick(list.getStartTick() + 1);
            list.setDelay(list.delayStart);
            rel = true;
        }
    }

    protected static class KeyList {
        protected int code;
        protected int tick;
        protected int delay;
        protected int delayStart;
        protected int startTick = 0;
        protected int clicks;
        protected String command;
        protected boolean sound;

        public KeyList(int code, String command, int tick, int delay, boolean sound, int clicks) {
            this.code = code;
            this.command = command;
            this.tick = tick;
            this.delay = delay;
            this.delayStart = delay;
            this.sound = sound;
            this.clicks = clicks;
        }

        public boolean getPlaySound() {
            return sound;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setStartTick(int startTick) {
            this.startTick = startTick;
        }

        public void setTick(int tick) {
            this.tick = tick;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getCommand() {
            return command;
        }

        public int getStartTick() {
            return startTick;
        }

        public int getCode() {
            return code;
        }

        public int getDelay() {
            return delay;
        }

        public int getClicks() {
            return clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
            if (this.clicks < 1) {
                this.clicks = 1;
            }
        }

        public int getTick() {
            return tick;
        }
    }
}
