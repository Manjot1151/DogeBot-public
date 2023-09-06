package org.manjot.commands.music;

import java.net.MalformedURLException;
import java.net.URL;

import org.manjot.Error;
import org.manjot.Mongo;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.GuildMusicManager;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import com.mongodb.client.model.Filters;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends Command implements CommandListener {

    public PlayCommand() {
        this.setName("play")
                .setAliases("p", "playmusic", "playsong", "unpause")
                .setDescription("Play music")
                .setUsage("play (music)")
                .setBotPermissions(Permission.VOICE_CONNECT, Permission.VOICE_SPEAK)
                .setType(CommandType.MUSIC);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        Member self = guild.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioPlayer audioPlayer = musicManager.audioPlayer;
        if (!memberVoiceState.inAudioChannel()) {
            Error.replyMessage(message, "You need to be in a voice channel!");
            return;
        }
        if (memberVoiceState.isDeafened()) {
            Error.replyMessage(message, "You need to be undeafened to use that command");
            return;
        }
        if (!audioPlayer.isPaused() && args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        if (!selfVoiceState.inAudioChannel()) {
            AudioManager audioManager = guild.getAudioManager();
            AudioChannel memberChannel = memberVoiceState.getChannel();
            audioManager.setSelfDeafened(true);
            audioManager.openAudioConnection(memberChannel);
        }

        else if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            Error.replyMessage(message, "You need to be in the same voice channel as me!");
            return;
        }
        if (args.length >= 1) {
            String link = Utils.messageFrom(args, 0);
            boolean ytSearch = Mongo.client.getDatabase("music").getCollection("ytGuilds")
                    .find(Filters.eq("id", guild.getId())).first() != null;
            if (args.length >= 2 || !isUrl(link)) {
                link = (ytSearch ? "yt" : "sc") + "search:" + link;
            }
            if (link.matches("https?://(?:youtu\\.?be|\\w+?\\.youtube).*") && !ytSearch) {
                Error.replyMessage(message, "YouTube links are not supported!");
                return;
            }
            PlayerManager.getInstance().loadAndPlay(message, link);
            return;
        }
        if (audioPlayer.isPaused()) {
            audioPlayer.setPaused(false);
            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle("Continued playing!");
            message.replyEmbeds(embed.build()).queue();
        }
    }

    private static boolean isUrl(String url) {
        try {
            if (url.isEmpty()) {
                return false;
            }
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
