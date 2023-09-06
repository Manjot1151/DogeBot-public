package org.auction;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.manjot.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Fetcher
{

    private static String itemListFile = "../lowestbin/itemList.json";

    private static void updateJson(String file, JsonObject json) throws IOException
    {
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(gson.toJson(json));
        fileWriter.close();
    }

    public static void fetchAuctions() throws Exception
    {
        // long startTime = Instant.now().toEpochMilli();
        Gson gson = new Gson();
        HashMap<String, JsonObject> itemList = new HashMap<String, JsonObject>();
        // System.out.println("Fetching auctions...");
        JsonArray auctionArray = Utils.readJsonUrl("https://api.eastarcti.ca/auctions?query={\"bin\":true}&sort={\"starting_bid\":1}&filter={\"uuid\":1,\"end\":1,\"item_name\":1,\"item_lore\":1,\"starting_bid\":1,\"item_bytes\":1}").getAsJsonArray();
        System.out.println("Finished fetching!");
        // long fetchTime = Instant.now().toEpochMilli();
        int totalAuctions = auctionArray.size();
        for (int i = 0; i < totalAuctions; i++)
        {
            Auction ah = gson.fromJson(auctionArray.get(i), Auction.class);;
            // System.out.println("Auction " + (i+1) + " of " + totalAuctions + " [" + ((i+1)*100/totalAuctions) + "%]");
            try
            {
                if (ah.isBin() && !ah.isClaimed())
                {
                    if (!itemList.containsKey(ah.getItemId()) || itemList.get(ah.getItemId()).get("price").getAsInt() > ah.getStartingBid())
                    {
                        JsonObject itemInfo = new JsonObject();
                        itemInfo.addProperty("name", ah.getItemName());
                        itemInfo.addProperty("price", ah.getStartingBid());
                        itemInfo.addProperty("uuid", ah.getUuid());
                        itemInfo.addProperty("lore", ah.getItemLore());
                        itemInfo.addProperty("end", ah.getEndMilli());
                        itemList.put(ah.getItemId(), itemInfo);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(ah.getItemName());
                continue;
            }
        }
        // long calculateTime = Instant.now().toEpochMilli();
        updateJson(itemListFile, mapToJson(itemList));
        // long updateTime = Instant.now().toEpochMilli();
        System.out.println("Item List Updated!");//\nFetch Time: " + (fetchTime - startTime) + "ms\nCalculate Time: " + (calculateTime - fetchTime) + "ms\nUpdate Time: " + (updateTime - calculateTime) + "ms\nTotal: " + (updateTime - startTime) + "ms");
    }

    private static JsonObject mapToJson(HashMap<String, JsonObject> map)
    {
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        jsonObject = JsonParser.parseString(gson.toJson(map)).getAsJsonObject();
        jsonObject.addProperty("lastUpdated", lastUpdated());
        return jsonObject;
    }

    public static MessageEmbed getLowestBin(String item) throws Exception
    {
        item = item.trim().replaceAll(" +", "%20");
        item = fixItemName(item);
        String lbinApi = "https://api.eastarcti.ca/skyblock/auctions?query={\"item_name\":{\"$regex\":\"[ITEM]\",\"$options\":\"i\"},\"bin\":true}&sort={\"starting_bid\":1}&limit=1&filter={\"uuid\":1,\"end\":1,\"item_name\":1,\"item_lore\":1,\"starting_bid\":1,\"item_bytes\":1}";
        Gson gson = new Gson();
        JsonObject jsonAh = Utils.readJsonUrl(lbinApi.replace("[ITEM]", item)).getAsJsonArray().get(0).getAsJsonObject();
        Auction ah = gson.fromJson(jsonAh, Auction.class);
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Lowest BIN");
        embed.addField("Item", "```fix\n" + ah.getItemName() + "```", true);
        embed.addField("Price", "```arm\n" + Utils.numberWithCommas(ah.getStartingBid()) + "```", true);
        embed.addField("Ends in", "```\n" + Utils.formatMilli(ah.getEndMilli() - Instant.now().toEpochMilli()) + "```", true);
        embed.addField("Command", "```\n/viewauction " + ah.getUuid() + "```", false);
        embed.addField("Lore", "```css\n" + ah.getItemLore() + "```", false);
        embed.setThumbnail("https://sky.shiiyu.moe/item.gif/" + ah.getItemId());
        return embed.build();
    }

    private static String fixItemName(String item)
    {
        switch (item)
        {
            case "aote":
            {
                return "Aspect Of The End";
            }
            case "aotd":
            {
                return "Aspect Of The Dragons";
            }
            case "aotj":
            {
                return "Aspect Of The Jerry";
            }
            case "aotv":
            {
                return "Aspect Of The Void";
            }
            case "aots":
            {
                return "Axe Of The Shredded";
            }
            default:
            {
                return item;
            }
        }
    }

    private static long lastUpdated()
    {
        return Instant.now().toEpochMilli();
    }

}
