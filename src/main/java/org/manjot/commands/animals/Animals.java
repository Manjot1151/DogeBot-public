package org.manjot.commands.animals;

import java.util.List;

import org.manjot.Utils;


public class Animals
{
    private static String api = "https://some-random-api.ml/img/";
    private static List<String> animals = List.of("panda", "red_panda", "bird", "fox", "koala", "raccoon", "kangaroo");
    private static String getImage(String animal) throws Exception
    {
        return Utils.readJsonUrl(api + animal).getAsJsonObject().get("link").getAsString();
    }
    public static String get(String animal)
    {
        if (animals.indexOf(animal)!=-1)
            try {
                return getImage(animal);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        return null;
    }
}
