package org.manjot.commands.music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.GuildMusicManager;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class QueueCommand extends Command implements CommandListener {

    public QueueCommand() {
        this.setName("queue")
                .setAliases("q", "musicqueue", "songqueue")
                .setDescription("Get a list of queued songs")
                .setUsage("queue (clear)")
                .setType(CommandType.MUSIC);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        Member self = guild.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        if (!memberVoiceState.inAudioChannel()) {
            Error.replyMessage(message, "You need to be in a voice channel!");
            return;
        }
        if (memberVoiceState.isDeafened()) {
            Error.replyMessage(message, "You need to be undeafened to use that command");
            return;
        }
        if (!selfVoiceState.inAudioChannel()) {
            Error.replyMessage(message, "I am not in a voice channel!");
            return;
        } else if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            Error.replyMessage(message, "You need to be in the same voice channel as me!");
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (args.length >= 1 && args[0].equalsIgnoreCase("clear")) {
            if (queue.size() == 0) {
                Error.replyMessage(message, "Queue is already empty!");
                return;
            }
            queue.clear();
            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle("Cleared queue!");
            message.replyEmbeds(embed.build()).queue();
            return;
        }

        final AudioTrack currentTrack = musicManager.audioPlayer.getPlayingTrack();
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        if (currentTrack != null)
            trackList.add(0, musicManager.audioPlayer.getPlayingTrack());
        final int trackCount = Math.min(trackList.size(), 20);
        if (trackList.isEmpty()) {
            Error.replyMessage(message, "The queue is currently empty!");
            return;
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Queue [" + trackList.size() + "]");
        for (int i = 0; i < trackCount; i++) {
            final AudioTrackInfo info = trackList.get(i).getInfo();
            embed.appendDescription(
                    "[" + info.title + "](" + info.uri + ") - `"
                            + (info.isStream ? "🔴 LIVE" : Utils.formatMilli(info.length)) + "`\n");
        }
        if (trackList.size() > trackCount)
            embed.appendDescription("And `" + (trackList.size() - trackCount) + "` more...");
        message.replyEmbeds(embed.build()).queue();
    }
}
