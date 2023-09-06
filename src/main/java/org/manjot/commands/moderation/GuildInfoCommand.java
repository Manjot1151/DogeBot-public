package org.manjot.commands.moderation;

import java.time.format.DateTimeFormatter;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GuildInfoCommand extends Command implements CommandListener {

    public GuildInfoCommand() {
        this.setName("guildinfo")
                .setAliases("ginfo")
                .setDescription("Get info about your guild")
                .setUsage("guildinfo")
                .setPermissions(Permission.MANAGE_SERVER)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        Guild g = guild;
        if (Utils.isDev(author)) {
            try {
                g = jda.getGuildById(args[0]);
            } catch (Exception e) {
            }
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle(g.getName());
        embed.setThumbnail(g.getIconUrl());
        embed.addField("ID", g.getId(), true);
        embed.addField("Owner", g.retrieveOwner().complete().getUser().getName(), true);
        embed.addField("Members", Integer.toString(g.getMemberCount()), true);
        embed.addField("Channels", Integer.toString(g.getChannels().size()), true);
        embed.addField("Roles", Integer.toString(g.getRoles().size()), true);
        embed.addField("Emotes", Integer.toString(g.getEmojis().size()), true);
        embed.addField("Boosts", Integer.toString(g.getBoostCount()), true);
        embed.addField("Created At", g.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
        message.replyEmbeds(embed.build()).queue();
    }
}
