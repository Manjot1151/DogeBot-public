package org.manjot.commands.moderation;

import java.util.ArrayList;
import java.util.List;

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
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RolesCommand extends Command implements CommandListener {

    public RolesCommand() {
        this.setName("roles")
                .setAliases("rolelist", "serverroles", "guildroles", "serverrolelist", "guildrolelist")
                .setDescription("Get a list of all roles in the server")
                .setUsage("roles")
                .setPermissions(Permission.MANAGE_ROLES)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        Guild server = args.length == 0 ? guild : (!Utils.isDev(author)) ? guild : jda.getGuildById(args[0]);
        List<Role> roles = server.getRoles();
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Roles [" + roles.size() + "]");
        embed.setFooter(server.getName(), server.getIconUrl());
        embed.setThumbnail(server.getIconUrl());
        List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();

        for (int i = 0; i < roles.size(); i++) {
            try {
                embed.appendDescription(roles.get(i).getAsMention() + " (" + roles.get(i).getId() + ")\n");
            } catch (Exception e) {
                embeds.add(embed.build());
                embed.setDescription("");
            }
        }
        embeds.add(embed.build());
        message.replyEmbeds(embeds).queue();
    }
}
