package com.boosch.simplerancher.block.Pedestal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

/**
 * This extends the special renderer for tile entities
 * In it, we can spell out special rules for how to render our tile entity
 */
public class TileEntitySpecialRendererPedestal extends TileEntitySpecialRenderer<TileEntityPedestal> {

    /**
     * In general, get the item stack stored in the current tileEntity, if it's not EMPTY, set up a
     * GLstate (?) render the itemStack itself, and reset the GL state...
     *
     * The GLState performs lighting, blending, etc. Once set up, we need to make our item bob up and down.
     * There are two parts to this. We want to bob TO x+.5, y+1.25, z+.5 - this is directly .25 blocks above the
     * center of our block
     *
     * To get the bounce, we need to calculate an offset at all times
     *   1. get the time in ticks since the pedestal was modified
     *   2. Add in partial ticks (a fractional value representing the amount of time between the last full tick and now.
     *        Without this the animation would be jittery because there can be fewer ticks per second than FPS.
     *   3. divide this by 8 to slow things down and look... fancy
     *   4. Take the sine of that to produce a value that's oscillating back and forth across an axis
     *   5. divide THAT by 4 to compress the sine-wave vertically so the bobbing isn't too intensely high up and down.
     *
     * Then we rotate...
     *    1. Get the world time, adjust for partialTicks
     *    2. divide by 4 - slow it down. There's no sine wave here because we want a constant rate of rotation
     *    3. finish the parameters: they define the vector around which rotation happens - we want horizontal rotation
     *       so we use the vector along a Y axis (0,1,0)
     *
     * Now the GL state is fully set up, so we render it. The Null in the call indicates that it's purely visual - there
     * is no item to be picked up.
     *
     * IBakedModel is a 'compiled' representation of a model - it contains all of the data from the JSON compressed into
     * a list of BakedQuad shapes that cna be passed directly to OpenGL for rendering.
     * We alert ForgeHooksClient to handle the camera transformations for the specific model, then the type of
     * transformations (closest match) and 'false' to indicate that it's not an item in the left hand (?). This is a
     * simplifying technique to allow Forge to deal with 'perspective-aware-models.'
     *
     * Now we have a fully baked model, and it needs a texture. The TextureMap.LOCATION_BLOCKS_TEXTURE is badly named -
     * it represents all textures for items as well as blocks.
     *
     * Next, we rendering the whole thing - giving in the itemStack, the IBakedModel to the RenderItem instance.
     * Then we clean up (reset the GLState)
     *
     * Then finally, after all of this, we have to register our renderer-  but look over to ClientProxy (rendering
     * doesnt happen on the server!) for the details.
     *
     * @param te
     * @param x
     * @param y
     * @param z
     * @param partialTicks
     * @param destroyStage
     * @param alpha
     */
    @Override
    public void render(TileEntityPedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {

            //set up GLState
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();

            //Do the bobbing translation setup
            double offset = Math.sin((te.getWorld().getTotalWorldTime() - te.getLastChangeTime() + partialTicks) / 8) / 4.0;
            GlStateManager.translate(x + 0.5, y + 1.25 + offset, z + 0.5);

            //add the rotational setup
            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);

            //bake the model
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            //bind the texture
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            //clean up after ourselves
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }
}
