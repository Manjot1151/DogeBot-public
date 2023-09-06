package org.manjot.commands.moderation;

import org.manjot.Error;
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
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CreateRoleCommand extends Command implements CommandListener {

    public CreateRoleCommand() {
        this.setName("createrole")
                .setAliases("rolecreate")
                .setDescription("Create a role")
                .setUsage("createrole (name)")
                .setPermissions(Permission.MANAGE_ROLES)
                .setBotPermissions(Permission.MANAGE_ROLES)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        String roleName = (args.length > 0) ? Utils.messageFrom(args, 0) : "new role";
        if (roleName.length() > 100) {
            Error.replyMessage(message, "Name of role cannot be longer than 100 characters!");
        }
        Role createdRole = guild.createRole().setName(roleName).complete();
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Created Role")
                .setDescription(createdRole.getAsMention());
        message.replyEmbeds(embed.build()).queue();

    }
}
