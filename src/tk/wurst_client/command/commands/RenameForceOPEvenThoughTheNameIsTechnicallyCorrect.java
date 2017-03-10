package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;

public class RenameForceOPEvenThoughTheNameIsTechnicallyCorrect extends Command
{
	private static String[] commandHelp =
	{
		"You know what this does. Happy typing!",
		".RenameForceOPEvenThoughTheNameIsTechnicallyCorrect"
	};
	
	public RenameForceOPEvenThoughTheNameIsTechnicallyCorrect()
	{
		super("RenameForceOPEvenThoughTheNameIsTechnicallyCorrect", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		Client.Wurst.options.renameForceOPEvenThoughTheNameIsTechnicallyCorrect = !Client.Wurst.options.renameForceOPEvenThoughTheNameIsTechnicallyCorrect;
		Client.Wurst.fileManager.saveValues();
		Client.Wurst.chat.message("Congratulations! You spelled that correctly.");
		Client.Wurst.chat.message("Now you need to restart Wurst.");
	}
}
