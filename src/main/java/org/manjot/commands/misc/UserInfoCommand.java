package org.manjot.commands.misc;

import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.stream.Collectors;

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
import net.dv8tion.jda.api.entities.User.UserFlag;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UserInfoCommand extends Command implements CommandListener {
    public UserInfoCommand() {
        this.setName("userinfo")
                .setAliases("ui", "memberinfo", "whois")
                .setDescription("Get info about a user")
                .setUsage("userinfo (user)")
                .setType(CommandType.MISCELLANEOUS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        String userroles = "";
        Member m;
        m = args.length == 0 ? member : Utils.getMentionedMemberOrDefault(guild, args[0], member);
        embed.setThumbnail(m.getEffectiveAvatarUrl());
        embed.setDescription(m.getAsMention());
        embed.setAuthor(m.getUser().getName(), null, m.getEffectiveAvatarUrl());
        embed.setFooter(guild.getName(), guild.getIconUrl());
        for (int i = 0; i < m.getRoles().size(); i++) {
            userroles += m.getRoles().get(i).getAsMention() + " ";
        }

        String status = m.getOnlineStatus().toString();

        switch (status) {
            case "ONLINE":
                status = "<:online:841667691504336896> Online";
                break;

            case "OFFLINE":
                status = "<:offline:841667625499230229> Offline";
                break;

            case "DO_NOT_DISTURB":
                status = "<:dnd:841667625549561887> Do Not Disturb";
                break;

            case "IDLE":
                status = "<:idle:841667625335521292> Idle";
                break;
        }

        String perms = m.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.joining(", "));

        String badgeString = createBadgeList(m.getUser().getFlags());

        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        embed.addField("Joined", m.getTimeJoined().format(formatter), true);
        embed.addField("Registered", m.getTimeCreated().format(formatter), true);
        embed.addField("Status", status, false);
        embed.addField("Roles [" + m.getRoles().size() + "]", userroles, false);
        embed.addField("Permissions", perms, false);
        embed.addField("Badges [" + m.getUser().getFlags().size() + "]", badgeString, false);
        message.replyEmbeds(embed.build()).queue();
    }

    public static String createBadgeList(EnumSet<User.UserFlag> badges) {
        Iterator<UserFlag> badgeIterator = badges.iterator();
        String badgeString = "";
        while (badgeIterator.hasNext()) {
            String badge = badgeIterator.next().toString();
            switch (badge) {
                case "BOT_HTTP_INTERACTIONS":
                    badgeString += ":globe_with_meridians: | Bot HTTP Interactions";
                    break;

                case "BUG_HUNTER_LEVEL_1":
                    badgeString += "<:discordbughunterlv1:850047764816986152> | Bug Hunter Level 1";
                    break;

                case "BUG_HUNTER_LEVEL_2":
                    badgeString += "<:discordbughunterlv2:850047764649869382> | Bug Hunter Level 2";
                    break;

                case "CERTIFIED_MODERATOR":
                    badgeString += "<:certified_moderator:850106245306384437> | Certified Moderator";
                    break;

                case "EARLY_SUPPORTER":
                    badgeString += "<:discordearlysupporter:850047764393623632> | Early Supporter";
                    break;

                case "HYPESQUAD":
                    badgeString += "<:hypesquad:850109052432416789> | Hypesquad";
                    break;

                case "HYPESQUAD_BALANCE":
                    badgeString += "<:discordbalance:850047764712652820> | Hypesquad Balance";
                    break;

                case "HYPESQUAD_BRAVERY":
                    badgeString += "<:discordbravery:850047763889651753> | Hypesquad Bravery";
                    break;

                case "HYPESQUAD_BRILLIANCE":
                    badgeString += "<:discordbrillance:850047764721041428> | Hypesquad Brilliance";
                    break;

                case "PARTNER":
                    badgeString += "<:discordpartner:850047764644888616> | Partner";
                    break;

                case "STAFF":
                    badgeString += "<:discord_staff:850047765093810256> | Discord Staff";
                    break;

                case "SYSTEM":
                    badgeString += "<:system_badge:850047764481310760> | System";
                    break;

                case "TEAM_USER":
                    badgeString += "Team User | IF SOMEONE HAS THIS BADGE PLEASE DM Manjot1151#8472";
                    break;

                case "UNKNOWN":
                    badgeString += "Unknown | IF SOMEONE HAS THIS BADGE PLEASE DM Manjot1151#8472";
                    break;

                case "VERIFIED_BOT":
                    badgeString += "<:verified_bot_fake:850112293135450142> | Verified Bot";
                    break;

                case "VERIFIED_DEVELOPER":
                    badgeString += "<:verified_bot_developer:850104836879024129> | Verified Bot Developer";
                    break;
                    
                case "ACTIVE_DEVELOPER":
                    badgeString += "<:active_developer:1119263037577236602> | Active Developer";
                    break;

                default:
                    badgeString += badge.toString();
                    break;
            }
            badgeString += "\n";
        }
        return badgeString;
    }
}
