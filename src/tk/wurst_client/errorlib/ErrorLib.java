package tk.wurst_client.errorlib;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ErrorLib
{
	private static JButton linkButton;
	public static String error = "";
	public static final String defaultReportLink = "http://www.wurst-client.tk/bugs";
	public static String reportLink = defaultReportLink;
	
	public static void crash(String name, String version, Exception e)
	{
		JFrame frame = new JFrame();
		frame.setTitle("Error");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setContentPane(createReport(name, version, e));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void crash(String name, String version, JDialog parent, Exception e)
	{
		JFrame frame = new JFrame();
		frame.setTitle("Error");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setContentPane(createReport(name, version, e));
		frame.pack();
		frame.setLocationRelativeTo(parent);
		if(parent != null)
			parent.dispose();
		frame.setVisible(true);
	}
	
	public static void crash(String name, String version, JFrame parent, Exception e)
	{
		JFrame frame = new JFrame();
		frame.setTitle("Error");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setContentPane(createReport(name, version, e));
		frame.pack();
		frame.setLocationRelativeTo(parent);
		if(parent != null)
			parent.dispose();
		frame.setVisible(true);
	}
	
	private static JPanel createReport(final String name, final String version, final Exception e)
	{
		e.printStackTrace();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel header = new JLabel();
		header.setText
		(
			"<html><div align=\"center\">" + name + " has crashed!<br>"
			+ "Please report the following error at:"
		);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(header);
		
		linkButton = new JButton("<html><u>" + reportLink);
		linkButton.setForeground(new Color(0x0000ff));
		linkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		linkButton.setBorder(null);
		linkButton.setContentAreaFilled(false);
		linkButton.setFocusPainted(false);
		linkButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI(reportLink));
					linkButton.setForeground(new Color(0x551A8B));
				}catch(Exception e1)
				{
					error();
					e1.printStackTrace();
				}
			}
		});
		panel.add(linkButton);
		
		JTextArea message = new JTextArea();
		message.setEditable(false);
		error += "---- " + name + " crash report ----";
		error += "\n" + name + " version: " + version;
		error += "\nTime: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").format(new Date());
		error += "\nOperating system: " + System.getProperty("os.name") + " version " + System.getProperty("os.version");
		error += "\nJava version: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
		error += "\n\nStack trace:";
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		error += "\n" + writer.toString();
		message.setText(error);
		JScrollPane scrollpane = new JScrollPane(message);
		panel.add(scrollpane);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 32, 5));
		
		JButton copyButton = new JButton("Copy to clipboard");
		copyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(error), null);
			}
		});
		buttons.add(copyButton);
		
		JButton saveButton = new JButton("Save as file");
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new SwingWorker()
				{
					@Override
					protected Object doInBackground() throws Exception
					{
						JFileChooser fileChooser = new JFileChooser()
						{
							@Override
							protected JDialog createDialog(Component parent) throws HeadlessException
							{
								JDialog dialog = super.createDialog(parent);
								dialog.setAlwaysOnTop(true);
								return dialog;
							}
						};
						fileChooser.setSelectedFile(new File(new SimpleDateFormat("'crash'-yyyy-MM-dd_HH.mm.ss-'" + name + ".txt'").format(new Date())));
						fileChooser.setCurrentDirectory(new File("."));
						fileChooser.setAcceptAllFileFilterUsed(false);
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Plain text file", "txt"));
						int choice = fileChooser.showSaveDialog(null);
						if(choice == JFileChooser.APPROVE_OPTION)
						{
							File file = fileChooser.getSelectedFile();
							if(!file.getPath().endsWith(".txt"))
								file = new File(file.getPath() + ".txt");
							PrintWriter save = new PrintWriter(new FileWriter(file));
							for(String line : error.split("\n"))
								save.println(line);
							save.close();
						}
						return null;
					}
				}.execute();
			}
		});
		buttons.add(saveButton);
		
		panel.add(buttons);
		
		return panel;
	}
	
	public static void error()
	{
		error(null, null);
	}
	
	public static void error(Component parent)
	{
		error(parent, null);
	}
	
	public static void error(Component parent, Exception e)
	{
		JOptionPane.showMessageDialog(parent, "An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
		if(e != null)
			e.printStackTrace();
	}
	
	public static void error(Exception e)
	{
		error(null, e);
	}
	
	public static void errorError()
	{
		errorError(null, null);
	}
	
	public static void errorError(Component parent)
	{
		errorError(parent, null);
	}
	
	public static void errorError(Component parent, Exception e)
	{
		JOptionPane.showMessageDialog(parent, "An error occurred while displaying the previous error.", "Error error", JOptionPane.ERROR_MESSAGE);
		if(e != null)
			e.printStackTrace();
	}
	
	public static void errorError(Exception e)
	{
		errorError(null, e);
	}
}
