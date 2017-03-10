package tk.wurst_client.gui.servers;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;

import com.google.common.collect.Lists;

public class GuiCleanUp extends GuiScreen
{
    private GuiMultiplayer prevMenu;
	private boolean removeAll;
	private String[] toolTips =
	{
		"",
		"Start the Clean Up with the settings\n"
		+ "you specified above.\n"
		+ "It might look like the game is not\n"
		+ "reacting for a couple of seconds.",
		"Servers that clearly don't exist.",
		"Servers that run a different Minecraft\n"
		+ "version than you.",
		"All servers that failed the last ping.\n"
		+ "Make sure that the last ping is complete\n"
		+ "before you do this. That means: Go back,\n"
		+ "press the refresh button and wait until\n"
		+ "all servers are done refreshing.",
		"This will completely clear your server\n"
		+ "list. §cUse with caution!§r",
		"Renames your servers to \"Grief me #1\",\n"
		+ "\"Grief me #2\", etc.",
	};

    public GuiCleanUp(GuiMultiplayer prevMultiplayerMenu)
    {
        this.prevMenu = prevMultiplayerMenu;
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
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 144 + 12, "Cancel"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Clean Up"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 - 24 + 12, "Unknown Hosts: " + removeOrKeep(Client.Wurst.options.cleanupUnknown)));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 0 + 12, "Outdated Servers: " + removeOrKeep(Client.Wurst.options.cleanupOutdated)));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + 12, "Failed Ping: " + removeOrKeep(Client.Wurst.options.cleanupFailed)));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + 12, "§cRemove all Servers: " + yesOrNo(removeAll)));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 72 + 12, "Rename all Servers: " + yesOrNo(Client.Wurst.options.cleanupRename)));
    }
    
    private String yesOrNo(boolean bool)
	{
		return bool ? "Yes" : "No";
	}
    
    private String removeOrKeep(boolean bool)
	{
		return bool ? "Remove" : "Keep";
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
        	if(clickedButton.id == 0)
            {//Cancel
                mc.displayGuiScreen(prevMenu);
            }else if(clickedButton.id == 1)
            {//Clean Up
            	if(removeAll)
            	{
            		prevMenu.savedServerList.clearServerList();
					prevMenu.savedServerList.saveServerList();
					prevMenu.serverListSelector.setSelectedServer(-1);
					prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
	            	mc.displayGuiScreen(prevMenu);
	            	return;
            	}
            	for(int i = prevMenu.savedServerList.countServers() - 1; i >= 0; i--)
				{
					ServerData server = prevMenu.savedServerList.getServerData(i);
					if
					(
						(Client.Wurst.options.cleanupUnknown && server.serverMOTD != null && server.serverMOTD.equals(EnumChatFormatting.DARK_RED + "Can\'t resolve hostname"))
						|| (Client.Wurst.options.cleanupOutdated && server.version != 4 && server.version != 5)
						|| (Client.Wurst.options.cleanupFailed && server.pingToServer != -2L && server.pingToServer < 0L)
					)
					{
						prevMenu.savedServerList.removeServerData(i);
						prevMenu.savedServerList.saveServerList();
						prevMenu.serverListSelector.setSelectedServer(-1);
						prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
					}
				}
            	if(Client.Wurst.options.cleanupRename)
            	{
            		for(int i = 0; i < prevMenu.savedServerList.countServers(); i++)
    				{
    					ServerData server = prevMenu.savedServerList.getServerData(i);
    					server.serverName = "Grief me #" + (i + 1);
    					prevMenu.savedServerList.saveServerList();
    					prevMenu.serverListSelector.setSelectedServer(-1);
    					prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
    				}
            	}
            	mc.displayGuiScreen(prevMenu);
            }else if(clickedButton.id == 2)
            {//Unknown host
            	Client.Wurst.options.cleanupUnknown = !Client.Wurst.options.cleanupUnknown;
            	clickedButton.displayString = "Unknown Hosts: " + removeOrKeep(Client.Wurst.options.cleanupUnknown);
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 3)
            {//Outdated
            	Client.Wurst.options.cleanupOutdated = !Client.Wurst.options.cleanupOutdated;
            	clickedButton.displayString = "Outdated Servers: " + removeOrKeep(Client.Wurst.options.cleanupOutdated);
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 4)
            {//Failed ping
            	Client.Wurst.options.cleanupFailed = !Client.Wurst.options.cleanupFailed;
            	clickedButton.displayString = "Failed Ping: " + removeOrKeep(Client.Wurst.options.cleanupFailed);
            	Client.Wurst.fileManager.saveValues();
            }else if(clickedButton.id == 5)
            {//Remove
            	removeAll = !removeAll;
            	clickedButton.displayString = "§cRemove all Servers: " + yesOrNo(removeAll);
            }else if(clickedButton.id == 6)
            {//Rename
            	Client.Wurst.options.cleanupRename = !Client.Wurst.options.cleanupRename;
            	clickedButton.displayString = "Rename all Servers: " + yesOrNo(Client.Wurst.options.cleanupRename);
            	Client.Wurst.fileManager.saveValues();
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
        this.drawCenteredString(this.fontRendererObj, "Clean Up", this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, "Please select the servers you want to remove:", this.width / 2, 36, 10526880);
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
