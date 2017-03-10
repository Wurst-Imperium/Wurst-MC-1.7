package tk.wurst_client.utils;

import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConnector
{
    private static final AtomicInteger threadNumber = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    public NetworkManager networkManager;
    private final GuiScreen prevMenu;
	public Minecraft mc;
	public Connection connection;
	public String lastIP;
	public int lastPort;
    
    public ServerConnector(GuiScreen prevMenu, Minecraft mc)
    {
        this.mc = mc;
        this.prevMenu = prevMenu;
    }
    
    public enum Connection
    {
    	SUCCESSFUL,
    	FAILED;
    }
    
    public void connect(final String ip, final int port)
    {
        connect(ip, port, this.mc.getSession());
    }
    
    public void connect(final String ip, final int port, final Session session)
    {
    	this.lastIP = ip;
    	this.lastPort = port;
        new Thread("Wurst Server Connector #" + threadNumber.incrementAndGet())
        {
            public void run()
            {
                try
                {
                	ServerConnector.logger.info("Connecting to " + ip + ", " + port);
                    ServerConnector.this.networkManager = NetworkManager.provideLanClient(InetAddress.getByName(ip), port);
                    ServerConnector.this.networkManager.setNetHandler(new NetHandlerLoginClient(ServerConnector.this.networkManager, ServerConnector.this.mc, ServerConnector.this.prevMenu));
                    ServerConnector.this.networkManager.scheduleOutboundPacket(new C00Handshake(4, ip, port, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
                    ServerConnector.this.networkManager.scheduleOutboundPacket(new C00PacketLoginStart(session.func_148256_e()), new GenericFutureListener[0]);
                    ServerConnector.logger.info("Connection successful");
                    ServerConnector.this.connection = ServerConnector.Connection.SUCCESSFUL;
                }catch(UnknownHostException var2)
                {
                    ServerConnector.logger.error("Couldn\'t connect to server", var2);
                    ServerConnector.this.connection = ServerConnector.Connection.FAILED;
                }catch(Exception var3)
                {
                    ServerConnector.logger.error("Couldn\'t connect to server", var3);
                    ServerConnector.this.connection = ServerConnector.Connection.FAILED;
                }
            }
        }.start();
    }
}
