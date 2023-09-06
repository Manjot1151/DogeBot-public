package org.manjot.hypixel;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

import org.manjot.Utils;

import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.mojangapi.MojangAPI;
import net.hypixel.api.reply.GuildReply.Guild;

public class Player
{
    private String displayname;
    private String uuid;
    private long lastLogin;
    private long lastLogout;
    private String mostRecentGameType;
    private int karma;
    private long networkExp;
    private String newPackageRank;
    private String monthlyPackageRank;
    private String monthlyRankColor;
    private String rankPlusColor;
    private String rank;
    private String prefix;
    public Player(){}
    public String getName()
    {
        return displayname;
    }
    public boolean isOnline()
    {
        return (lastLogin >= lastLogout)? true : false;
    }
    public String getOnlineStatus()
    {
        return (lastLogin >= lastLogout)? "<:online:841667691504336896> Online\n(for **" + getOnlineDuration() + "**)" : "<:offline:841667625499230229> Offline\n(for **" + getOfflineDuration() + "**)";
    }
    public String getMostRecentGame()
    {
        return (mostRecentGameType!=null)? Utils.firstCharUpperCase(mostRecentGameType) : "Unknown";
    }
    public String getUuid()
    {
        return uuid;
    }
    public String getAvatarUrl()
    {
        return "https://mc-heads.net/avatar/" + uuid;
    }
    public int getKarma()
    {
        return karma;
    }
    public long getNetworkExperience()
    {
        return networkExp;
    }
    public int getNetworkLevel()
    {
        return (int) ((Math.sqrt((2 * networkExp) + 30625) / 50) - 2.5);
    }
    public String getRank()
    {
        if (uuid.equals("b876ec32e396476ba1158438d83c67d4")) return "PIG+++"; // Technoblade
        if (uuid.equals("f7c77d999f154a66a87dc4a51ef30d19")) return "OWNER"; // hypixel
        if (prefix!=null) return prefix.substring(3, prefix.length()-1);
        if (rank!=null) return rank;
        if (newPackageRank == null) return "NONE";
        if (monthlyPackageRank!=null && monthlyPackageRank.equals("SUPERSTAR")) return "MVP++";
        return newPackageRank.replaceAll("_PLUS", "+");
    }
    public String getPlusColor()
    {
        return rankPlusColor;
    }
    public String getRankColor()
    {
        return monthlyRankColor;
    }
    public String getRankEmoji()
    {
        String rankEmoji = "";
        String plusEmoji = "";

        switch (getRank())
        {
            case "VIP":
                return "<:vip1:864506282965598248><:vip2:864506283020386324><:vip3:864506283172298792>";

            case "VIP+":
                return "<:vipp1:864507929570705439><:vipp2:864507929284706315><:vipp3:864507929276448789>";

            case "MVP":
                return "<:mvp1:864506161808932874><:mvp2:864506161843404820><:mvp3:864506161709449227>";

            case "MVP+":
                rankEmoji = "<:mvpp1:864506229090025473><:mvpp2:864506228906393691><:mvpp3:864506228859994174>";
                break;

            case "MVP++":
                if (monthlyRankColor == null || monthlyRankColor.equals("GOLD"))
                    rankEmoji = "<:gmvp:865670374548570112><:gmvp2:865670374451707924>";
                else
                    rankEmoji = "<:bmvp:865669280351911946><:bmvp2:865669279986089986>";
                break;

            case "PIG+++":
                return "<:pig1:864506162073698334><:pig2:864506161873420288><:pig3:864506161893998602><:pig4:864506162090213416>";

            case "YOUTUBER":
                return "<:yt1:864512840269824060><:yt2:864512840282538034><:yt3:864512840366948362><:yt4:864512840118566953><:yt5:864512840228405308>";

            case "ADMIN":
                return "<:admin1:865684332072009778><:admin2:865684332009619456><:admin3:865684332050645022><:admin4:865684552898969690>";

            case "OWNER":
                return "<:owner1:865685848083136593><:owner2:865685848049057822><:owner3:865685848053121054><:owner4:865685848041062410>";

            case "NONE":
                return "";

            default:
                return "[" + getRank() + "]";
        }
        if (getPlusColor() == null)
        {
            if (!getRank().equals("MVP++"))
            {
                plusEmoji = "<:bmvpdarkred2:864506229145206834>";
            }
            else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
            {
                plusEmoji = "+<:bmvpdarkred2:864506229145206834>";
            }
            else
            {
                plusEmoji = "<:gmvpdarkred:865670374494961704><:gmvpdarkred2:865670374201360385>";
            }
        }
        else
        switch (getPlusColor())
        {
            case "BLACK":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpblack2:864506229199470632>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "<:bmvpblack:865668363258429476><:bmvpblack2:864506229199470632>";
                }
                else
                {
                    plusEmoji = "<:gmvpblack:865670374447382569><:gmvpblack2:865670374229671937>";
                }
                break;

            case "DARK_BLUE":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpdarkblue2:864506229056602114>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpdarkblue2:864506229056602114>";
                }
                else
                {
                    plusEmoji = "<:gmvpdarkblue:865670374448824362><:gmvpdarkblue2:865670374577143828>";
                }
                break;

            case "DARK_GREEN":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpdarkgreen2:864506229030912070>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "<:bmvpdarkgreen:865668363476271105><:bmvpdarkgreen2:864506229030912070>";
                }
                else
                {
                    plusEmoji = "<:gmvmdarkgreen:865670374134251532><:gmvpdarkgreen2:865670374477922325>";
                }
                break;

            case "DARK_AQUA":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpaqua2:864506229190950962>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpaqua2:864506229190950962>";
                }
                else
                {
                    plusEmoji = "<:gmvpaqua:865670374459703357><:gmvpaqua2:865670374322864199>";
                }

                break;

            case "DARK_RED":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpdarkred2:864506229145206834>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpdarkred2:864506229145206834>";
                }
                else
                {
                    plusEmoji = "<:gmvpdarkred:865670374494961704><:gmvpdarkred2:865670374201360385>";
                }
                break;

            case "DARK_PURPLE":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvppurple2:864506228831027252>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvppurple2:864506228831027252>";
                }
                else
                {
                    plusEmoji = "<:gmvppurple:865670374623805540><:gmvppurple2:865670374598770718>";
                }
                break;

            case "GOLD":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvporange2:864506229056864286>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvporange2:864506229056864286>";
                }
                else
                {
                    plusEmoji = "";
                }
                break;

            case "DARK_GRAY":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpgray2:864506229253341194>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpgray2:864506229253341194>";
                }
                else
                {
                    plusEmoji = "<:gmvpgray:865670374711754772><:gmvpgray2:865670374499156009>";
                }
                break;

            case "BLUE":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpblue2:864506228885815297>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "<:bmvpblue:865668363509563482><:bmvpblue2:864506228885815297>";
                }
                else
                {
                    plusEmoji = "<:gmvpblue:865670374149849089><:gmvpblue2:865670374494306354>";
                }
                break;

            case "GREEN":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpgreen2:864506229061845012>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpgreen2:864506229061845012>";
                }
                else
                {
                    plusEmoji = "<:gmvpgreen:865670374490505267><:gmvpgreen2:865670374481854474>";
                }
                break;

            case "RED":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpred2:864506228813725737>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "<:bmvpred:865668363602886696><:bmvpred2:864506228813725737>";
                }
                else
                {
                    plusEmoji = "<:gmvpred:865670374506102799><:gmvpred2:865670374829457408>";
                }
                break;

            case "LIGHT_PURPLE":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvppink2:864506229279686676>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "<:bmvppink:865668363556225034><:bmvppink2:864506229279686676>";
                }
                else
                {
                    plusEmoji = "<:gmvppink:865670374493257738><:gmvppink2:865670374213156865>";
                }
                break;

            case "YELLOW":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpyellow2:864506229215723570>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpyellow2:864506229215723570>";
                }
                else
                {
                    plusEmoji = "<:gmvpyellow:865670374649102377><:gmvpyellow2:865670374481854505>";
                }
                break;

            case "WHITE":
                if (!getRank().equals("MVP++"))
                {
                    plusEmoji = "<:bmvpwhite2:864506229078360114>";
                }
                else if (monthlyRankColor!=null && monthlyRankColor.equals("AQUA"))
                {
                    plusEmoji = "+<:bmvpwhite2:864506229078360114>";
                }
                else
                {
                    plusEmoji = "<:gmvpwhite:865670374498500609><:gmvpwhite2:865670374829195284>";
                }
                break;
        }
        return rankEmoji + plusEmoji;
    }

    public Guild getGuild() throws APIException, InvalidPlayerException, InterruptedException, ExecutionException, IOException
    {
        return Hypixel.api.getGuildByPlayer(MojangAPI.getUUID(displayname)).get().getGuild();
    }
    public String getOnlineDuration()
    {
        return Utils.formatMilli(Instant.now().toEpochMilli() - lastLogin);
    }
    public String getOfflineDuration()
    {
        return Utils.formatMilli(Instant.now().toEpochMilli() - lastLogout);
    }
}
