package org.auction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.manjot.Auth;
import org.manjot.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class PlayerAuction {
    public static MessageEmbed get(String name) throws Exception {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        JsonObject mojangProfile = JsonParser
                .parseString(Utils.readUrl("https://api.mojang.com/users/profiles/minecraft/" + name)).getAsJsonObject();
        name = mojangProfile.get("name").getAsString();
        String uuid = mojangProfile.get("id").getAsString();
        embed.setTitle(name, "https://sky.shiiyu.moe/" + name);
        embed.setThumbnail("https://mc-heads.net/head/" + uuid + "/left");
        JsonArray profiles = JsonParser.parseString(Utils.readUrl(
                "https://api.hypixel.net/skyblock/profiles?key=" + Auth.hypixel + "&uuid=" + uuid))
                .getAsJsonObject().get("profiles").getAsJsonArray();
        HashMap<String, String> profileNames = new HashMap<String, String>();
        for (JsonElement e : profiles) {
            JsonObject profile = e.getAsJsonObject();
            String profileId = profile.get("profile_id").getAsString();
            String cuteName = profile.get("cute_name").getAsString();
            profileNames.put(profileId, cuteName);
        }
        return getEmbed(embed, uuid, profileNames);
    }

    private static MessageEmbed getEmbed(EmbedBuilder embed, String playerUuid, HashMap<String, String> profileNames)
            throws Exception {
        HashMap<String, List<Field>> fields = new HashMap<String, List<Field>>();
        Gson gson = new Gson();
        JsonArray auctionArray = JsonParser.parseString(Utils
                .readUrl("https://api.hypixel.net/skyblock/auction?key=" + Auth.hypixel + "&player="
                        + playerUuid))
                .getAsJsonObject().get("auctions").getAsJsonArray();
        int totalAuctions = auctionArray.size();

        long moneyEarnt = 0l;
        long unsoldValue = 0l;
        int totalAuctionsUnclaimed = 0;

        for (int i = 0; i < totalAuctions; i++) {
            try {
                Auction ah = gson.fromJson(auctionArray.get(i), Auction.class);
                if (!ah.isClaimed() && profileNames.containsKey(ah.getProfileUuid())) {
                    String description = "";
                    totalAuctionsUnclaimed++;
                    if (ah.hasExpired()) {
                        description = "This auction has expired! | Starting Bid: " + priceFormat(ah.getStartingBid());
                    } else if (ah.hasSold()) {
                        moneyEarnt += ah.getHighestBid();
                        description = "This auction has ended at " + priceFormat(ah.getHighestBid()) + " coins.";
                    } else if (ah.isBin()) {
                        unsoldValue += ah.getStartingBid();
                        description = "Buy Price: " + priceFormat(ah.getStartingBid()) + " | Time Remaining: "
                                + formatMilli(ah.timeTillEnd());
                    } else if (ah.getHighestBid() != 0) {
                        unsoldValue += ah.getHighestBid();
                        description = "Current Bid: " + priceFormat(ah.getHighestBid()) + " | Time Remaining: "
                                + formatMilli(ah.timeTillEnd());
                    } else {
                        unsoldValue += ah.getStartingBid();
                        description = "Starting Bid: " + priceFormat(ah.getStartingBid()) + " | Time Remaining: "
                                + formatMilli(ah.timeTillEnd());
                    }
                    if (!description.isEmpty()) {
                        List<Field> fieldList = fields.getOrDefault(ah.getProfileUuid(), new ArrayList<Field>());
                        fieldList.add(new Field(ah.getItemName(), description, false));
                        fields.put(ah.getProfileUuid(), fieldList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        for (Entry<String, List<Field>> entry : fields.entrySet()) {
            Iterator<Field> fieldIterator = entry.getValue().iterator();
            if (!fieldIterator.hasNext()) {
                continue;
            }
            String cuteName = profileNames.get(entry.getKey());
            embed.addField("", ">>> ```arm\n" + cuteName + "```", false);
            while (fieldIterator.hasNext()) {
                embed.addField(fieldIterator.next());
            }
        }
        if (totalAuctionsUnclaimed == 0) {
            embed.setDescription("This player has no auctions running!");
        } else {
            if (moneyEarnt > 0)
                embed.setDescription("Unclaimed coins: **" + Utils.formatInteger(moneyEarnt) + "**")
                        .appendDescription("\nUnsold value: **" + Utils.formatInteger(unsoldValue) + "**")
                        .appendDescription("\nOverall value: **" + Utils.formatInteger(moneyEarnt + unsoldValue) + "**");

            embed.setFooter("Total Auctions: " + totalAuctionsUnclaimed);
        }
        return embed.build();
    }

    // private static JsonObject readUrl(String urlString) throws Exception
    // {
    // BufferedReader reader = null;
    // try {
    // URL url = new URL(urlString);
    // reader = new BufferedReader(new InputStreamReader(url.openStream()));
    // StringBuffer buffer = new StringBuffer();
    // int read;
    // char[] chars = new char[1024];
    // while ((read = reader.read(chars)) != -1)
    // buffer.append(chars, 0, read);
    // JsonParser parser = new JsonParser();
    // return (JsonObject) parser.parse(buffer.toString());
    // } finally {
    // if (reader != null)
    // reader.close();
    // }
    // }
    public static String formatMilli(long milli) {
        if (milli <= 0)
            return "**Ended!**";
        long absSeconds = milli / 1000;
        int days = (int) (absSeconds / (3600 * 24));
        int hours = (int) ((absSeconds % (3600 * 24)) / 3600);
        int minutes = (int) ((absSeconds % 3600) / 60);
        int seconds = (int) (absSeconds % 60);
        String formatted = seconds + "s";
        if (minutes != 0)
            formatted = minutes + "m " + formatted;
        if (hours != 0)
            formatted = hours + "h " + formatted;
        if (days != 0)
            formatted = days + "d " + formatted;
        return "**" + formatted + "**";
    }

    private static String[] suffix = new String[] { "", "k", "m", "b", "t" };
    private static int MAX_LENGTH = 4;

    private static String priceFormat(long number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return "**" + r + "**";
    }

}
