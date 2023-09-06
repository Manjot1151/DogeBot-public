package org.manjot.hypixel;

public class BazaarItem
{
    private String id;
    private String name;
    private int buyPrice;
    private int sellPrice;
    private int buyVolume;
    private int sellVolume;
    private String tag;
    private int price;
    public BazaarItem(){}
    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public int getBuyPrice()
    {
        return buyPrice;
    }
    public int getSellPrice()
    {
        return sellPrice;
    }
    public int getBuyVolume()
    {
        return buyVolume;
    }
    public int getSellVolume()
    {
        return sellVolume;
    }
    public String getTag()
    {
        return tag;
    }
    public int getPrice()
    {
        return price;
    }
}