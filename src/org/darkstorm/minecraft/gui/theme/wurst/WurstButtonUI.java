package org.darkstorm.minecraft.gui.theme.wurst;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import net.minecraft.client.Minecraft;

import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class WurstButtonUI extends AbstractComponentUI<Button> {
	private final WurstTheme theme;
	private long lastMS;
	private Button describedButton;
	private Button rightButton;

	WurstButtonUI(WurstTheme theme) {
		super(Button.class);
		this.theme = theme;

		foreground = Color.WHITE;
		background = new Color(0, 0, 0, 0);
	}

	@Override
	protected void renderComponent(Button button) {
		translateComponent(button, false);
		Rectangle area = button.getArea();
		area.width = button.getParent().getWidth() - 4;
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		glDisable(GL_TEXTURE_2D);
		RenderUtil.setColor(button.getBackgroundColor());
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width, 0);
			glVertex2d(area.width, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();
		RenderUtil.setColor(new Color(0, 0, 0, 255));
		glLineWidth(1.5F);
		glBegin(GL_LINE_LOOP);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width, 0);
			glVertex2d(area.width, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();
		Point mouse = RenderUtil.calculateMouseLocation();
		Point rawMouse = RenderUtil.calculateMouseLocation();
		int fontHeight = theme.getFontRenderer().FONT_HEIGHT;
		Component parent = button.getParent();
		while(parent != null) {
			mouse.x -= parent.getX();
			mouse.y -= parent.getY();
			parent = parent.getParent();
		}
		if(area.contains(mouse) && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
		{
			glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.3f : 0.2f);
			glBegin(GL_QUADS);
			{
				glVertex2d(0, 0);
				glVertex2d(area.width, 0);
				glVertex2d(area.width, area.height);
				glVertex2d(0, area.height);
			}
			glEnd();
		}
		String text = button.getText();
		theme.getFontRenderer().drawString(
				text,
				area.width / 2 - theme.getFontRenderer().getStringWidth(text)
				/ 2,
				area.height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2,
				RenderUtil.toRGBA(button.getForegroundColor()));
		translateComponent(button, true);
		if(area.contains(mouse) && describedButton != button)
		{
			lastMS = 0L;
			describedButton = button;
		}
		if(lastMS == 0L && describedButton != null && isRightButton(button, describedButton))
		{
			lastMS = System.currentTimeMillis();
			rightButton = button;
			return;
		}
		if(System.currentTimeMillis() < lastMS + 500)
			return;
		if(describedButton != null && rightButton == button && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
		{
			String[] lines = describedButton.getDescription().split("\n");
			int textWidth = 0;
			for(String line : lines)
			{
				int lineWidth = theme.getFontRenderer().getStringWidth(line);
				if(lineWidth > textWidth)
					textWidth = lineWidth;
			}
			int textHeight = (theme.getFontRenderer().FONT_HEIGHT + 2) * lines.length;
			Rectangle dArea = describedButton.getArea();
			dArea.width = describedButton.getParent().getWidth() - 4;
			for(Module module : Client.Wurst.moduleManager.activeModules)
			{
				if(button.getText().equals(module.getName()))
				{
					for(org.darkstorm.minecraft.gui.component.Frame frame : Client.Wurst.guiManager.getFrames())
					{
						if(frame.getTitle().equalsIgnoreCase(module.getCategory().name()))
							Client.Wurst.guiManager.bringForward(frame);
					}
				}
			}
			int scale = Minecraft.getMinecraft().gameSettings.guiScale;
			if(scale == 0)
				scale = 1000;
			int scaleFactor = 0;
			while(scaleFactor < scale && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240)
				scaleFactor++;
			if(rawMouse.x + 8 + textWidth > Minecraft.getMinecraft().displayWidth / scaleFactor)
				rawMouse.x -= textWidth + 16;
			if(rawMouse.y - 5 + textHeight > Minecraft.getMinecraft().displayHeight / scaleFactor)
				rawMouse.y -= textHeight - 8;
			glEnable(GL_BLEND);
			glDisable(GL_CULL_FACE);
			glDisable(GL_TEXTURE_2D);
			RenderUtil.setColor(new Color(64, 64, 64, 192));
			glBegin(GL_QUADS);
			{
				glVertex2d(rawMouse.x + 6, rawMouse.y - 5);
				glVertex2d(rawMouse.x + textWidth + 11, rawMouse.y - 5);
				glVertex2d(rawMouse.x + textWidth + 11, rawMouse.y - 5 + textHeight);
				glVertex2d(rawMouse.x + 6, rawMouse.y - 5 + textHeight);
			}
			glEnd();
			glLineWidth(1.0F);
			RenderUtil.setColor(new Color(0, 0, 0, 255));
			glBegin(GL_LINE_LOOP);
			{
				glVertex2d(rawMouse.x + 6, rawMouse.y - 5);
				glVertex2d(rawMouse.x + textWidth + 11, rawMouse.y - 5);
				glVertex2d(rawMouse.x + textWidth + 11, rawMouse.y - 5 + textHeight);
				glVertex2d(rawMouse.x + 6, rawMouse.y - 5 + textHeight);
			}
			glEnd();
			for(int i = 0; i < lines.length; i++)
				theme.getFontRenderer().drawString(lines[i], rawMouse.x + 8, rawMouse.y - 5 + (theme.getFontRenderer().FONT_HEIGHT + 2) * i, RenderUtil.toRGBA(Color.WHITE));
		}
		if(!area.contains(mouse) && describedButton == button)
		{
			lastMS = 0L;
			describedButton = null;
			rightButton = null;
		}
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	private boolean isRightButton(Button button, Button dButton)
	{
		Category buttonCategory = null;
		Category dButtonCategory = null;
		buttonCategory = Client.Wurst.moduleManager.getModuleByName(button.getText()).getCategory();
		dButtonCategory = Client.Wurst.moduleManager.getModuleByName(dButton.getText()).getCategory();
		boolean isRightFrame = buttonCategory == dButtonCategory && buttonCategory != null;
		if(!isRightFrame)
			return false;
		boolean isLastButton = false;
		for(Module module : Client.Wurst.moduleManager.activeModules)
		{
			if(buttonCategory == module.getCategory())
			{
				if(button.getText().equals(module.getName()))
					isLastButton = true;
				else
					isLastButton = false;
			}
		}
		return isLastButton;
	}

	@Override
	protected Dimension getDefaultComponentSize(Button component) {
		return new Dimension(theme.getFontRenderer().getStringWidth(
				component.getText()) + 4,
				theme.getFontRenderer().FONT_HEIGHT + 4);
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(Button component) {
		return new Rectangle[] { new Rectangle(0, 0, component.getWidth(),
				component.getHeight()) };
	}

	@Override
	protected void handleComponentInteraction(Button component, Point location,
			int button) {
		if(location.x <= component.getWidth()
				&& location.y <= component.getHeight() && button == 0)
			component.press();
	}
}