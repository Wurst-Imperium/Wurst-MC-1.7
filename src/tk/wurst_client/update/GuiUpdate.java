package tk.wurst_client.update;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;

public class GuiUpdate extends GuiScreen
{
    private GuiScreen prevMenu;
    private static final String __OBFID = "CL_00000709";
	private boolean linkIsOpen = false;
	private static final Logger logger = LogManager.getLogger();

    public GuiUpdate(GuiScreen par1GuiScreen)
    {
        this.prevMenu = par1GuiScreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    	
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Close Minecraft"));
        Client.Wurst.updater.openUpdateLink();
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
            {//Close
            	this.mc.shutdown();
            }
        }
    }

	/**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {

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
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "The link to the new version of Wurst", this.width / 2, this.height / 2 - 64, 16777215);
        this.drawCenteredString(this.fontRendererObj, "should have openened in your browser.", this.width / 2, this.height / 2 - 52, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
