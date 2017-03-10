package tk.wurst_client.gui.options;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import tk.wurst_client.Client;
import tk.wurst_client.utils.MiscUtils;

public class GuiWurstOptions extends GuiScreen
{
	private GuiScreen prevMenu;
	private String[] arrayListModes = {"Auto", "Count", "Hidden"};
	private String[] toolTips =
	{
		"",
		"Manage your friends by clicking them\n"
		+ "with the middle mouse button.",
		"Work in progress mods.\n"
		+ "These mods are unstable and can cause\n"
		+ "problems. Changing this option requires\n"
		+ "a restart.",
		"How the mod list under the Wurst logo\n"
		+ "should be displayed.\n"
		+ "§nModes§r:\n"
		+ "§o§lAuto§r: Renders the whole list if it fits\n"
		+ "onto the screen.\n"
		+ "§o§lCount§r: Only renders the number of active\n"
		+ "mods.\n"
		+ "§o§lHidden§r: Renders nothing.",
		"",
		"",
		"Manager for the keybinds",
		"Manager for the blocks that X-Ray will\n"
		+ "show",
		"",
		"",
		"",
		"The official Website of the Wurst Client",
		"Frequently asked questions",
		"",
		"",
		"Online feedback survey",
	};
	private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00000703";

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
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 144 - 16, 200, 20, "Back"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 154, this.height / 4 + 24 - 16, 100, 20, "Click Friends: " + (Client.Wurst.options.middleClickFriends ? "ON" : "OFF")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 154, this.height / 4 + 48 - 16, 100, 20, "WIP Mods: " + (Client.Wurst.options.WIP ? "ON" : "OFF")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 154, this.height / 4 + 72 - 16, 100, 20, "ArrayList: " + arrayListModes[Client.Wurst.options.arrayListMode]));
        //this.buttonList.add(new GuiButton(4, this.width / 2 - 154, this.height / 4 + 96 - 16, 100, 20, "???"));
        //this.buttonList.add(new GuiButton(5, this.width / 2 - 154, this.height / 4 + 120 - 16, 100, 20, "???"));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 50, this.height / 4 + 24 - 16, 100, 20, "Keybinds"));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 50, this.height / 4 + 48 - 16, 100, 20, "X-Ray Blocks"));
        //this.buttonList.add(new GuiButton(8, this.width / 2 - 50, this.height / 4 + 72 - 16, 100, 20, "???"));
        //this.buttonList.add(new GuiButton(9, this.width / 2 - 50, this.height / 4 + 96 - 16, 100, 20, "???"));
        //this.buttonList.add(new GuiButton(10, this.width / 2 - 50, this.height / 4 + 120 - 16, 100, 20, "???"));
        this.buttonList.add(new GuiButton(11, this.width / 2 + 54, this.height / 4 + 24 - 16, 100, 20, "Wurst Website"));
        this.buttonList.add(new GuiButton(12, this.width / 2 + 54, this.height / 4 + 48 - 16, 100, 20, "FAQ"));
        this.buttonList.add(new GuiButton(13, this.width / 2 + 54, this.height / 4 + 72 - 16, 100, 20, "Report a Bug"));
        this.buttonList.add(new GuiButton(14, this.width / 2 + 54, this.height / 4 + 96 - 16, 100, 20, "Suggest a Feature"));
        this.buttonList.add(new GuiButton(15, this.width / 2 + 54, this.height / 4 + 120 - 16, 100, 20, "Give Feedback"));
    }

    protected void actionPerformed(GuiButton clickedButton)
    {
		if(clickedButton.enabled)
        {
            if(clickedButton.id == 0)
            {//Cancel
            	this.mc.displayGuiScreen(this.prevMenu);
            }else if(clickedButton.id == 1)
            {//Middle Click Friends
            	Client.Wurst.options.middleClickFriends = !Client.Wurst.options.middleClickFriends;
            	clickedButton.displayString = "Click Friends: " + (Client.Wurst.options.middleClickFriends ? "ON" : "OFF");
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 2)
            {//WIP
            	Client.Wurst.options.WIP = !Client.Wurst.options.WIP;
            	clickedButton.displayString = "WIP Mods: " + (Client.Wurst.options.WIP ? "ON" : "OFF");
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 3)
            {//ArrayList
            	Client.Wurst.options.arrayListMode++;
            	if(Client.Wurst.options.arrayListMode > 2)
            		Client.Wurst.options.arrayListMode = 0;
            	clickedButton.displayString = "ArrayList: " + arrayListModes[Client.Wurst.options.arrayListMode];
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 4)
            {
            	
            }else if(clickedButton.id == 5)
            {
            	
            }else if(clickedButton.id == 6)
            {//Keybinds
            	this.mc.displayGuiScreen(new GuiKeybindManager(this));
            }else if(clickedButton.id == 7)
            {//X-Ray Blocks
            	this.mc.displayGuiScreen(new GuiXRayBlocksManager(this));
            }else if(clickedButton.id == 8)
            {
            	
            }else if(clickedButton.id == 9)
            {
            	
            }else if(clickedButton.id == 10)
            {
            	
            }else if(clickedButton.id == 11)
            {//Website
            	MiscUtils.openLink("http://www.wurst-client.tk/");
            }else if(clickedButton.id == 12)
            {//FAQ
            	MiscUtils.openLink("http://www.wurst-client.tk/faq");
            }else if(clickedButton.id == 13)
            {//Bug Report
            	MiscUtils.openLink("http://www.wurst-client.tk/bugs");
            }else if(clickedButton.id == 14)
            {//Suggestion
            	MiscUtils.openLink("http://www.wurst-client.tk/ideas");
            }else if(clickedButton.id == 15)
            {//Feedback
            	MiscUtils.openLink("https://www.surveymonkey.com/r/QDTKZDY");
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
        this.drawCenteredString(this.fontRendererObj, "Wurst Options", this.width / 2, 40, 0xffffff);
        this.drawCenteredString(this.fontRendererObj, "Settings", this.width / 2 - 104, 56, 0xcccccc);
        this.drawCenteredString(this.fontRendererObj, "Managers", this.width / 2, 56, 0xcccccc);
        this.drawCenteredString(this.fontRendererObj, "Online", this.width / 2 + 104, 56, 0xcccccc);
        super.drawScreen(par1, par2, par3);
    	for(int i = 0; i < buttonList.size(); i++)
		{
			GuiButton button = (GuiButton)this.buttonList.get(i);
			if(button.isMouseOver() && !toolTips[button.id].isEmpty())
			{
				ArrayList toolTip = Lists.newArrayList(toolTips[button.id].split("\n"));
				this.drawHoveringText(toolTip, par1, par2);
				break;
			}
		}
    }
}
