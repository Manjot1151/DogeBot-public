package org.manjot.commands.minecraft;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ProfileCommand extends Command implements CommandListener {
    public ProfileCommand() {
        this.setName("profile")
                .setAliases("minecraftprofile", "mcprofile")
                .setDescription("Get info about a minecraft player")
                .setUsage("profile <IGN/UUID>")
                .setType(CommandType.MINECRAFT);
    }

    private static String api = "https://mc-heads.net/minecraft/profile/";

    public static MessageEmbed get(String username) throws Exception {
        JsonObject profileJson = Utils.readJsonUrl(api + username).getAsJsonObject();
        if (profileJson.get("error") != null) {
            return null;
        }
        return getAsEmbed(profileJson);
    }

    private static MessageEmbed getAsEmbed(JsonObject profile) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Profile");
        embed.addField("IGN", "```fix\n" + profile.get("name").getAsString() + "```", true);
        embed.addField("UUID", "```" + profile.get("id").getAsString() + "```", true);
        embed.setThumbnail("https://mc-heads.net/head/" + profile.get("id").getAsString() + "/left");
        return embed.build();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        try {
            channel.sendMessageEmbeds(get(args[0])).queue();
        } catch (Exception e) {
            Error.replyMessage(message, "Could not find that player!");
        }
    }
}
