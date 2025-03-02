package net.itshamza.cavity.block.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import net.itshamza.cavity.Cavity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CavityBlockScreen extends AbstractContainerScreen<CavityBlockMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Cavity.MOD_ID, "textures/gui/chest_cavity.png");

    public CavityBlockScreen(CavityBlockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        p_283065_.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }
}
