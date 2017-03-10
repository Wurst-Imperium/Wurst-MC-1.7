package tk.wurst_client.servers;

import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPinger
{
	private static final AtomicInteger threadNumber = new AtomicInteger(0);
	public static final Logger logger = LogManager.getLogger();
	public ServerData server;
	private boolean done = false;
	private boolean failed = false;
	
	public void ping(final String ip)
	{
		ping(ip, 25565);
	}
	
	public void ping(final String ip, final int port)
	{
		server = new ServerData("", ip + ":" + port);
		new Thread("Wurst Server Connector #" + threadNumber.incrementAndGet())
		{
			@Override
			public void run()
			{
				OldServerPinger pinger = new OldServerPinger();
				try
				{
					logger.info("Pinging " + ip + ":" + port + "...");
					pinger.ping(server);
					logger.info("Ping successful: " + ip + ":" + port);
				}catch(UnknownHostException e)
				{
					logger.info("Unknown host: " + ip + ":" + port);
					failed = true;
				}catch(Exception e2)
				{
					logger.info("Ping failed: " + ip + ":" + port);
					failed = true;
				}
				pinger.clearPendingNetworks();
				done = true;
			}
		}.start();
	}
	
	public boolean isStillPinging()
	{
		return !done;
	}
	
	public boolean isWorking()
	{
		return !failed;
	}
	
	public boolean isOtherVersion()
	{
		return server.version != 4 && server.version != 5;
	}
}
