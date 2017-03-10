package tk.wurst_client.gui.servers;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.servers.ServerPinger;
import tk.wurst_client.utils.MiscUtils;

public class GuiServerFinder extends GuiScreen
{
    private GuiMultiplayer prevMenu;
    private GuiTextField ipBox;
    private GuiTextField maxThreadsBox;
	private boolean running;
	private int checked;
	private int working;
	private boolean terminated;

    public GuiServerFinder(GuiMultiplayer prevMultiplayerMenu)
    {
        this.prevMenu = prevMultiplayerMenu;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.ipBox.updateCursorCounter();
        ((GuiButton)this.buttonList.get(0)).enabled =
        (
        	this.ipBox.getText().trim().length() >= 7//1.1.1.1 has a length of 7
        	&& this.ipBox.getText().contains(".")//Must have dots
        	&& !this.ipBox.getText().contains(":")//Must not have a port
        	&& StringUtils.countMatches(this.ipBox.getText(), ".") == 3//Must have three dots
        	&& MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[0])//The first part must be an integer
        	&& MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[1])//And the second
        	&& MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[2])//And the third
        	&& MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[3])//And so on
        	&& !this.running
        	&& MiscUtils.isInteger(this.maxThreadsBox.getText())
        );
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Search"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
        this.ipBox = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 34, 200, 20);
        this.ipBox.setMaxStringLength(15);
        this.ipBox.setFocused(true);
        this.maxThreadsBox = new GuiTextField(this.fontRendererObj, this.width / 2 - 32, this.height / 4 + 58, 26, 12);
        this.maxThreadsBox.setMaxStringLength(3);
        this.maxThreadsBox.setFocused(false);
        this.maxThreadsBox.setText(Integer.toString(Client.Wurst.options.serverFinderThreads));
        this.running = false;
    	this.terminated = false;
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
    	this.terminated = true;
    	if(MiscUtils.isInteger(this.maxThreadsBox.getText()))
    	{
    		Client.Wurst.options.serverFinderThreads = Integer.valueOf(this.maxThreadsBox.getText());
    		Client.Wurst.fileManager.saveValues();
    	}
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton clickedButton)
    {
        if (clickedButton.enabled)
        {
        	if(clickedButton.id == 0)
            {//Search
            	if(MiscUtils.isInteger(this.maxThreadsBox.getText()))
            	{
            		Client.Wurst.options.serverFinderThreads = Integer.valueOf(this.maxThreadsBox.getText());
            		Client.Wurst.fileManager.saveValues();
            	}
        		this.running = true;
        		new Thread("Server Finder")
        		{
					public void run()
        			{
        				int[] ipParts = new int[4];
        				for(int i = 0; i < GuiServerFinder.this.ipBox.getText().split("\\.").length; i++)
        					ipParts[i] = Integer.valueOf(GuiServerFinder.this.ipBox.getText().split("\\.")[i]);
        				ArrayList<ServerPinger> pingers = new ArrayList<ServerPinger>();
        				serverFinder:
        				for(int i = 3; i >= 0; i--)
        				{
        					for(int i2 = 0; i2 <= 255; i2++)
        					{
        						if(GuiServerFinder.this.terminated)
        							break serverFinder;
        						int[] ipParts2 = ipParts.clone();
        						ipParts2[i] = i2;
        						String ip = ipParts2[0] + "." + ipParts2[1] + "." + ipParts2[2] + "." + ipParts2[3];
        						
        						ServerPinger pinger = new ServerPinger();
        						pinger.ping(ip);
        						pingers.add(pinger);
        						while(pingers.size() >= Client.Wurst.options.serverFinderThreads)
        							pingers = updatePingers(pingers);
        					}
        				}
        				while(pingers.size() > 0)
							pingers = updatePingers(pingers);
        			}
        		}.start();
            }else if(clickedButton.id == 1)
            {//Cancel
                this.mc.displayGuiScreen(this.prevMenu);
            }
        }
    }
    
    private ArrayList<ServerPinger> updatePingers(ArrayList<ServerPinger> pingers)
    {
    	for(int i = 0; i < pingers.size(); i++)
		{
			if(!pingers.get(i).isStillPinging())
			{
				GuiServerFinder.this.checked++;
				if(pingers.get(i).isWorking())
				{
					GuiServerFinder.this.working++;
					GuiServerFinder.this.prevMenu.savedServerList.addServerData(new ServerData("Grief me #" + this.working, pingers.get(i).server.serverIP));
					GuiServerFinder.this.prevMenu.savedServerList.saveServerList();
					GuiServerFinder.this.prevMenu.serverListSelector.setSelectedServer(-1);
					GuiServerFinder.this.prevMenu.serverListSelector.func_148195_a(GuiServerFinder.this.prevMenu.savedServerList);
				}
				pingers.remove(i);
			}
		}
		return pingers;
    }

	/**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.ipBox.textboxKeyTyped(par1, par2);
        this.maxThreadsBox.textboxKeyTyped(par1, par2);

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
        this.ipBox.mouseClicked(par1, par2, par3);
        this.maxThreadsBox.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Server Finder", this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, "This will search for servers with similar IPs", this.width / 2, 40, 10526880);
        this.drawCenteredString(this.fontRendererObj, "to the IP you type into the field below.", this.width / 2, 50, 10526880);
        this.drawCenteredString(this.fontRendererObj, "The servers it finds will be added to your server list.", this.width / 2, 60, 10526880);
        this.drawString(this.fontRendererObj, "Numeric IP without port", this.width / 2 - 100, this.height / 4 + 24, 10526880);
        this.ipBox.drawTextBox();
        this.drawString(this.fontRendererObj, "Max. threads:", this.width / 2 - 100, this.height / 4 + 60, 10526880);
        this.maxThreadsBox.drawTextBox();
        if(!((GuiButton)this.buttonList.get(0)).enabled)
        {
        	if(this.ipBox.getText().length() == 0)
        		this.drawCenteredString(this.fontRendererObj, "§4IP field is empty!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(this.ipBox.getText().contains(":"))
        		this.drawCenteredString(this.fontRendererObj, "§4Ports are not supported!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(!MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[0]))
        		this.drawCenteredString(this.fontRendererObj, "§4Hostnames are not supported!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(StringUtils.countMatches(this.ipBox.getText(), ".") >= 1 && !MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[1]))
        		this.drawCenteredString(this.fontRendererObj, "§4Hostnames are not supported!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(StringUtils.countMatches(this.ipBox.getText(), ".") >= 2 && !MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[2]))
        		this.drawCenteredString(this.fontRendererObj, "§4Hostnames are not supported!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(StringUtils.countMatches(this.ipBox.getText(), ".") >= 3 && !MiscUtils.isInteger(this.ipBox.getText().split("\\.", -1)[3]))
        		this.drawCenteredString(this.fontRendererObj, "§4Hostnames are not supported!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(StringUtils.countMatches(this.ipBox.getText(), ".") < 3)
        		this.drawCenteredString(this.fontRendererObj, "§4IP is too short!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(StringUtils.countMatches(this.ipBox.getText(), ".") > 3)
        		this.drawCenteredString(this.fontRendererObj, "§4IP is too long!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(!MiscUtils.isInteger(this.maxThreadsBox.getText()))
        		this.drawCenteredString(this.fontRendererObj, "§4Max. threads must be a number!", this.width / 2, this.height / 4 + 73, 10526880);
        	else if(this.running)
        		if(this.checked == 1024)
            		this.drawCenteredString(this.fontRendererObj, "§2Done!", this.width / 2, this.height / 4 + 73, 10526880);
        		else
        			this.drawCenteredString(this.fontRendererObj, "§2Searching...", this.width / 2, this.height / 4 + 73, 10526880);
        	else
        		this.drawCenteredString(this.fontRendererObj, "§4Unknown error! Bug?", this.width / 2, this.height / 4 + 73, 10526880);
        }
        this.drawString(this.fontRendererObj, "Checked: " + this.checked + " / 1024", this.width / 2 - 100, this.height / 4 + 84, 10526880);
        this.drawString(this.fontRendererObj, "Working: " + this.working, this.width / 2 - 100, this.height / 4 + 94, 10526880);
        super.drawScreen(par1, par2, par3);
    }
}
