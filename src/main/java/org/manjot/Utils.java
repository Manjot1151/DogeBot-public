package org.manjot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Utils {
    public static JDA jda;
    public static User me;
    public static User bot;
    public static List<Long> devs;
    public static List<String> ytGuilds;
    public static ScheduledExecutorService exec;

    public static void initialize() {
        jda = Main.jda;
        me = jda.retrieveUserById(316481084835495937L).complete();
        bot = jda.getSelfUser();
        devs = List.of(316481084835495937L, 389482537329623040L);
        exec = Executors.newSingleThreadScheduledExecutor();
    }

    public static EmbedBuilder getDefaultEmbed() {
        return new EmbedBuilder()
                .setColor(0xFCC203)
                .setTimestamp(OffsetDateTime.now());
    }

    public static EmbedBuilder getDefaultErrorEmbed() {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTimestamp(OffsetDateTime.now());
    }

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            HttpURLConnection httpcon = (HttpURLConnection) new URL(urlString).openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla");
            reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static JsonElement readJsonUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            HttpURLConnection httpcon = (HttpURLConnection) new URL(urlString).openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla");
            reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return JsonParser.parseString(buffer.toString());
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static String messageFrom(String[] args, int argStart) {
        String argSum = "";
        for (int i = argStart; i < args.length - 1; i++) {
            argSum += args[i] + " ";
        }
        argSum += args[args.length - 1];
        return argSum;
    }

    public static String firstCharUpperCase(String str) {
        str = str.toLowerCase();
        String words[] = str.split("\\s");
        String capitalizeWord = "";
        for (String w : words) {
            String first = w.substring(0, 1);
            String afterfirst = w.substring(1);
            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizeWord.trim();
    }

    public static String parseId(String mention) {
        Matcher matcher = Pattern.compile("\\d{17,}").matcher(mention);
        if (!matcher.find())
            return "";
        return matcher.group();
    }

    public static User getMentionedUser(String mention) {
        try {
            return jda.retrieveUserById(parseId(mention)).complete();
        } catch (Exception e) {
            return null;
        }
    }

    public static User getMentionedUserOrDefault(String mention, User user) {
        try {
            User retrievedUser = jda.retrieveUserById(parseId(mention)).complete();
            if (retrievedUser == null)
                throw new Exception();
            return retrievedUser;
        } catch (Exception e) {
            return user;
        }
    }

    public static Member getMentionedMember(Guild guild, String mention) {
        try {
            return guild.retrieveMemberById(parseId(mention)).complete();
        } catch (Exception e) {
            return null;
        }
    }

    public static Member getMentionedMemberOrDefault(Guild guild, String mention, Member member) {
        try {
            Member retrievedMember = guild.retrieveMemberById(parseId(mention)).complete();
            if (retrievedMember == null)
                throw new Exception();
            return guild.getMemberById(parseId(mention));
        } catch (Exception e) {
            return member;
        }
    }

    public static int randomPercentage(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static String formatMilli(long milli) {
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
        /*
         * String positive = String.format(
         * "%dh %02dm %02ds",
         * absSeconds / 3600,
         * (absSeconds % 3600) / 60,
         * absSeconds % 60);
         */
        return formatted;
    }

    public static String formatMilliColons(long milli) {
        long absSeconds = milli / 1000;
        int hours = (int) (absSeconds / 3600);
        int minutes = (int) ((absSeconds % 3600) / 60);
        int seconds = (int) (absSeconds % 60);
        return (hours != 0 ? hours + ":" : "") + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "")
                + seconds;
    }

    public static String getTrackSlider(AudioTrack audioTrack) {
        AudioTrackInfo info = audioTrack.getInfo();
        long currentPosition = audioTrack.getPosition();
        int dashesLeft = (int) Math.min(((currentPosition * 30) / info.length), 30);
        int dashesRight = 30 - dashesLeft;
        String slider = "";
        for (int i = 0; i < dashesLeft; i++) {
            slider += "―";
        }
        slider += "`🔘`";
        for (int i = 0; i < dashesRight; i++) {
            slider += "―";
        }
        return "`" + formatMilliColons(audioTrack.getPosition()) + " " + slider + " "
                + Utils.formatMilliColons(info.length) + "`";
    }

    public static String numberWithCommas(long l) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(l);
    }

    public static void sendMessage(MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    public static String formatDuration(Duration duration) {
        long durSeconds = duration.getSeconds();
        long absSeconds = Math.abs(durSeconds);
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
        /*
         * String positive = String.format(
         * "%dh %02dm %02ds",
         * absSeconds / 3600,
         * (absSeconds % 3600) / 60,
         * absSeconds % 60);
         */
        return formatted;
    }

    public static String bruteForceYTLink(String code, int length) {
        if (length > 0) {
            for (char i = '-'; i <= 'z'; i++) {
                String newLink = bruteForceYTLink(code + i, length - 1);
                if (newLink != null) {
                    return newLink;
                }
                switch (i) {
                    case '-' -> {
                        i = '0' - 1;
                    }
                    case '9' -> {
                        i = 'A' - 1;
                    }
                    case 'Z' -> {
                        i = '_' - 1;
                    }
                    case '_' -> {
                        i = 'a' - 1;
                    }
                }
            }
        } else {
            try {
                System.out.println(code);
                new URL("https://img.youtube.com/vi/" + code + "/0.jpg").openStream();
                return code;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static boolean isDev(User user) {
        return devs.contains(user.getIdLong());
    }

    public static boolean isDev(Member member) {
        return devs.contains(member.getIdLong());
    }

    public static String formatInteger(long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public static String formatDouble(double number) {
        String s = Double.toString(number);
        return s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", "") : s;
    }

    public static String formatFloat(float number) {
        String s = Float.toString(number);
        return s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", "") : s;
    }

    public static boolean isNSFW(MessageChannel channel) {
        return ((TextChannel) (channel instanceof ThreadChannel
                ? ((ThreadChannel) channel).getParentChannel()
                : channel)).isNSFW();
    }
}
