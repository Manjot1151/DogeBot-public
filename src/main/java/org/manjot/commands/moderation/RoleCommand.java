package org.manjot.commands.moderation;

import java.util.List;

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

public class RoleCommand extends Command implements CommandListener {

    public RoleCommand() {
        this.setName("role")
                // .setAliases()
                .setDescription("Add/Remove a role from a user")
                .setUsage("role <add/remove> <user> <role>")
                .setPermissions(Permission.MANAGE_ROLES)
                .setBotPermissions(Permission.MANAGE_ROLES)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length != 3 || !List.of("add", "remove", "give").contains(args[0])) {
            replyInvalidUsage(message);
            return;
        }
        Member roleMember = null;
        Role role = null;
        try {
            roleMember = Utils.getMentionedMember(guild, args[1]);
        } catch (Exception e) {
            Error.replyMessage(message, "Could not find that member!");
            return;
        }
        try {
            role = guild.getRoleById(Utils.parseId(args[2]));
        } catch (Exception e) {
            Error.replyMessage(message, "Could not find that role!");
            return;
        }
        if (roleMember == null) {
            Error.replyMessage(message, "Could not find that member!");
            return;
        }
        if (role == null) {
            Error.replyMessage(message, "Could not find that role!");
            return;
        }
        if (!member.canInteract(role)) {
            Error.replyMessage(message, "You cannot interact with that role!");
            return;
        }
        Member botMember = guild.getSelfMember();
        if (!botMember.canInteract(role)) {
            Error.replyMessage(message, "I cannot interact with that role!");
            return;
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        if (args[0].equals("add") || args[0].equals("give")) {
            if (roleMember.getRoles().contains(role)) {
                Error.replyMessage(message, roleMember.getAsMention() + " already has the role " + role.getAsMention());
                return;
            }
            embed.setTitle("Added role")
                    .setAuthor(roleMember.getUser().getName(), null, roleMember.getEffectiveAvatarUrl())
                    .setDescription(role.getAsMention());
            guild.addRoleToMember(roleMember, role).queue();
            ;
            message.replyEmbeds(embed.build()).queue();
            return;
        }
        if (args[0].equals("remove")) {
            if (!roleMember.getRoles().contains(role)) {
                Error.replyMessage(message,
                        roleMember.getAsMention() + " does not have the role " + role.getAsMention());
                return;
            }
            embed.setTitle("Removed role")
                    .setAuthor(roleMember.getUser().getName(), null, roleMember.getEffectiveAvatarUrl())
                    .setDescription(role.getAsMention());
            guild.removeRoleFromMember(roleMember, role).queue();
            message.replyEmbeds(embed.build()).queue();
            ;
            return;
        }
        Error.replyMessage(message, "Well, this was unexpected...");
    }
}