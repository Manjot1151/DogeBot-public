package org.auction;

import java.io.IOException;
import java.time.Instant;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;

class Auction
{
    private String profile_id;
    private String auctioneer;
    private String uuid;
    private String item_name;
    private int starting_bid;
    private int highest_bid_amount;
    private boolean claimed;
    private String item_lore;
    private boolean bin;
    private Object item_bytes;
    private long end;
    public Auction(){}
    private String itemBytes()
    {
        return item_bytes.toString().startsWith("{")? JsonParser.parseString(item_bytes.toString()).getAsJsonObject().get("data").getAsString() : item_bytes.toString();
    }
    public String getAuctioneerUuid()
    {
        return auctioneer;
    }
    public String getProfileUuid()
    {
        return profile_id;
    }
    public String getUuid()
    {
        return uuid;
    }
    public String getItemName()
    {
        if (item_name.equals("Enchanted Book"))
        {
            return getItemLore().split("\n")[0];
        }
        return item_name;
    }
    public int getStartingBid()
    {
        return starting_bid;
    }
    public int getHighestBid()
    {
        return highest_bid_amount;
    }
    public boolean isClaimed()
    {
        return claimed;
    }
    public String getItemLore()
    {
        return item_lore.replaceAll("(?i)§([0-9a-fl-or]|k.*?(?=(§[0-9a-fr]|\n|$)))", "");
    }
    public boolean isBin()
    {
        return bin;
    }
    public String getItemId() throws JsonSyntaxException, IOException
    {
        if (item_name.startsWith("[Lvl"))
        {
            return item_name.split("] ")[1].replaceAll(" " + (char) 10022, "").toUpperCase().replaceAll(" ", "_");
        }
        return parseNBT().getString("tag.ExtraAttributes.id", "NULL");
    }
    public long getEndMilli()
    {
        return end;
    }
    public NBTCompound parseNBT() throws IOException
    {
        return (NBTCompound) NBTReader.readBase64(itemBytes()).getList("i").get(0);
    }
    public boolean hasSold()
    {
        if ((currentTime() >= end || bin) && highest_bid_amount > 0)
            return true;
        return false;
    }
    public boolean hasExpired()
    {
        if (currentTime() >= end && highest_bid_amount == 0)
            return true;
        return false;
    }
    public long timeTillEnd()
    {
        return end - currentTime();
    }
    private long currentTime()
    {
        return Instant.now().toEpochMilli();
    }
}
