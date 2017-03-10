package tk.wurst_client.alts;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;

import org.lwjgl.opengl.GL11;

import tk.wurst_client.Client;
import tk.wurst_client.utils.AltUtils;

public class GuiAlts extends GuiScreen
{
	private GuiScreen prevMenu;
	private boolean shouldAsk = true;
	private int errorTimer;
	public static GuiAltList altList;

	public GuiAlts(GuiScreen par1GuiScreen)
    {
        this.prevMenu = par1GuiScreen;
    }
	
	public void initGui()
	{
		this.altList = new GuiAltList(this.mc, this);
		this.altList.func_148134_d(7, 8);
		this.altList.elementClicked(-1, false, 0, 0);
		if(this.altList.alts.isEmpty() && this.shouldAsk)
		{
			this.mc.displayGuiScreen(new GuiYesNo(this, "Your alt list is empty.", "Would you like some random alts to get started?", 0));
		}
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height - 52, 100, 20, "Use"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height - 52, 100, 20, "Direct Login"));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 54, this.height - 52, 100, 20, "Add"));
		this.buttonList.add(new GuiButton(3, this.width / 2 - 154, this.height - 28, 100, 20, "Edit"));
		this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 28, 100, 20, "Delete"));
		this.buttonList.add(new GuiButton(5, this.width / 2 + 54, this.height - 28, 100, 20, "Cancel"));
	}
	
	/**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ((GuiButton)this.buttonList.get(0)).enabled = !this.altList.alts.isEmpty() && this.altList.getSelectedSlot() != - 1;
        ((GuiButton)this.buttonList.get(3)).enabled = !this.altList.alts.isEmpty() && this.altList.getSelectedSlot() != - 1;
        ((GuiButton)this.buttonList.get(4)).enabled = !this.altList.alts.isEmpty() && this.altList.getSelectedSlot() != - 1;
    }
	
	protected void actionPerformed(GuiButton clickedButton)
    {
		if(clickedButton.enabled)
        {
            if(clickedButton.id == 0)
            {//Use
            	Alt alt = this.altList.alts.get(this.altList.getSelectedSlot());
            	if(alt.cracked)
            	{//Cracked
            		AltUtils.changeCrackedName(alt.name);
                	this.mc.displayGuiScreen(this.prevMenu);
            	}else
            	{//Premium
            		String reply = AltUtils.login(alt.name, alt.password);
            		if(reply.equals(""))
            			this.mc.displayGuiScreen(this.prevMenu);
            		else
            		{
            			this.errorTimer = 8;
            			if(reply.equals("§4§lWrong password!"))
            			{
            				this.altList.alts.remove(this.altList.alts.indexOf(alt));
            				Client.Wurst.fileManager.saveAlts();
            			}
            		}
            	}
            }else if(clickedButton.id == 1)
            {//Direct login
            	this.mc.displayGuiScreen(new GuiAltLogin(this));
            }else if(clickedButton.id == 2)
            {//Add
            	this.mc.displayGuiScreen(new GuiAltAdd(this));
            }else if(clickedButton.id == 3)
            {//Edit
            	Alt alt = this.altList.alts.get(this.altList.getSelectedSlot());
            	this.mc.displayGuiScreen(new GuiAltEdit(this, alt));
            }else if(clickedButton.id == 4)
            {//Delete
            	Alt alt = this.altList.alts.get(this.altList.getSelectedSlot());
                String deleteQuestion = "Are you sure you want to remove this alt?";
                String deleteWarning = "\"" + alt.name + "\" will be lost forever! (A long time!)";
            	this.mc.displayGuiScreen(new GuiYesNo(this, deleteQuestion, deleteWarning, "Delete", "Cancel", 1));
            }else if(clickedButton.id == 5)
            {//Cancel
            	this.mc.displayGuiScreen(this.prevMenu);
            }
        }
    }
	
	public void confirmClicked(boolean par1, int par2)
    {
		if(par2 == 0)
		{
			if(par1)
			{
				for(int i = 0; i < 8; i++)
					this.altList.alts.add(new Alt(AltUtils.generateName(), null));
				this.altList.sortAlts();
				Client.Wurst.fileManager.saveAlts();
			}
			this.shouldAsk = false;
		}else if(par2 == 1)
		{
        	Alt alt = this.altList.alts.get(this.altList.getSelectedSlot());
        	if(par1)
        	{
        		this.altList.alts.remove(this.altList.alts.indexOf(alt));
				Client.Wurst.fileManager.saveAlts();
        	}
		}
    	this.mc.displayGuiScreen(this);
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
    			this.altList.elementClicked(- 1, false, 0, 0);
        super.mouseClicked(par1, par2, par3);
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.altList.func_148128_a(par1, par2, par3);
        if(this.altList.getSelectedSlot() != -1)
        {
        	Alt alt = this.altList.alts.get(this.altList.getSelectedSlot());
            AltUtils.drawAltBack(alt.name, (this.width / 2 - 125) / 2 - 32, this.height / 2 - 64 - 9, 64, 128);
            AltUtils.drawAltBody(alt.name, this.width - (this.width / 2 - 140) / 2 - 32, this.height / 2 - 64 - 9, 64, 128);
        }
        this.drawCenteredString(this.fontRendererObj, "Alt Manager", this.width / 2, 8, 16777215);
        this.drawCenteredString(this.fontRendererObj, "Alts: " + this.altList.alts.size(), this.width / 2, 20, 16777215);
        if(errorTimer > 0)
        {
        	GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL_CULL_FACE);
			GL11.glEnable(GL_BLEND);
	        GL11.glColor4f(1.0F, 0.0F, 0.0F, (float)errorTimer / 16);
	        GL11.glBegin(GL11.GL_QUADS);
	        {
	        	GL11.glVertex2d(0, 0);
	        	GL11.glVertex2d(this.width, 0);
	        	GL11.glVertex2d(this.width, this.height);
	        	GL11.glVertex2d(0, this.height);
	        }
	        GL11.glEnd();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL_CULL_FACE);
			GL11.glDisable(GL_BLEND);
			errorTimer--;
        }
        super.drawScreen(par1, par2, par3);
    }
}
