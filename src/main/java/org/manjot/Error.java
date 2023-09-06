package org.manjot;

import java.util.stream.Collectors;

import org.manjot.commandhandler.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Error {
    public static void replyException(Message message, Exception e) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("An exception occured!")
                .setDescription(e.getMessage());
        message.replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public static void replyMessage(Message message, String e) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Error!")
                .setDescription(e);
        message.replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public static MessageEmbed exceptionEmbed(Exception e) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("An exception occured!")
                .setDescription(e.getMessage());
        return embed.build();
    }

    public static MessageEmbed messageEmbed(String e) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Error!")
                .setDescription(e);
        return embed.build();
    }

    public static void replyMissingPermissions(Message message, Command command) {
        Member member = message.getMember();
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Missing Permissions!")
                .setDescription("You are missing permissions: ")
                .appendDescription(
                        command.getPermissions().stream()
                                .filter(p -> !member.hasPermission(p))
                                .map(p -> "`" + p.getName() + "`")
                                .collect(Collectors.joining(", ")));
        message.replyEmbeds(embed.build()).queue();
    }

    public static void replyBotMissingPermissions(Message message, Command command) {
        Member self = message.getGuild().getSelfMember();
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Missing Permissions!")
                .setDescription("I am missing permissions: ")
                .appendDescription(
                        command.getBotPermissions().stream()
                                .filter(p -> !self.hasPermission(p))
                                .map(p -> "`" + p.getName() + "`")
                                .collect(Collectors.joining(", ")));
        message.replyEmbeds(embed.build()).queue();
    }

    public static void replyCommandCooldown(Message message, float wait) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Slow down!")
                .setDescription(String.format("Wait `%.1fs` before using that command again.", wait));
        message.replyEmbeds(embed.build()).queue();
    }

    public static void replyGlobalCooldown(Message message) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Slow down!")
                .setDescription("You are using commands too fast.");
        message.replyEmbeds(embed.build()).queue();
    }
}
