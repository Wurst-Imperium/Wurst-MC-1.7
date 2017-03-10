package tk.wurst_client.options;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.wurst_client.Client;
import tk.wurst_client.module.Module;

public class GuiKeybinds extends GuiScreen
{
	private GuiScreen prevMenu;
	public static GuiKeybindList bindList;

	public GuiKeybinds(GuiScreen par1GuiScreen)
    {
        this.prevMenu = par1GuiScreen;
    }
	
	public void initGui()
	{
		this.bindList = new GuiKeybindList(this.mc, this);
		this.bindList.func_148134_d(7, 8);
		this.bindList.sortModules();
		this.bindList.elementClicked(-1, false, 0, 0);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 52, 98, 20, "Change Bind"));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 2, this.height - 52, 98, 20, "Clear Bind"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height - 28, 200, 20, "Back"));
	}
	
	/**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ((GuiButton)this.buttonList.get(0)).enabled = this.bindList.getSelectedSlot() != - 1;
        ((GuiButton)this.buttonList.get(1)).enabled = this.bindList.getSelectedSlot() != - 1 && Client.Wurst.moduleManager.activeModules.get(Client.Wurst.moduleManager.activeModules.indexOf(this.bindList.modules.get(this.bindList.getSelectedSlot()))).getBind() != 0;
    }
	
	protected void actionPerformed(GuiButton clickedButton)
    {
		if(clickedButton.enabled)
        {
            if(clickedButton.id == 0)
            {//Change Bind
                Module module = Client.Wurst.moduleManager.activeModules.get(Client.Wurst.moduleManager.activeModules.indexOf(this.bindList.modules.get(this.bindList.getSelectedSlot())));
            	this.mc.displayGuiScreen(new GuiChangeKeybind(this, module));
            }else if(clickedButton.id == 1)
            {//Clear Bind
                Module module = Client.Wurst.moduleManager.activeModules.get(Client.Wurst.moduleManager.activeModules.indexOf(this.bindList.modules.get(this.bindList.getSelectedSlot())));
            	module.setBind(0);
            	Client.Wurst.fileManager.saveModules();
        		this.bindList.sortModules();
            }else if(clickedButton.id == 2)
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
    	if(par2 >= 36 && par2 <= this.height - 57)
    		if(par1 >= this.width / 2 + 140 || par1 <= this.width / 2 - 126)
    			this.bindList.elementClicked(- 1, false, 0, 0);
        super.mouseClicked(par1, par2, par3);
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.bindList.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, "Keybind Manager", this.width / 2, 8, 16777215);
        int totalBinds = 0;
		for(int i = 0; i < this.bindList.modules.size(); i++)
		{
			if(this.bindList.modules.get(i).getBind() != 0)
				totalBinds++;
		}
        this.drawCenteredString(this.fontRendererObj, "Keybinds: " + totalBinds + ", Mods: " + Client.Wurst.moduleManager.activeModules.size(), this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
