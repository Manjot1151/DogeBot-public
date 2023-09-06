package org.manjot.commands.music.lavaplayer;

import org.manjot.Error;
import org.manjot.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    public final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.audioPlayerManager.setTrackStuckThreshold(10000);

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(Message message, String trackUrl) {
        final GuildMusicManager musicManager = this.getMusicManager(message.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                AudioTrackInfo info = track.getInfo();
                musicManager.scheduler.queue(track);
                EmbedBuilder embed = Utils.getDefaultEmbed();
                embed.setTitle("Adding to queue...")
                    .setThumbnail("https://i.ytimg.com/vi_webp/" + info.identifier + "/maxresdefault.webp")
                    .appendDescription("Track: [" + info.title + "](" + info.uri + ")")
                    .appendDescription("\nAuthor: " + info.author + "\n")
                    .appendDescription(info.isStream ? "🔴 LIVE" : "Length: " + Utils.formatMilli(info.length));
                message.replyEmbeds(embed.build()).queue();;
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();
                if (playlist.isSearchResult()) {
                    AudioTrack track = tracks.get(0);
                    AudioTrackInfo info = track.getInfo();
                    EmbedBuilder embed = Utils.getDefaultEmbed();
                    embed.setTitle("Adding to queue...")
                    .setThumbnail("https://i.ytimg.com/vi_webp/" + info.identifier + "/maxresdefault.webp")
                    .appendDescription("Track: [" + info.title + "](" + info.uri + ")")
                    .appendDescription("\nAuthor: " + info.author + "\n")
                    .appendDescription(info.isStream ? "🔴 LIVE" : "Length: " + Utils.formatMilli(info.length));
                    message.replyEmbeds(embed.build()).queue();

                    musicManager.scheduler.queue(track);
                    return;
                }
                EmbedBuilder embed = Utils.getDefaultEmbed();
                embed.setTitle("Adding to queue...")
                    .appendDescription("Tracks: " + Integer.toString(tracks.size()))
                    .appendDescription("\nPlaylist: " + playlist.getName());
                message.replyEmbeds(embed.build()).queue();

                for (final AudioTrack track : tracks) {
                    musicManager.scheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                Error.replyMessage(message, "Could not find any matches!");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                Error.replyMessage(message, "Failed to load the song");
            }

        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
