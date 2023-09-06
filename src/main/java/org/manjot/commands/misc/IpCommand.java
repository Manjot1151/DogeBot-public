package org.manjot.commands.misc;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class IpCommand extends Command implements CommandListener {
    public IpCommand() {
        this.setName("ip")
                // .setAliases()
                .setDescription("Get information about an IP")
                .setUsage("ip <IP>")
                .setType(CommandType.MISCELLANEOUS);
    }

    private static String api = "http://ip-api.com/json/{query}?fields=status,message,country,regionName,city,district,zip,lat,lon,isp,org,as,reverse,proxy,hosting,query";

    public static MessageEmbed trace(String searchIp) {
        Gson gson = new Gson();
        try {
            IpInfo ip = gson.fromJson(
                    JsonParser.parseString(Utils.readUrl(api.replace("{query}", searchIp))).getAsJsonObject(),
                    IpInfo.class);
            if (ip.getStatus().equals("fail"))
                return Error.messageEmbed(ip.getMessage());
            EmbedBuilder embed = Utils.getDefaultEmbed();
            embed.setTitle(ip.getQuery())
                    .addField("Address", ip.getAddress(), false)
                    .addField("Co-ordinates", ip.getCoords(), false)
                    .addField("ZIP", ip.getZip(), false)
                    .addField("ISP", ip.getIsp(), false)
                    .addField("Org", ip.getOrg(), false)
                    .addField("AS", ip.getAS(), false)
                    .addField("Reverse DNS", ip.getReverseDns(), false)
                    .addField("Proxy", Boolean.toString(ip.isProxy()), false)
                    .addField("Hosting", Boolean.toString(ip.isHosting()), false);
            return embed.build();
        } catch (Exception e) {
            return Error.messageEmbed("Could not track that IP!");
        }

    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        message.replyEmbeds(trace(Utils.messageFrom(args, 0))).queue();
    }
}

class IpInfo {
    // status,message,country,regionName,city,district,zip,lat,lon,isp,org,as,reverse,proxy,hosting,query
    private String status, message, country, regionName, city, district, zip, isp, org, as, reverse, query;
    private float lat, lon;
    private boolean proxy, hosting;

    public IpInfo() {
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return district + (district.isEmpty() || city.isEmpty() ? "" : ", ") + city
                + (city.isEmpty() || regionName.isEmpty() ? "" : ", ") + regionName
                + (regionName.isEmpty() || country.isEmpty() ? "" : ", ") + country;
    }

    public String getZip() {
        return zip;
    }

    public String getCoords() {
        return lat + ", " + lon;
    }

    public String getIsp() {
        return isp;
    }

    public String getOrg() {
        return org;
    }

    public String getAS() {
        return as;
    }

    public String getReverseDns() {
        return reverse;
    }

    public String getQuery() {
        return query;
    }

    public boolean isProxy() {
        return proxy;
    }

    public boolean isHosting() {
        return hosting;
    }
}
