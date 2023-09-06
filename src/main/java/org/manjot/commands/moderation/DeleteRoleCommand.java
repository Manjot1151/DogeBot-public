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

public class DeleteRoleCommand extends Command implements CommandListener {

    public DeleteRoleCommand() {
        this.setName("deleterole")
                .setAliases("roledelete", "delrole", "roledel")
                .setDescription("Delete a role")
                .setUsage("deleterole <role>")
                .setPermissions(Permission.MANAGE_ROLES)
                .setBotPermissions(Permission.MANAGE_ROLES)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }

        EmbedBuilder embed = Utils.getDefaultEmbed();
        Role role;
        try {
            role = guild.getRoleById(Utils.parseId(args[0]));
            if (role != null) {
                if (!member.canInteract(role)) {
                    Error.replyMessage(message, "You cannot interact with that role!");
                    return;
                }
                if (!guild.getSelfMember().canInteract(role)) {
                    Error.replyMessage(message, "I cannot interact with that role!");
                    return;
                }
                embed.setTitle("Role deleted")
                        .setDescription("@" + role.getName() + " (" + role.getId() + ")");
                role.delete().queue();
                message.replyEmbeds(embed.build()).queue();
                return;
            }
        } catch (Exception e) {
            replyInvalidUsage(message);
            return;
        }
        Error.replyMessage(message, "Could not find that role");
    }
}