package tk.wurst_client.gui.options;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.wurst_client.Client;
import tk.wurst_client.utils.XRayUtils;

public class GuiXRayBlocksManager extends GuiScreen
{
	private GuiScreen prevMenu;
	public static GuiXRayBlocksList blockList;

	public GuiXRayBlocksManager(GuiScreen par1GuiScreen)
    {
        this.prevMenu = par1GuiScreen;
    }
	
	public void initGui()
	{
		this.blockList = new GuiXRayBlocksList(this.mc, this);
		this.blockList.func_148134_d(7, 8);
		this.blockList.sortBlocks();
		this.blockList.elementClicked(-1, false, 0, 0);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 52, 98, 20, "Add"));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 2, this.height - 52, 98, 20, "Remove"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height - 28, 200, 20, "Back"));
	}
	
	/**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ((GuiButton)this.buttonList.get(1)).enabled = this.blockList.getSelectedSlot() != - 1;
    }
	
	protected void actionPerformed(GuiButton clickedButton)
    {
		if(clickedButton.enabled)
        {
            if(clickedButton.id == 0)
            {//Add
            	this.mc.displayGuiScreen(new GuiXRayBlocksAdd(this));
            }else if(clickedButton.id == 1)
            {//Remove
            	XRayUtils.xrayBlocks.remove(this.blockList.getSelectedSlot());
            	this.blockList.sortBlocks();
            	Client.Wurst.fileManager.saveXRayBlocks();
            }else if(clickedButton.id == 2)
            {//Back
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
    			this.blockList.elementClicked(- 1, false, 0, 0);
        super.mouseClicked(par1, par2, par3);
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.blockList.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, "X-Ray Block Manager", this.width / 2, 8, 16777215);
        int totalBlocks = 0;
		for(int i = 0; i < this.blockList.blocks.size(); i++)
		{
			if(XRayUtils.xrayBlocks.contains(this.blockList.blocks.get(i)))
				totalBlocks++;
		}
        this.drawCenteredString(this.fontRendererObj, "Blocks: " + totalBlocks, this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
