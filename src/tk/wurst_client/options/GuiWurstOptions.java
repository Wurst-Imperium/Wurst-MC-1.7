package tk.wurst_client.options;

import java.net.URI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tk.wurst_client.Client;

public class GuiWurstOptions extends GuiScreen
{
	private GuiScreen prevMenu;
    private static final String __OBFID = "CL_00000703";
	private static final Logger logger = LogManager.getLogger();

	public GuiWurstOptions(GuiScreen par1GuiScreen)
    {
        this.prevMenu = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 144 - 16, "Back"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 - 16, "WIP Mods: " + (Client.Wurst.options.WIP ? "Enabled" : "Disabled")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72 - 16, "Middle Click Friends: " + (Client.Wurst.options.middleClickFriends ? "Enabled" : "Disabled")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 48 - 16, "Keybind Manager"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 - 16, "X-Ray Block Manager"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 0 - 16, "Wurst Website"));
    }

    protected void actionPerformed(GuiButton clickedButton)
    {
		if(clickedButton.enabled)
        {
            if(clickedButton.id == 0)
            {//Cancel
            	this.mc.displayGuiScreen(this.prevMenu);
            }else if(clickedButton.id == 1)
            {//WIP
            	Client.Wurst.options.WIP = !Client.Wurst.options.WIP;
            	clickedButton.displayString = "WIP Mods: " + (Client.Wurst.options.WIP ? "Enabled" : "Disabled");
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 2)
            {//Middle Click Friends
            	Client.Wurst.options.middleClickFriends = !Client.Wurst.options.middleClickFriends;
            	clickedButton.displayString = "Middle Click Friends: " + (Client.Wurst.options.middleClickFriends ? "Enabled" : "Disabled");
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 3)
            {//Keybinds
            	this.mc.displayGuiScreen(new GuiKeybinds(this));
            }else if(clickedButton.id == 4)
            {//X-Ray Blocks
            	this.mc.displayGuiScreen(new GuiXRayBlocks(this));
            }else if(clickedButton.id == 5)
            {//Website
            	try
                {
                	Class var3 = Class.forName("java.awt.Desktop");
                	Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                	var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {new URI("http://www.wurst-client.tk/")});
                }
                catch (Throwable var5)
                {
                	logger.error("Couldn\'t open link", var5);
                }
            }
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Wurst menu", this.width / 2, 40, 16777215);
        this.drawCenteredString(this.fontRendererObj, "These mods are unstable and can", this.width / 2, this.height / 4 + 102, 16777215);
        this.drawCenteredString(this.fontRendererObj, "cause all kinds of weirdness. Changes", this.width / 2, this.height / 4 + 110, 16777215);
        this.drawCenteredString(this.fontRendererObj, "will only take effect after a restart.", this.width / 2, this.height / 4 + 118, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
