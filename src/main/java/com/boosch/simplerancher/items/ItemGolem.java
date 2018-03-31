package com.boosch.simplerancher.items;

public class ItemGolem extends SimpleRancherItemBase implements FlavorText {


    protected String name;
    protected String type;
    protected String flavorText;

    public ItemGolem(String name, String type, String flavorText){

        super(name);
        this.name = name;
        this.type = type;
        this.flavorText=flavorText;
    }




    public String getFlavorText(){
        return flavorText;
    }
    public String getType(){
        return type;
    }



}
