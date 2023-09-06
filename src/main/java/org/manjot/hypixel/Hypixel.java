package org.manjot.hypixel;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.auction.Fetcher;
import org.manjot.Auth;
import org.manjot.Error;
import org.manjot.Utils;

import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.mojangapi.MojangAPI;
import me.nullicorn.nedit.NBTReader;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.GuildReply.Guild;

public class Hypixel
{
    public static String petHeads = "{\"ARMADILLO\":\"https://sky.shiiyu.moe/head/c1eb6df4736ae24dd12a3d00f91e6e3aa7ade6bbefb0978afef2f0f92461018f\",\"BAT\":\"https://sky.shiiyu.moe/head/382fc3f71b41769376a9e92fe3adbaac3772b999b219c9d6b4680ba9983e527\",\"BLAZE\":\"https://sky.shiiyu.moe/head/b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0\",\"CHICKEN\":\"https://sky.shiiyu.moe/head/7f37d524c3eed171ce149887ea1dee4ed399904727d521865688ece3bac75e\",\"HORSE\":\"https://sky.shiiyu.moe/head/36fcd3ec3bc84bafb4123ea479471f9d2f42d8fb9c5f11cf5f4e0d93226\",\"JERRY\":\"https://sky.shiiyu.moe/head/822d8e751c8f2fd4c8942c44bdb2f5ca4d8ae8e575ed3eb34c18a86e93b\",\"OCELOT\":\"https://sky.shiiyu.moe/head/5657cd5c2989ff97570fec4ddcdc6926a68a3393250c1be1f0b114a1db1\",\"PIGMAN\":\"https://sky.shiiyu.moe/head/63d9cb6513f2072e5d4e426d70a5557bc398554c880d4e7b7ec8ef4945eb02f2\",\"RABBIT\":\"https://sky.shiiyu.moe/head/117bffc1972acd7f3b4a8f43b5b6c7534695b8fd62677e0306b2831574b\",\"SHEEP\":\"https://sky.shiiyu.moe/head/64e22a46047d272e89a1cfa13e9734b7e12827e235c2012c1a95962874da0\",\"SILVERFISH\":\"https://sky.shiiyu.moe/head/da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540\",\"WITHER_SKELETON\":\"https://sky.shiiyu.moe/head/f5ec964645a8efac76be2f160d7c9956362f32b6517390c59c3085034f050cff\",\"SKELETON_HORSE\":\"https://sky.shiiyu.moe/head/47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a\",\"WOLF\":\"https://sky.shiiyu.moe/head/dc3dd984bb659849bd52994046964c22725f717e986b12d548fd169367d494\",\"ENDERMAN\":\"https://sky.shiiyu.moe/head/6eab75eaa5c9f2c43a0d23cfdce35f4df632e9815001850377385f7b2f039ce1\",\"PHOENIX\":\"https://sky.shiiyu.moe/head/23aaf7b1a778949696cb99d4f04ad1aa518ceee256c72e5ed65bfa5c2d88d9e\",\"MAGMA_CUBE\":\"https://sky.shiiyu.moe/head/38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429\",\"FLYING_FISH\":\"https://sky.shiiyu.moe/head/40cd71fbbbbb66c7baf7881f415c64fa84f6504958a57ccdb8589252647ea\",\"BLUE_WHALE\":\"https://sky.shiiyu.moe/head/dab779bbccc849f88273d844e8ca2f3a67a1699cb216c0a11b44326ce2cc20\",\"TIGER\":\"https://sky.shiiyu.moe/head/fc42638744922b5fcf62cd9bf27eeab91b2e72d6c70e86cc5aa3883993e9d84\",\"LION\":\"https://sky.shiiyu.moe/head/38ff473bd52b4db2c06f1ac87fe1367bce7574fac330ffac7956229f82efba1\",\"PARROT\":\"https://sky.shiiyu.moe/head/5df4b3401a4d06ad66ac8b5c4d189618ae617f9c143071c8ac39a563cf4e4208\",\"SNOWMAN\":\"https://sky.shiiyu.moe/head/11136616d8c4a87a54ce78a97b551610c2b2c8f6d410bc38b858f974b113b208\",\"TURTLE\":\"https://sky.shiiyu.moe/head/212b58c841b394863dbcc54de1c2ad2648af8f03e648988c1f9cef0bc20ee23c\",\"BEE\":\"https://sky.shiiyu.moe/head/7e941987e825a24ea7baafab9819344b6c247c75c54a691987cd296bc163c263\",\"ENDER_DRAGON\":\"https://sky.shiiyu.moe/head/aec3ff563290b13ff3bcc36898af7eaa988b6cc18dc254147f58374afe9b21b9\",\"GUARDIAN\":\"https://sky.shiiyu.moe/head/221025434045bda7025b3e514b316a4b770c6faa4ba9adb4be3809526db77f9d\",\"SQUID\":\"https://sky.shiiyu.moe/head/01433be242366af126da434b8735df1eb5b3cb2cede39145974e9c483607bac\",\"GIRAFFE\":\"https://sky.shiiyu.moe/head/176b4e390f2ecdb8a78dc611789ca0af1e7e09229319c3a7aa8209b63b9\",\"ELEPHANT\":\"https://sky.shiiyu.moe/head/7071a76f669db5ed6d32b48bb2dba55d5317d7f45225cb3267ec435cfa514\",\"MONKEY\":\"https://sky.shiiyu.moe/head/13cf8db84807c471d7c6922302261ac1b5a179f96d1191156ecf3e1b1d3ca\",\"SPIDER\":\"https://sky.shiiyu.moe/head/cd541541daaff50896cd258bdbdd4cf80c3ba816735726078bfe393927e57f1\",\"ENDERMITE\":\"https://sky.shiiyu.moe/head/5a1a0831aa03afb4212adcbb24e5dfaa7f476a1173fce259ef75a85855\",\"GHOUL\":\"https://sky.shiiyu.moe/head/87934565bf522f6f4726cdfe127137be11d37c310db34d8c70253392b5ff5b\",\"JELLYFISH\":\"https://sky.shiiyu.moe/head/913f086ccb56323f238ba3489ff2a1a34c0fdceeafc483acff0e5488cfd6c2f1\",\"PIG\":\"https://sky.shiiyu.moe/head/621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4\",\"ROCK\":\"https://sky.shiiyu.moe/head/cb2b5d48e57577563aca31735519cb622219bc058b1f34648b67b8e71bc0fa\",\"SKELETON\":\"https://sky.shiiyu.moe/head/fca445749251bdd898fb83f667844e38a1dff79a1529f79a42447a0599310ea4\",\"ZOMBIE\":\"https://sky.shiiyu.moe/head/56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11\",\"DOLPHIN\":\"https://sky.shiiyu.moe/head/cefe7d803a45aa2af1993df2544a28df849a762663719bfefc58bf389ab7f5\",\"BABY_YETI\":\"https://sky.shiiyu.moe/head/ab126814fc3fa846dad934c349628a7a1de5b415021a03ef4211d62514d5\",\"MEGALODON\":\"https://sky.shiiyu.moe/head/a94ae433b301c7fb7c68cba625b0bd36b0b14190f20e34a7c8ee0d9de06d53b9\",\"GOLEM\":\"https://sky.shiiyu.moe/head/89091d79ea0f59ef7ef94d7bba6e5f17f2f7d4572c44f90f76c4819a714\",\"HOUND\":\"https://sky.shiiyu.moe/head/b7c8bef6beb77e29af8627ecdc38d86aa2fea7ccd163dc73c00f9f258f9a1457\",\"TARANTULA\":\"https://sky.shiiyu.moe/head/8300986ed0a04ea79904f6ae53f49ed3a0ff5b1df62bba622ecbd3777f156df8\",\"BLACK_CAT\":\"https://sky.shiiyu.moe/head/e4b45cbaa19fe3d68c856cd3846c03b5f59de81a480eec921ab4fa3cd81317\",\"SPIRIT\":\"https://sky.shiiyu.moe/head/8d9ccc670677d0cebaad4058d6aaf9acfab09abea5d86379a059902f2fe22655\",\"GRIFFIN\":\"https://sky.shiiyu.moe/head/4c27e3cb52a64968e60c861ef1ab84e0a0cb5f07be103ac78da67761731f00c8\",\"MITHRIL_GOLEM\":\"https://sky.shiiyu.moe/head/c1b2dfe8ed5dffc5b1687bc1c249c39de2d8a6c3d90305c95f6d1a1a330a0b1\",\"GRANDMA_WOLF\":\"https://sky.shiiyu.moe/head/4e794274c1bb197ad306540286a7aa952974f5661bccf2b725424f6ed79c7884\",\"RAT\":\"https://sky.shiiyu.moe/head/a8abb471db0ab78703011979dc8b40798a941f3a4dec3ec61cbeec2af8cffe8\",\"BAL\":\"https://sky.shiiyu.moe/head/c469ba2047122e0a2de3c7437ad3dd5d31f1ac2d27abde9f8841e1d92a8c5b75\",\"SCATHA\":\"https://sky.shiiyu.moe/head/df03ad96092f3f789902436709cdf69de6b727c121b3c2daef9ffa1ccaed186c\",\"GOLDEN_DRAGON\":\"https://sky.shiiyu.moe/head/2e9f9b1fc014166cb46a093e5349b2bf6edd201b680d62e48dbf3af9b0459116\",\"AMMONITE\":\"https://sky.shiiyu.moe/head/a074a7bd976fe6aba1624161793be547d54c835cf422243a851ba09d1e650553\"}";
    public static HypixelAPI api = new HypixelAPI(new ApacheHttpClient(UUID.fromString(Auth.hypixel))); // Hypixel API key - /api new
    final static DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    public static String[] getGuild(String username) throws InterruptedException, ExecutionException, APIException, InvalidPlayerException, IOException
    {
        final UUID player = MojangAPI.getUUID(username);
        Guild guild = api.getGuildByPlayer(player).get().getGuild();
        String name = guild.getName();
        String tag = guild.getTag();
        String description = guild.getDescription();
        String creationDate = guild.getCreationDate().withZoneSameInstant(ZoneId.of("GMT")).format(formatter);
        return new String[] {name, tag, description, creationDate};
    }

    public static Player getPlayer(String username) throws APIException, InvalidPlayerException, IOException, InterruptedException, ExecutionException
    {
        final UUID playerUuid = MojangAPI.getUUID(username);
        Gson gson = new Gson();
        Player player = gson.fromJson(api.getPlayerByUuid(playerUuid).get().getPlayer().getRaw(), Player.class);
        return player;
    }

    public static JsonObject getSkyblockProfile(String username) throws InterruptedException, ExecutionException, APIException, InvalidPlayerException, IOException
    {
        final UUID player = MojangAPI.getUUID(username);
        JsonObject profile = api.getSkyBlockProfile(player.toString()).get().getProfile();
        return profile;
    }

    public static ArrayList<String> getAuctions(int page, int index) throws InterruptedException, ExecutionException
    {
        ArrayList<String> auctionList = new ArrayList<String>();
        Iterator<JsonElement> iterator = api.getSkyBlockAuctions(page).get().getAuctions().iterator();
        Gson gson = new Gson();
        if (!iterator.hasNext())
        {
            auctionList.add("No auctions were found!");
            return auctionList;
        }

        while (iterator.hasNext() && index-->0)
        {
            iterator.next();
        }
            JsonElement json = iterator.next();
            Auction auction = gson.fromJson(json, Auction.class);
            System.out.println(json.toString());
            auctionList.add("**Item:**\n" + auction.getItemName() + "\n\n**Starting bid:**\n" + auction.getStartingBid() + "\n\n**Highest Bid:**\n" + auction.getHighestBid() + "\n\n**Claimed:**\n" + auction.isClaimed() + "\n\n**BIN:**\n" + auction.isBin() + "\n\n**Item Lore:\n**" + auction.getItemLore() + "\n");
            // Auction auction = g.fromJson(json, Auction.class);
            // System.out.println(auction.item_name);
        //     auctionList.add()
        //     auctionList.add("Item: " + auction.getItemName("item_name") + "\nStarting bid: " + auction.get("starting_bid") + "\nHighest Bid: " + auction.get("highest_bid_amount") + "\nClaimed: " + auction.get("claimed") + "\n" + auction.get("item_lore") + "\n\n");
        //     System.out.println("Item: " + auction.get("item_name") + "\nStarting bid: " + auction.get("starting_bid") + "\nHighest Bid: " + auction.get("highest_bid_amount") + "\nClaimed: " + auction.get("claimed") + "\n" + auction.get("item_lore") + "\n\n");
        return auctionList;
    }

    public static int getLowestBin(String item) throws Exception
    {
        JsonObject lbin = Utils.readJsonUrl("http://moulberry.codes/lowestbin.json").getAsJsonObject();
        return (lbin.get(item.toUpperCase().replaceAll(" ", "_")).getAsInt());
        //return (int) Double.parseDouble(lbin.get(item.toUpperCase().replaceAll(" ", "_")));
    }

    public static JsonObject getRealLowestBin(String item, MessageChannel channel, Message message) throws InterruptedException, ExecutionException, APIException, IOException
    {
        item = item.toUpperCase().replaceAll(" ", "_");
        FileReader reader = new FileReader("../lowestbin/itemList.json");
        JsonObject itemList = JsonParser.parseReader(reader).getAsJsonObject();
        long lastUpdated = itemList.get("lastUpdated").getAsLong();
        long timeSinceUpdate = Instant.now().toEpochMilli() - lastUpdated;
        reader.close();
        if (timeSinceUpdate > 1800000)
        {
            try {
                Fetcher.fetchAuctions();
                return getRealLowestBin(item, channel, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!itemList.has(item))
        {
            message.delete().queue();
            Error.replyMessage(message, "Could not find that item on the auction!");
        }
        JsonObject itemJson = itemList.getAsJsonObject(item);
        String formattedUpdated = Utils.formatMilli(timeSinceUpdate);
        itemJson.addProperty("lastUpdated", formattedUpdated + " ago");
        return itemJson;
    }

    public static BazaarItem getBazaarInfo(String itemId) throws Exception
    {
        itemId = itemId.replaceAll("ENCH_", "ENCHANTED_");
        String json = Utils.readUrl("https://sky.shiiyu.moe/api/v2/bazaar");
        if (!itemId.endsWith("K:4")) json = json.replaceFirst("INK_SACK:4", "LAPIS_LAZULI");
        json = json.replaceFirst("(?i)(?!\")\\w*" + itemId + "\\w*(?=(\":))", "BAZAAR_ITEM");
        JsonObject bazaar = JsonParser.parseString(json).getAsJsonObject();
        Gson gson = new Gson();
        BazaarItem bazaarItem = gson.fromJson(bazaar.get("BAZAAR_ITEM"), BazaarItem.class);
        return bazaarItem;
    }

}

class Auction
{
    private String uuid;
    private String item_name;
    private int starting_bid;
    private int highest_bid_amount;
    private boolean claimed;
    private String item_lore;
    private boolean bin;
    private String item_bytes;
    public Auction(){}
    public String getUuid()
    {
        return uuid;
    }
    public String getItemName()
    {
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
        String lore = item_lore;
        char colorPrefix = (char) 167;
        char[] remove = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'k', 'l', 'm', 'n', 'o', 'r'};
        for (char c : remove)
        {
            lore = lore.replaceAll(colorPrefix + "" + c, "");
        }
        return lore;
    }
    public boolean isBin()
    {
        return bin;
    }
    public String getItemId() throws JsonSyntaxException, IOException
    {
        if (item_name.startsWith("["))
        {
            return item_name.split("] ")[1].replaceAll(" " + (char) 10022, "").toUpperCase().replaceAll(" ", "_");
        }
        String decodedNBT = NBTReader.readBase64(item_bytes).toString();
        JsonObject jsonObject = JsonParser.parseString(decodedNBT).getAsJsonObject();
        String itemId = jsonObject.get("i").getAsJsonArray().get(0).getAsJsonObject().get("tag").getAsJsonObject().get("ExtraAttributes").getAsJsonObject().get("id").getAsString();
        return itemId;
    }
}
