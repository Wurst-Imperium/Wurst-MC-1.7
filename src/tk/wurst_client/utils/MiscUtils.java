package tk.wurst_client.utils;

import java.awt.Desktop;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class MiscUtils
{
	private static final Logger logger = LogManager.getLogger();
	
	public static boolean isInteger(String s)
	{
	    try
	    { 
	        Integer.parseInt(s);
	    }catch(NumberFormatException e)
	    { 
	        return false; 
	    }
	    return true;
	}
	
	public static boolean openLink(String url)
	{
		try
        {
        	Desktop.getDesktop().browse(new URI(url));
        	return true;
        }
        catch (Exception e)
        {
        	logger.error("Failed to open link", e);
        	return false;
        }
	}
}
