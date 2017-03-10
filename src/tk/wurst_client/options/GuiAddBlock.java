package tk.wurst_client.options;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glDisable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.utils.XRayUtils;

public class GuiAddBlock extends GuiScreen
{
    private GuiScreen prevMenu;
    private GuiTextField nameBox;
    private static final String __OBFID = "CL_00000709";

    public GuiAddBlock(GuiScreen par1GuiScreen)
    {
        this.prevMenu = par1GuiScreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.nameBox.updateCursorCounter();
        Block block = Block.getBlockFromName(this.nameBox.getText());
        ((GuiButton)this.buttonList.get(0)).enabled = this.nameBox.getText().trim().length() > 0 && block != null;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Add"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 144 + 12, "Cancel"));
        this.nameBox = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 80, 200, 20);
        this.nameBox.setFocused(true);
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton clickedButton)
    {
        if (clickedButton.enabled)
        {
            if (clickedButton.id == 0)
            {//Add
                Block block = Block.getBlockFromName(this.nameBox.getText());
                XRayUtils.xrayBlocks.add(block);
                GuiXRayBlocks.blockList.sortBlocks();
                Client.Wurst.fileManager.saveXRayBlocks();
                this.mc.displayGuiScreen(this.prevMenu);
            }else if (clickedButton.id == 1)
            {//Cancel
                this.mc.displayGuiScreen(this.prevMenu);
            }
        }
    }

	/**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.nameBox.textboxKeyTyped(par1, par2);

        if (par2 == 28 || par2 == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.nameBox.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.func_146278_c(0);
        Block block = Block.getBlockFromName(this.nameBox.getText());
		int x = this.width / 2 - 9;
		int y = this.height / 2 - 32;
		try
		{
			new RenderItem().renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), new ItemStack(Item.getItemFromBlock(block)), x, y);
			glDisable(GL_LIGHTING);
			this.drawString(this.fontRendererObj, "Name: " + block.getLocalizedName(), (this.width - this.fontRendererObj.getStringWidth("Name: " + block.getLocalizedName())) / 2, y + 24, 10526880);
			this.drawString(this.fontRendererObj, "ID: " + Block.getIdFromBlock(block), (this.width - this.fontRendererObj.getStringWidth("ID: " + Block.getIdFromBlock(block))) / 2, y + 36, 10526880);
			this.drawString(this.fontRendererObj, "Block exists: " + (block != null), (this.width - this.fontRendererObj.getStringWidth("Block exists: " + (block != null))) / 2, y + 48, 10526880);
		}catch(Exception e)
		{
			glDisable(GL_LIGHTING);
			this.mc.fontRenderer.drawString("?", x + 6, y + 5, 10526880);
			this.drawString(this.fontRendererObj, "Name: unknown", (this.width - this.fontRendererObj.getStringWidth("Name: unknown")) / 2, y + 24, 10526880);
			this.drawString(this.fontRendererObj, "ID: unknown", (this.width - this.fontRendererObj.getStringWidth("ID: unknown")) / 2, y + 36, 10526880);
			this.drawString(this.fontRendererObj, "Block exists: " + (block != null), (this.width - this.fontRendererObj.getStringWidth("Block exists: " + (block != null))) / 2, y + 48, 10526880);
		}
        this.drawCenteredString(this.fontRendererObj, "Add a Block", this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, "Name or ID", this.width / 2 - 100, 67, 10526880);
        this.nameBox.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
