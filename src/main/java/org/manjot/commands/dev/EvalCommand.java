package org.manjot.commands.dev;

import javax.script.ScriptEngine;

import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandHandler;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EvalCommand extends Command implements CommandListener {

    public EvalCommand() {
        this.setName("eval")
                .setAliases("evaluate")
                .setDescription("Evaluate code")
                .setUsage("eval <code>")
                .setType(CommandType.PRIVATE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }

        ScriptEngine engine = new GroovyScriptEngineImpl();
        engine.put("jda", jda);
        engine.put("event", event);
        engine.put("author", author);
        engine.put("member", member);
        engine.put("guild", guild);
        engine.put("channel", channel);
        engine.put("message", message);
        engine.put("prefix", prefix);
        engine.put("args", args);
        engine.put("commands", CommandHandler.commandMap);

        String imports = createImports(
                "net.dv8tion.jda.api.*",
                "net.dv8tion.jda.api.entities.*",
                "org.manjot.*",
                "org.manjot.commandhandler.*",
                "org.manjot.commands.music.lavaplayer.*");

        try {
            engine.eval(imports + "\n" + Utils.messageFrom(args, 0));
            message.addReaction(Emoji.fromFormatted("✅")).queue();
        } catch (Exception e) {
            Error.replyMessage(message, e.getMessage().replace("javax.script.ScriptException: groovy.lang.", ""));
        }
    }

    public static String createImports(String... pkgs) {
        StringBuilder imports = new StringBuilder();
        for (String pkg : pkgs) {
            imports.append("import " + pkg + ";");
        }
        return imports.toString();
    }
}
