package org.manjot.commands.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Image {
    private static String api = "https://some-random-api.ml/canvas/";

    public static String gay(String avatar) {
        return api + "gay?avatar=" + avatar;
    }

    public static String glass(String avatar) {
        return api + "glass?avatar=" + avatar;
    }

    public static String wasted(String avatar) {
        return api + "wasted?avatar=" + avatar;
    }

    public static InputStream triggered(String avatar) throws MalformedURLException, IOException {
        return new URL(api + "triggered?avatar=" + avatar).openStream();
    }

    public static String grayScale(String avatar) {
        return api + "greyscale?avatar=" + avatar;
    }

    public static String invert(String avatar) {
        return api + "invert?avatar=" + avatar;
    }

    public static String invertGrayScale(String avatar) {
        return api + "invertgreyscale?avatar=" + avatar;
    }

    public static String brightness(String avatar) {
        return api + "brightness?avatar=" + avatar;
    }

    public static String jail(String avatar) {
        return api + "jail?avatar=" + avatar;
    }

    public static String missionPassed(String avatar) {
        return api + "passed?avatar=" + avatar;
    }

    public static String comrade(String avatar) {
        return api + "comrade?avatar=" + avatar;
    }

    public static String comment(String avatar, String username, String comment) {
        return (api + "youtube-comment?avatar=" + avatar + "&username=" + username + "&comment=" + comment)
                .replaceAll(" ", "+");
    }

    public static String tweet(String avatar, String username, String displayname, String comment) {
        return (api + "tweet?avatar=" + avatar + "&username=" + username + "&displayname=" + displayname + "&comment="
                + comment).replaceAll(" ", "+");
    }

    public static String stupid(String avatar, String text) {
        return (api + "its-so-stupid?avatar=" + avatar + "&dog=" + text.replaceAll("\\s+", "+"));
    }
}
