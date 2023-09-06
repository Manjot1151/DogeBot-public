package org.manjot.commands.music;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class VolumeCommand extends Command implements CommandListener {

    public VolumeCommand() {
        this.setName("volume")
                .setAliases("vol", "musicvolume", "songvolume")
                .setDescription("Change volume of music")
                .setUsage("volume (integer: 0-100)")
                .setType(CommandType.MUSIC);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        Member self = guild.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        AudioPlayer audioPlayer = PlayerManager.getInstance().getMusicManager(guild).audioPlayer;

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

        if (args.length == 0) {
            message.reply("Current volume is set to 🔊 " + audioPlayer.getVolume()).queue();
            ;
            return;
        }

        try {
            int volume = Integer.parseInt(args[0]);
            if (volume > 100 || volume < 0)
                throw new Exception("Volume must be an integer within 0-100");

            audioPlayer.setVolume(volume);

            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle("Volume")
                    .setDescription("set to 🔊 " + volume + ".");
            message.replyEmbeds(embed.build()).queue();
        } catch (Exception e) {
            Error.replyMessage(message, "Volume must be an integer within 0-100");
            return;
        }
    }
}
