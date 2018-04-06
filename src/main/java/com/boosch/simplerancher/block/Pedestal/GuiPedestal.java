package com.boosch.simplerancher.block.Pedestal;

import com.boosch.simplerancher.init.ModBlocks;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiPedestal extends GuiContainer{

    private InventoryPlayer playerInv;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/pedestal.png");


    public GuiPedestal(Container container, InventoryPlayer inventoryPlayer){
        super(container);
        this.playerInv= inventoryPlayer;

    }

    /**
     * First, reset colors to white - so we dont tint the whole thing to some other random color
     * X and Y determine our starting corner and size of the panel, so we draw it in the center
     * of the screen
     * Then draw it
     * @param partialTicks
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //drawing takes the start positions in the screen, and the start positions on the canvas, then a size to pull from the canvas
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }


    /**
     * Draw the localized name of our block on the screen. We draw it at the top center of our GUI,
     * so we subtract half of width of our localized name (as calculated by getStringWidth)
     * from half of the x-size of the GUI, giving use the X position that will result in it being
     * centered in the GUI.
     * We also pass 6 as the Y coordinate, so 6 pixels from the top of the GUI, and 0x404040 as
     * the color, in hexadecimal, for our string.
     *
     * Draw the localized name of the player’s inventory on the screen:
     * We call playerInv.getDisplayName() which returns an ITextComponent and call
     * getUnformattedText() on it to get the string to render. We draw it at X position 8,
     * just offset from the left side of our GUI, and at the Y position which is 94 pixels
     * (the height of the player’s inventory in our GUI) above the bottom of our GUI.
     *
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = ModBlocks.BLOCK_PEDESTAL.getLocalizedName();

        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);


        //super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
}
