package org.manjot.commands.misc;

import org.manjot.Main;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.GuildMusicManager;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InfoCommand extends Command implements CommandListener {
    public InfoCommand() {
        this.setName("info")
                .setAliases("dashboard", "bot", "botinfo")
                .setDescription("Get info about bot")
                .setUsage("info")
                .setType(CommandType.MISCELLANEOUS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        jda.getRestPing().queue(restPing -> {
            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle("Bot Info")
                    .addField(":ping_pong: Ping",
                            "**Bot**: " + restPing + "ms\n**API**: " + jda.getGatewayPing() + "ms", true)
                    .addField(":hourglass: Uptime", Utils.formatMilli(System.currentTimeMillis() - Main.start), true)
                    .addField(":satellite: Servers", String.valueOf(jda.getGuilds().size()), true)
                    .addField(":musical_note: Music", getPlayerCount() + " Guilds", true)
                    .addField(":busts_in_silhouette: UserCache", String.valueOf(jda.getUserCache().size()), true)
                    .addField(":cd: Memory", getMemoryUsage(), true)
                    .setFooter("Made by " + Utils.me.getName(), Utils.me.getEffectiveAvatarUrl());
            message.replyEmbeds(embed.build()).queue();
        });
    }

    private static int getPlayerCount() {
        int count = 0;
        for (GuildMusicManager manager : PlayerManager.getInstance().musicManagers.values()) {
            if (manager.audioPlayer.getPlayingTrack() != null)
                count++;
        }
        return count;
    }

    private static String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        float percentage = (float) (usedMemory * 100) / totalMemory;
        return String.format("%.2f%% (%dMB/%dMB)", percentage, usedMemory / (1024 * 1024), totalMemory / (1024 * 1024));
    }
}
