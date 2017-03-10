package tk.wurst_client.module.modules;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Timer extends Module
{
	public Timer()
	{
		super
		(
			"Timer",
			"Changes the speed of almost everything.\n"
			+ "Tip: Slow speeds make aiming easier and work well with\n"
			+ "NoCheat+.",
			0,
			Category.MOVEMENT
		);
	}
	
	public static float speed = 2.0F;//Minimum: 0.1F, maximum: 10.0F
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("Timer speed", speed, 0.1, 10, 0.1, ValueDisplay.DECIMAL));
	}
	
	public void updateSettings()
	{
		this.speed = (float) this.moduleSliders.get(0).getValue();
	}
}
