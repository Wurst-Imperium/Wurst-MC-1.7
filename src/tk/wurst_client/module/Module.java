package tk.wurst_client.module;

import java.util.ArrayList;

import org.darkstorm.minecraft.gui.component.basic.BasicSlider;

import tk.wurst_client.Client;

public class Module
{
	private String moduleName;

	private String moduleDescription;
	
	private int moduleBind;
	
	private Category moduleCategory;
	
	private boolean isToggled;
	
	protected ArrayList<BasicSlider> moduleSliders = new ArrayList<BasicSlider>();
	
	private long currentMS = 0L;
	
	protected long lastMS = -1L;
	
	public Module(String moduleName, String moduleDescription, int moduleBind, Category moduleCategory)
	{
		this.moduleName = moduleName;
		this.moduleDescription = moduleDescription;
		this.moduleBind = moduleBind;
		this.moduleCategory = moduleCategory;
		this.initSliders();
	}
	
	public String getName()
	{
		return this.moduleName;
	}
	
	public String getRenderName()
	{
		return this.moduleName;
	}

	public String getDescription()
	{
		return this.moduleDescription;
	}
	
	public int getBind()
	{
		return this.moduleBind;
	}
	
	public Category getCategory()
	{
		return this.moduleCategory;
	}
	
	public boolean getToggled()
	{
		return this.isToggled;
	}
	
	public void setToggled(boolean shouldToggle)
	{
		this.onToggle();
		if(shouldToggle)
		{
			this.onEnable();
			this.isToggled = true;
		}else
		{
			this.onDisable();
			this.isToggled = false;
		}
		Client.Wurst.fileManager.saveModules();
	}
	
	public void toggleModule()
	{
		this.setToggled(!this.getToggled());
	}
	
	public void setBind(int newBind)
	{
		this.moduleBind = newBind;
	}
	
	public ArrayList<BasicSlider> getSliders()
	{
		return this.moduleSliders;
	}
	
	public void setSliders(ArrayList<BasicSlider> newSliders)
	{
		this.moduleSliders = newSliders;
	}
	
	public void noCheatMessage()
	{
		Client.Wurst.chat.warning(this.moduleName + " cannot bypass NoCheat+.");
	}
	
	public void updateMS()
	{
		this.currentMS = System.currentTimeMillis();
	}
	
	public void updateLastMS()
	{
		this.lastMS = System.currentTimeMillis();
	}
	
	public boolean hasTimePassedM(long MS)
	{
		return this.currentMS >= this.lastMS + MS;
	}
	
	public boolean hasTimePassedS(float speed)
	{
		return this.currentMS >= this.lastMS + (long)(1000 / speed);
	}
	
	public void onToggle(){}
	
	public void onEnable(){}
	
	public void onDisable(){}
	
	public void onUpdate(){}
	
	public void onRender(){}
	
	public void onRenderGUI(){}
	
	public void onDeath(){}
	
	/**
	 * Note: This runs before the swing animation.
	 */
	public void onLeftClick(){}
	
	public void initSliders(){}
	
	public void updateSettings(){}
	
	public void onReceivedMessage(String message){}
}
