package tk.wurst_client.update;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Alexander01998
 */
public class Updater
{
	public String latestVersion;
	public boolean updateAvailable;
	/**Uses the normal domain.*/
	public String downloadLink = "http://www.wurst-client.tk/download";
	/**Uses the Google sites domain for a secure HTTPS connection.*/
	private String versionsLink = "https://sites.google.com/site/wurstclient/client/versions";
	
	/**
	 * Checks if the current version of a project is the latest version.
	 * @author Alexander01998
	 * @param projectName
	 * @param currentVersion
	 */
	public boolean check(String projectName, String currentVersion)
	{
		try
		{
			if(currentVersion.contains("(pre-release)"))//Development pre-release - ignores updates.
			{
				latestVersion = null;
			}else if(currentVersion.contains("pre-release"))//Public pre-release - searches for the latest version (-l).
			{
				latestVersion = getVersion(projectName + "-l");
				if(latestVersion.equals(""))
					latestVersion = getVersion(projectName);
			}else
				latestVersion = getVersion(projectName);
			
			if(latestVersion == null)
				updateAvailable = false;
			else if(latestVersion.equals(currentVersion))
				updateAvailable = false;
			else
				updateAvailable = true;
			return updateAvailable;
		}catch(Exception e)
		{
			e.printStackTrace();
			updateAvailable = false;
			return false;
		}
	}
	
	/**
	 * Gets the latest version of a project.
	 * @author Alexander01998
	 * @param projectName
	 */
	private String getVersion(String projectName)
	{
		HttpsURLConnection connection = null;
		try
		{
			URL url = new URL(versionsLink);
			connection = (HttpsURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.connect();
			
			InputStream replyStream = connection.getInputStream();
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(replyStream));
			StringBuffer stringbuffer = new StringBuffer();
			String streamLine;
			while ((streamLine = streamReader.readLine()) != null)
			{
				stringbuffer.append(streamLine);
				stringbuffer.append('\r');
			}
			streamReader.close();
			if(connection != null)
				connection.disconnect();
			String reply = stringbuffer.toString();
			
			reply = URLDecoder.decode(reply, "UTF-8");
			String versions = reply.substring(reply.indexOf("§{") + 2, reply.indexOf("}$"));
			versions = versions.replaceAll("\\<.*?>", "");
			
			String version = versions.substring(versions.indexOf("§" + projectName + ":"));
			version = version.substring(1, version.indexOf("$"));
			version = version.substring(version.indexOf("'") + 1, version.lastIndexOf("'"));
			
			return version;
		}catch(Exception e)
		{
			if(connection != null)
				connection.disconnect();
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Opens the download link for the new version in the browser.
	 * @author Alexander01998
	 * @return Whether or not it was successful.
	 */
	public boolean openUpdateLink()
	{
		try
        {
        	Desktop.getDesktop().browse(new URI(downloadLink));
        	return true;
        }catch(Throwable e)
        {
        	return false;
        }
	}
}
