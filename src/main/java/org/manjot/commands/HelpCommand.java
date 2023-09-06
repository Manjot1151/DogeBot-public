package org.manjot.commands;

import java.util.EnumSet;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandHandler;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand extends Command implements CommandListener {

    public HelpCommand() {
        this.setName("help")
                .setAliases("h", "helpmenu", "helpme")
                .setDescription(
                        "Shows a list of all commands. Specify a command name to get command-specific help.\n<> : Required args\n() : Optional args")
                .setUsage("help (command)")
                .setType(CommandType.HELP);
    }

    private final String invite = "https://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=1512637328502&scope=bot%20applications.commands";
    private final String support = "https://discord.gg/W8hnfK5czn";
    private final String vote = "https://top.gg/bot/741412043411816488/vote";

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message,
            String prefix, String[] args) {
        if (args.length >= 1) {

            Command command = CommandHandler.getCommandByName(args[0]);
            if (command == null) {
                Error.replyMessage(message, "Command `" + args[0] + "` does not exist!");
                return;
            }

            if (command.getType() == CommandType.PRIVATE && !Utils.isDev(member)) {
                Error.replyMessage(message, "Command `" + args[0] + "` does not exist!");
                return;
            }

            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle(prefix + command.getName())
                    .addField("Aliases", String.join(", ", command.getAliases()), true)
                    .addField("Cooldown", Utils.formatFloat(command.getCooldown()) + "s", true)
                    .addField("Type", command.getType().toString(), true)
                    .addField("Usage", prefix + command.getUsage(), false)
                    .addField("Description", command.getDescription(), false)
                    .setFooter("Required: <> | Optional: ()");

            message.replyEmbeds(embed.build()).queue();
            return;
        }

        EmbedBuilder embed = Utils.getDefaultEmbed();

        embed.setTitle("Help Menu")
                .setThumbnail(jda.getSelfUser().getEffectiveAvatarUrl())
                .setDescription("Prefix: `" + prefix + "`\n`" + prefix + "help (command)` to get command-specific help")
                .setFooter("Made by " + Utils.me.getName(), Utils.me.getEffectiveAvatarUrl());

        EnumSet<CommandType> exclude = EnumSet.of(CommandType.HELP);
        if (!Utils.isDev(author))
            exclude.add(CommandType.PRIVATE);

        for (CommandType ct : EnumSet.complementOf(exclude)) {
            embed.addField(
                    ct.getEmoji() + " " + ct.toString() + " [" + ct.commandCount() + "]",
                    String.join(", ", ct.commandNames()),
                    false);
        }

        message.replyEmbeds(embed.build()).setActionRow(
                Button.link(invite, "Invite"),
                Button.link(support, "Support Server"),
                Button.link(vote, "Vote")).queue();
    }

}
