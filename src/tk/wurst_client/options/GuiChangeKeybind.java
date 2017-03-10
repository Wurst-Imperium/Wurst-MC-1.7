package tk.wurst_client.options;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Module;

public class GuiChangeKeybind extends GuiScreen
{
    private GuiScreen prevMenu;
    private static final String __OBFID = "CL_00000709";
	private Module module;
	private int key;

    public GuiChangeKeybind(GuiScreen prevMenu, Module module)
    {
        this.prevMenu = prevMenu;
        this.module = module;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen(){}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Save"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 144 + 12, "Cancel"));
        this.key = module.getBind();
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
            if (clickedButton.id == 1)
            {//Cancel
                this.mc.displayGuiScreen(this.prevMenu);
            }else if (clickedButton.id == 0)
            {//Save
            	this.module.setBind(key);
            	Client.Wurst.fileManager.saveModules();
        		GuiKeybinds.bindList.sortModules();
                this.mc.displayGuiScreen(this.prevMenu);
            }
        }
    }

	/**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        key = par2;
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
        this.func_146278_c(0);
        this.drawCenteredString(this.fontRendererObj, "Change this Keybind", this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, "Press a key to change the keybind.", this.width / 2, this.height / 4 + 48, 10526880);
        this.drawCenteredString(this.fontRendererObj, "Mod: " + this.module.getName(), this.width / 2, this.height / 4 + 84, 10526880);
        this.drawCenteredString(this.fontRendererObj, "Keybind: " + Keyboard.getKeyName(key), this.width / 2, this.height / 4 + 96, 10526880);
        super.drawScreen(par1, par2, par3);
    }
}
