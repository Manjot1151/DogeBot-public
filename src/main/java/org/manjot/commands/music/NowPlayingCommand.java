package org.manjot.commands.music;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.GuildMusicManager;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
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

public class NowPlayingCommand extends Command implements CommandListener {
    public NowPlayingCommand() {
        this.setName("nowplaying")
                .setAliases("np")
                .setDescription("Get the music currently playing")
                .setUsage("nowplaying")
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
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack audioTrack = audioPlayer.getPlayingTrack();

        if (audioTrack == null) {
            Error.replyMessage(message, "No track is currently playing!");
            return;
        }
        final AudioTrackInfo info = audioTrack.getInfo();
        String title = info.title.isBlank() ? "Unknown title" : info.title;
        final String speakerEmojiUrl = "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/twitter/282/speaker-high-volume_1f50a.png";
        if (info.isStream) {
            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle(title, info.uri)
                    .setThumbnail("https://i.ytimg.com/vi_webp/" + info.identifier + "/maxresdefault.webp")
                    .appendDescription("\nBy " + info.author + "\n\n")
                    .appendDescription(audioPlayer.isPaused() ? "⏸️" : "▶️")
                    .appendDescription(" 🔴 LIVE ")
                    .appendDescription(musicManager.scheduler.queueRepeating ? "🔁"
                            : musicManager.scheduler.trackRepeating ? "🔂" : "")
                    .setFooter(audioPlayer.getVolume() + "%", speakerEmojiUrl);
            message.replyEmbeds(embed.build()).queue();
            return;
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle(title, info.uri)
                .setThumbnail("https://i.ytimg.com/vi_webp/" + info.identifier + "/maxresdefault.webp")
                .appendDescription("\nBy " + info.author + "\n\n")
                .appendDescription(audioPlayer.isPaused() ? "⏸️ " : "▶️ ")
                .appendDescription(Utils.getTrackSlider(audioTrack))
                .appendDescription(" " + (musicManager.scheduler.queueRepeating ? "🔁"
                        : musicManager.scheduler.trackRepeating ? "🔂" : ""))
                .setFooter(audioPlayer.getVolume() + "%", speakerEmojiUrl);
        message.replyEmbeds(embed.build()).queue();
    }
}
