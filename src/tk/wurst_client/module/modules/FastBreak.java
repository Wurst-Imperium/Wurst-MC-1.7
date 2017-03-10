package tk.wurst_client.module.modules;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class FastBreak extends Module
{
	public FastBreak()
	{
		super
		(
			"FastBreak",
			"Allows you to break blocks faster.\n"
			+ "Tip: This works with Nuker.",
			Keyboard.KEY_B,
			Category.BLOCKS
		);
	}
	
	public static float speed = 2;
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("FastBreak speed", speed, 1, 5, 0.05, ValueDisplay.DECIMAL));
	}
	
	public void updateSettings()
	{
		this.speed = (int) this.moduleSliders.get(0).getValue();
	}
}
