package tk.wurst_client.alts;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import tk.wurst_client.Client;
import tk.wurst_client.utils.AltUtils;

public class GuiAltEdit extends GuiScreen
{
    private GuiScreen prevMenu;
    private GuiTextField nameBox;
    private GuiPasswordField passwordBox;
    private static final String __OBFID = "CL_00000709";
    private String displayText = "";
	private int errorTimer;
	private Alt alt;

    public GuiAltEdit(GuiScreen par1GuiScreen, Alt alt)
    {
        this.prevMenu = par1GuiScreen;
        this.alt = alt;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.nameBox.updateCursorCounter();
        this.passwordBox.updateCursorCounter();
        ((GuiButton)this.buttonList.get(0)).enabled =
        (
        	this.nameBox.getText().trim().length() > 0
        	&& (!this.nameBox.getText().trim().equalsIgnoreCase("Alexander01998") || this.passwordBox.getText().length() != 0)
        );
        ((GuiButton)this.buttonList.get(3)).enabled = !this.nameBox.getText().trim().equalsIgnoreCase("Alexander01998");
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72 + 12, "Save"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96 + 12, "Random Name"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
        this.buttonList.add(new GuiButton(4, this.width - (this.width / 2 - 100) / 2 - 64, this.height - 32, 128, 20, "Steal Skin"));
        this.nameBox = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.nameBox.setMaxStringLength(48);
        this.nameBox.setFocused(true);
        this.nameBox.setText(this.alt.name);
        this.passwordBox = new GuiPasswordField(this.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.passwordBox.setFocused(false);
        this.passwordBox.setText(this.alt.password);
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
            Alt newAlt = new Alt(nameBox.getText(), passwordBox.getText());
            if (clickedButton.id == 1)
            {//Cancel
                this.mc.displayGuiScreen(this.prevMenu);
            }else if (clickedButton.id == 0)
            {//Save
            	if(passwordBox.getText().length() == 0)
            	{//Cracked
            		GuiAlts.altList.alts.set(GuiAlts.altList.alts.indexOf(alt), newAlt);
            		displayText = "";
            	}else
            	{//Premium
            		displayText = AltUtils.check(nameBox.getText(), passwordBox.getText());
            		if(displayText.equals(""))
            		{
            			GuiAlts.altList.alts.set(GuiAlts.altList.alts.indexOf(alt), newAlt);
            		}
            	}
				if(displayText.equals(""))
				{
					GuiAlts.altList.sortAlts();
					Client.Wurst.fileManager.saveAlts();
            		this.mc.displayGuiScreen(this.prevMenu);
                	GuiAlts.altList.elementClicked(GuiAlts.altList.alts.indexOf(newAlt), false, 0, 0);
				}else
        			this.errorTimer = 8;
            }else if (clickedButton.id == 3)
            {//Random name
                this.nameBox.setText(AltUtils.generateName());
            }else if (clickedButton.id == 4)
            {//Steal Skin
                displayText = AltUtils.stealSkin(newAlt.name);
            }
        }
    }

	/**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.nameBox.textboxKeyTyped(par1, par2);
        this.passwordBox.textboxKeyTyped(par1, par2);

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
        this.passwordBox.mouseClicked(par1, par2, par3);
        if(this.nameBox.isFocused() || this.passwordBox.isFocused())
        	displayText = "";
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        AltUtils.drawAltBack(this.nameBox.getText(), (this.width / 2 - 100) / 2 - 64, this.height / 2 - 128, 128, 256);
        AltUtils.drawAltBody(this.nameBox.getText(), this.width - (this.width / 2 - 100) / 2 - 64, this.height / 2 - 128, 128, 256);
        this.drawCenteredString(this.fontRendererObj, "Edit this Alt", this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, "Name or E-Mail", this.width / 2 - 100, 47, 10526880);
        this.drawString(this.fontRendererObj, "Password", this.width / 2 - 100, 87, 10526880);
        this.drawCenteredString(this.fontRendererObj, displayText, this.width / 2, 142, 16777215);
        this.nameBox.drawTextBox();
        this.passwordBox.drawTextBox();
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
