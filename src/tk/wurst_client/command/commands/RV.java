package tk.wurst_client.command.commands;

import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.RemoteView;

public class RV extends Command
{
	private static String[] commandHelp =
		{
			"Toggles RemoteView or makes it target a specific entity.",
			".rv",
			".rv <Player>"
		};
	
	public RV()
	{
		super("rv", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			RemoteView.onEnabledByCommand("");
			return;
		}
		else
			RemoteView.onEnabledByCommand(args[0]);
	}
}
