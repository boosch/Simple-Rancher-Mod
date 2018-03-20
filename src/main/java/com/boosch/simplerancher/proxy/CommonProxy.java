package com.boosch.simplerancher.proxy;

import net.minecraft.item.Item;

public interface CommonProxy {

    public void init();
    public void preInit();
    public void postInit();
    public void registerItemRenderer(Item item, int meta, String id);

}
