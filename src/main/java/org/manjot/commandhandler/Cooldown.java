package org.manjot.commandhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.manjot.Utils;
import org.manjot.Error;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Cooldown {
    private static Map<Long, Map<String, Long>> cooldowned = new HashMap<>();

    static boolean canRun(User user, Command command, Message message) {
        long currentCall = System.currentTimeMillis();

        if (command.getCooldown() <= 0)
            return true;

        if (!cooldowned.containsKey(user.getIdLong())) {
            cooldowned.put(user.getIdLong(), new HashMap<>());
        }

        Map<String, Long> commandCalls = cooldowned.get(user.getIdLong());
        if (!commandCalls.containsKey(command.getName())) {
            commandCalls.put(command.getName(), System.currentTimeMillis());
            return true;
        }

        long lastCall = commandCalls.get(command.getName());
        float wait = (float) (lastCall - currentCall + command.getCooldown() * 1000) / 1000;
        if (wait > 0) {
            Error.replyCommandCooldown(message, wait);
            return false;
        }

        commandCalls.put(command.getName(), currentCall);
        return true;
    }

    private static float globalCooldown = 0.5f;
    private static Map<Long, Long> globalCooldowned = new HashMap<Long, Long>();

    static boolean canRunGlobal(User user, Message message) {
        long currentCall = System.currentTimeMillis();
        if (!globalCooldowned.containsKey(user.getIdLong())) {
            globalCooldowned.put(user.getIdLong(), currentCall);
            return true;
        }

        long lastCall = globalCooldowned.get(user.getIdLong());
        if (lastCall - currentCall + globalCooldown * 1000 > 0) {
            Error.replyGlobalCooldown(message);
            return false;
        }
        globalCooldowned.put(user.getIdLong(), currentCall);
        return true;
    }

    public static void startPurger() {
        Utils.exec.scheduleWithFixedDelay(() -> {
            cooldowned.clear();
            globalCooldowned.clear();
        }, 30, 30, TimeUnit.MINUTES);
    }
}
