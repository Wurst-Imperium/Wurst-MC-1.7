package tk.wurst_client.command;

import java.util.ArrayList;

import tk.wurst_client.command.commands.AddAlt;
import tk.wurst_client.command.commands.Annoy;
import tk.wurst_client.command.commands.Bind;
import tk.wurst_client.command.commands.Clear;
import tk.wurst_client.command.commands.Drop;
import tk.wurst_client.command.commands.Enchant;
import tk.wurst_client.command.commands.FastBreakSettings;
import tk.wurst_client.command.commands.Friends;
import tk.wurst_client.command.commands.GM;
import tk.wurst_client.command.commands.Help;
import tk.wurst_client.command.commands.IP;
import tk.wurst_client.command.commands.NukerSettings;
import tk.wurst_client.command.commands.RV;
import tk.wurst_client.command.commands.RenameForceOPEvenThoughTheNameIsTechnicallyCorrect;
import tk.wurst_client.command.commands.Say;
import tk.wurst_client.command.commands.SearchSettings;
import tk.wurst_client.command.commands.Stats;
import tk.wurst_client.command.commands.TP;
import tk.wurst_client.command.commands.Taco;
import tk.wurst_client.command.commands.ThrowSettings;
import tk.wurst_client.command.commands.Toggle;
import tk.wurst_client.command.commands.VClip;
import tk.wurst_client.command.commands.XRay;

public class CommandManager
{
	public ArrayList<Command> activeCommands = new ArrayList<Command>();
	
	public CommandManager()
	{
		this.activeCommands.add(new AddAlt());
		this.activeCommands.add(new Annoy());
		this.activeCommands.add(new Bind());
		this.activeCommands.add(new Clear());
		this.activeCommands.add(new Drop());
		this.activeCommands.add(new Enchant());
		this.activeCommands.add(new FastBreakSettings());
		this.activeCommands.add(new Friends());
		this.activeCommands.add(new GM());
		this.activeCommands.add(new Help());
		this.activeCommands.add(new IP());
		this.activeCommands.add(new NukerSettings());
		this.activeCommands.add(new RenameForceOPEvenThoughTheNameIsTechnicallyCorrect());
		this.activeCommands.add(new RV());
		this.activeCommands.add(new Say());
		this.activeCommands.add(new SearchSettings());
		this.activeCommands.add(new Stats());
		this.activeCommands.add(new Taco());
		this.activeCommands.add(new ThrowSettings());
		this.activeCommands.add(new Toggle());
		this.activeCommands.add(new TP());
		this.activeCommands.add(new VClip());
		this.activeCommands.add(new XRay());
	}
}