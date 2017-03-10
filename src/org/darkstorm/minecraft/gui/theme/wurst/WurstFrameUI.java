package org.darkstorm.minecraft.gui.theme.wurst;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
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

import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;

public class WurstFrameUI extends AbstractComponentUI<Frame> {
	private final WurstTheme theme;

	WurstFrameUI(WurstTheme theme) {
		super(Frame.class);
		this.theme = theme;

		foreground = Color.WHITE;
		background = new Color(64, 64, 64, 128);
	}

	@Override
	protected void renderComponent(Frame component) {
		Rectangle area = new Rectangle(component.getArea());
		int fontHeight = theme.getFontRenderer().FONT_HEIGHT;
		translateComponent(component, false);
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Draw frame background
		if(component.isMinimized())
			area.height = fontHeight + 4;
		RenderUtil.setColor(new Color(8, 8, 8, component.getBackgroundColor().getAlpha()));
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width, 0);
			glVertex2d(area.width, fontHeight + 4);
			glVertex2d(0, fontHeight + 4);
		}
		glEnd();
		RenderUtil.setColor(component.getBackgroundColor());
		glBegin(GL_QUADS);
		{
			glVertex2d(0, fontHeight + 4);
			glVertex2d(area.width, fontHeight + 4);
			glVertex2d(area.width, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();

		// Draw controls
		int offset = component.getWidth() - 2;
		Point mouse = RenderUtil.calculateMouseLocation();
		Component parent = component;
		while(parent != null) {
			mouse.x -= parent.getX();
			mouse.y -= parent.getY();
			parent = parent.getParent();
		}
		boolean[] checks = new boolean[] { component.isClosable(),
				component.isPinnable(), component.isMinimizable() };
		boolean[] overlays = new boolean[] { component.isClosable(), !component.isPinned(),
				component.isMinimized() };
		for(int i = 0; i < checks.length; i++) {
			if(!checks[i])
				continue;
			RenderUtil.setColor(component.getBackgroundColor());
			glBegin(GL_QUADS);
			{
				glVertex2d(offset - fontHeight, 2);
				glVertex2d(offset, 2);
				glVertex2d(offset, fontHeight + 2);
				glVertex2d(offset - fontHeight, fontHeight + 2);
			}
			glEnd();
			if( i == 1 && overlays[i])//UI for the pin button:
			{//If it is not pinned:
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight / 3, 2);
					glVertex2d(offset, fontHeight / 3 + 2);
					glVertex2d(offset - fontHeight / 3, fontHeight / 3 * 2 + 2);
					glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 + 2);
				}
				glEnd();
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight / 3 * 2 - 1, fontHeight / 3 + 1);
					glVertex2d(offset - fontHeight / 3 + 1, fontHeight / 3 * 2 + 3);
					glVertex2d(offset - fontHeight / 3, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 * 2 - 2, fontHeight / 3 + 2);
				}
				glEnd();
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 + 4);
					glVertex2d(offset - fontHeight / 3 - 2, fontHeight / 3 * 2 + 2);
					glVertex2d(offset - fontHeight + 1.5, fontHeight + 0.5);
				}
				glEnd();
				RenderUtil.setColor(new Color(0, 255, 0, 64));
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight / 3, 2);
					glVertex2d(offset, fontHeight / 3 + 2);
					glVertex2d(offset - fontHeight / 3, fontHeight / 3 * 2 + 2);
					glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 + 2);
				}
				glEnd();
				RenderUtil.setColor(new Color(0, 255, 0, 64));
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight / 3 * 2 - 1, fontHeight / 3 + 1);
					glVertex2d(offset - fontHeight / 3 + 1, fontHeight / 3 * 2 + 3);
					glVertex2d(offset - fontHeight / 3, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 * 2 - 2, fontHeight / 3 + 2);
				}
				glEnd();
				RenderUtil.setColor(new Color(192, 192, 192, 192));
				glBegin(GL_TRIANGLES);
				{
					glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 + 4);
					glVertex2d(offset - fontHeight / 3 - 2, fontHeight / 3 * 2 + 2);
					glVertex2d(offset - fontHeight + 1.5, fontHeight + 0.5);
				}
				glEnd();
				if(mouse.x >= offset - fontHeight && mouse.x <= offset && mouse.y >= 2 && mouse.y <= fontHeight + 2  && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
				{
					RenderUtil.setColor(new Color(0, 255, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight / 3, 2);
						glVertex2d(offset, fontHeight / 3 + 2);
						glVertex2d(offset - fontHeight / 3, fontHeight / 3 * 2 + 2);
						glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 + 2);
					}
					glEnd();
					RenderUtil.setColor(new Color(0, 255, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight / 3 * 2 - 1, fontHeight / 3 + 1);
						glVertex2d(offset - fontHeight / 3 + 1, fontHeight / 3 * 2 + 3);
						glVertex2d(offset - fontHeight / 3, fontHeight / 3 * 2 + 4);
						glVertex2d(offset - fontHeight / 3 * 2 - 2, fontHeight / 3 + 2);
					}
					glEnd();
					RenderUtil.setColor(new Color(255, 255, 255, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_TRIANGLES);
					{
						glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 + 4);
						glVertex2d(offset - fontHeight / 3 - 2, fontHeight / 3 * 2 + 2);
						glVertex2d(offset - fontHeight + 1.5, fontHeight + 0.5);
					}
					glEnd();
				}
			}else if(i == 1)
			{//If it is pinned:
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight / 3 * 2 - 1.5, fontHeight / 3 + 2);
					glVertex2d(offset - fontHeight / 3 + 0.5, fontHeight / 3 + 2);
					glVertex2d(offset - fontHeight / 3 + 0.5, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 * 2 - 1.5, fontHeight / 3 * 2 + 4);
				}
				glEnd();
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight / 3 * 2 - 2.5, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 + 1.5, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 + 1.5, fontHeight / 3 * 2 + 5);
					glVertex2d(offset - fontHeight / 3 * 2 - 2.5, fontHeight / 3 * 2 + 5);
				}
				glEnd();
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 * 2 + 5.1);
					glVertex2d(offset - fontHeight / 3 * 2 + 1.5, fontHeight / 3 * 2 + 5.1);
					glVertex2d(offset - fontHeight / 3 * 2 + 1.3, fontHeight + 2);
					glVertex2d(offset - fontHeight / 3 * 2 + 0.2, fontHeight + 2);
				}
				glEnd();
				RenderUtil.setColor(new Color(255, 0, 0, 64));
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight / 3 * 2 - 1.5, fontHeight / 3 + 2);
					glVertex2d(offset - fontHeight / 3 + 0.5, fontHeight / 3 + 2);
					glVertex2d(offset - fontHeight / 3 + 0.5, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 * 2 - 1.5, fontHeight / 3 * 2 + 4);
				}
				glEnd();
				RenderUtil.setColor(new Color(255, 0, 0, 64));
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight / 3 * 2 - 2.5, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 + 1.5, fontHeight / 3 * 2 + 4);
					glVertex2d(offset - fontHeight / 3 + 1.5, fontHeight / 3 * 2 + 5);
					glVertex2d(offset - fontHeight / 3 * 2 - 2.5, fontHeight / 3 * 2 + 5);
				}
				glEnd();
				RenderUtil.setColor(new Color(192, 192, 192, 192));
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 * 2 + 5.1);
					glVertex2d(offset - fontHeight / 3 * 2 + 1.5, fontHeight / 3 * 2 + 5.1);
					glVertex2d(offset - fontHeight / 3 * 2 + 1.3, fontHeight + 2);
					glVertex2d(offset - fontHeight / 3 * 2 + 0.2, fontHeight + 2);
				}
				glEnd();
				if(mouse.x >= offset - fontHeight && mouse.x <= offset && mouse.y >= 2 && mouse.y <= fontHeight + 2  && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
				{
					RenderUtil.setColor(new Color(255, 0, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight / 3 * 2 - 1.5, fontHeight / 3 + 2);
						glVertex2d(offset - fontHeight / 3 + 0.5, fontHeight / 3 + 2);
						glVertex2d(offset - fontHeight / 3 + 0.5, fontHeight / 3 * 2 + 4);
						glVertex2d(offset - fontHeight / 3 * 2 - 1.5, fontHeight / 3 * 2 + 4);
					}
					glEnd();
					RenderUtil.setColor(new Color(255, 0, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight / 3 * 2 - 2.5, fontHeight / 3 * 2 + 4);
						glVertex2d(offset - fontHeight / 3 + 1.5, fontHeight / 3 * 2 + 4);
						glVertex2d(offset - fontHeight / 3 + 1.5, fontHeight / 3 * 2 + 5);
						glVertex2d(offset - fontHeight / 3 * 2 - 2.5, fontHeight / 3 * 2 + 5);
					}
					glEnd();
					RenderUtil.setColor(new Color(255, 255, 255, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight / 3 * 2, fontHeight / 3 * 2 + 5.1);
						glVertex2d(offset - fontHeight / 3 * 2 + 1.5, fontHeight / 3 * 2 + 5.1);
						glVertex2d(offset - fontHeight / 3 * 2 + 1.3, fontHeight + 2);
						glVertex2d(offset - fontHeight / 3 * 2 + 0.2, fontHeight + 2);
					}
					glEnd();
				}
			}
			if( i == 2 && overlays[i])//UI for the minimize button:
			{//If it is minimized:
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight + 1, 4.5);
					glVertex2d(offset - 1, 4.5);
					glVertex2d(offset - fontHeight / 2, fontHeight - 0.5);
				}
				glEnd();
				RenderUtil.setColor(new Color(0, 255, 0, 64));
				glBegin(GL_TRIANGLES);
				{
					glVertex2d(offset - fontHeight + 1, 4.5);
					glVertex2d(offset - 1, 4.5);
					glVertex2d(offset - fontHeight / 2, fontHeight - 0.5);
				}
				glEnd();
				if(mouse.x >= offset - fontHeight && mouse.x <= offset && mouse.y >= 2 && mouse.y <= fontHeight + 2  && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
				{
					RenderUtil.setColor(new Color(0, 255, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_TRIANGLES);
					{
						glVertex2d(offset - fontHeight + 1, 4.5);
						glVertex2d(offset - 1, 4.5);
						glVertex2d(offset - fontHeight / 2, fontHeight - 0.5);
					}
					glEnd();
				}
			}else if(i == 2)
			{//If it is not minimized:
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight + 1, fontHeight - 1);
					glVertex2d(offset - 1, fontHeight - 1);
					glVertex2d(offset - fontHeight / 2, 4);
				}
				glEnd();
				RenderUtil.setColor(new Color(255, 0, 0, 64));
				glBegin(GL_TRIANGLES);
				{
					glVertex2d(offset - fontHeight + 1, fontHeight - 1);
					glVertex2d(offset - 1, fontHeight - 1);
					glVertex2d(offset - fontHeight / 2, 4);
				}
				glEnd();
				if(mouse.x >= offset - fontHeight && mouse.x <= offset && mouse.y >= 2 && mouse.y <= fontHeight + 2  && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
				{
					RenderUtil.setColor(new Color(255, 0, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_TRIANGLES);
					{
						glVertex2d(offset - fontHeight + 1, fontHeight - 1);
						glVertex2d(offset - 1, fontHeight - 1);
						glVertex2d(offset - fontHeight / 2, 4);
					}
					glEnd();
				}
			}
			if(i == 0)//UI for the close button:
			{
				glLineWidth(1.0f);
				glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
				glBegin(GL_LINE_LOOP);
				{
					glVertex2d(offset - fontHeight, 4);
					glVertex2d(offset - fontHeight + 2, 2);
					glVertex2d(offset - fontHeight / 2, fontHeight / 2);
					glVertex2d(offset - 2, 2);
					glVertex2d(offset, 4);
					glVertex2d(offset - fontHeight / 2 + 2, fontHeight / 2 + 2);
					glVertex2d(offset, fontHeight);
					glVertex2d(offset - 2, fontHeight + 2);
					glVertex2d(offset - fontHeight / 2, fontHeight / 2 + 4);
					glVertex2d(offset - fontHeight + 2, fontHeight + 2);
					glVertex2d(offset - fontHeight, fontHeight);
					glVertex2d(offset - fontHeight / 2 - 2, fontHeight / 2 + 2);
				}
				glEnd();
				RenderUtil.setColor(new Color(255, 0, 0, 64));
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight, 4);
					glVertex2d(offset - fontHeight + 2, 2);
					glVertex2d(offset, fontHeight);
					glVertex2d(offset - 2, fontHeight + 2);
				}
				glEnd();
				glBegin(GL_QUADS);
				{
					glVertex2d(offset, 4);
					glVertex2d(offset - 2, 2);
					glVertex2d(offset - fontHeight / 2, fontHeight / 2);
					glVertex2d(offset - fontHeight / 2 + 2, fontHeight / 2 + 2);
				}
				glEnd();
				glBegin(GL_QUADS);
				{
					glVertex2d(offset - fontHeight / 2, fontHeight / 2 + 4);
					glVertex2d(offset - fontHeight / 2 - 2, fontHeight / 2 + 2);
					glVertex2d(offset - fontHeight, fontHeight);
					glVertex2d(offset - fontHeight + 2, fontHeight + 2);
				}
				glEnd();
				if(mouse.x >= offset - fontHeight && mouse.x <= offset && mouse.y >= 2 && mouse.y <= fontHeight + 2  && Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen)
				{
					RenderUtil.setColor(new Color(255, 0, 0, Mouse.isButtonDown(0) ? 96 : 64));
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight, 4);
						glVertex2d(offset - fontHeight + 2, 2);
						glVertex2d(offset, fontHeight);
						glVertex2d(offset - 2, fontHeight + 2);
					}
					glEnd();
					glBegin(GL_QUADS);
					{
						glVertex2d(offset, 4);
						glVertex2d(offset - 2, 2);
						glVertex2d(offset - fontHeight / 2, fontHeight / 2);
						glVertex2d(offset - fontHeight / 2 + 2, fontHeight / 2 + 2);
					}
					glEnd();
					glBegin(GL_QUADS);
					{
						glVertex2d(offset - fontHeight / 2, fontHeight / 2 + 4);
						glVertex2d(offset - fontHeight / 2 - 2, fontHeight / 2 + 2);
						glVertex2d(offset - fontHeight, fontHeight);
						glVertex2d(offset - fontHeight + 2, fontHeight + 2);
					}
					glEnd();
				}
			}
			glLineWidth(1.0f);
			glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			glBegin(GL_LINE_LOOP);
			{
				glVertex2d(offset - fontHeight, 2);
				glVertex2d(offset, 2);
				glVertex2d(offset, fontHeight + 2);
				glVertex2d(offset - fontHeight, fontHeight + 2);
			}
			glEnd();
			offset -= fontHeight + 2;
		}
		if(!component.isMinimized())
		{
			glColor4f(0f, 0f, 0f, 1f);
			glLineWidth(1.0f);
			glBegin(GL_LINES);
			{
				glVertex2d(0, theme.getFontRenderer().FONT_HEIGHT + 4);
				glVertex2d(area.width, theme.getFontRenderer().FONT_HEIGHT + 4);
			}
			glEnd();
		}
		glEnable(GL_TEXTURE_2D);
		theme.getFontRenderer().drawStringWithShadow(component.getTitle(), 2,
				2, RenderUtil.toRGBA(component.getForegroundColor()));
		glEnable(GL_CULL_FACE);
		glDisable(GL_BLEND);
		translateComponent(component, true);
	}

	@Override
	protected Rectangle getContainerChildRenderArea(Frame container) {
		Rectangle area = new Rectangle(container.getArea());
		area.x = 2;
		area.y = theme.getFontRenderer().FONT_HEIGHT + 6;
		area.width -= 4;
		area.height -= theme.getFontRenderer().FONT_HEIGHT + 8;
		return area;
	}

	@Override
	protected Dimension getDefaultComponentSize(Frame component) {
		Component[] children = component.getChildren();
		Rectangle[] areas = new Rectangle[children.length];
		Constraint[][] constraints = new Constraint[children.length][];
		for(int i = 0; i < children.length; i++) {
			Component child = children[i];
			Dimension size = child.getTheme().getUIForComponent(child)
					.getDefaultSize(child);
			areas[i] = new Rectangle(0, 0, size.width, size.height);
			constraints[i] = component.getConstraints(child);
		}
		Dimension size = component.getLayoutManager().getOptimalPositionedSize(
				areas, constraints);
		size.width += 4;
		size.height += theme.getFontRenderer().FONT_HEIGHT + 8;
		return size;
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(Frame component) {
		return new Rectangle[] { new Rectangle(0, 0, component.getWidth(),
				theme.getFontRenderer().FONT_HEIGHT + 4) };
	}

	@Override
	protected void handleComponentInteraction(Frame component, Point location,
			int button) {
		if(button != 0)
			return;
		int offset = component.getWidth() - 2;
		int textHeight = theme.getFontRenderer().FONT_HEIGHT;
		if(component.isClosable()) {
			if(location.x >= offset - textHeight && location.x <= offset
					&& location.y >= 2 && location.y <= textHeight + 2) {
				component.close();
				return;
			}
			offset -= textHeight + 2;
		}
		if(component.isPinnable()) {
			if(location.x >= offset - textHeight && location.x <= offset
					&& location.y >= 2 && location.y <= textHeight + 2) {
				component.setPinned(!component.isPinned());
				return;
			}
			offset -= textHeight + 2;
		}
		if(component.isMinimizable()) {
			if(location.x >= offset - textHeight && location.x <= offset
					&& location.y >= 2 && location.y <= textHeight + 2) {
				component.setMinimized(!component.isMinimized());
				return;
			}
			offset -= textHeight + 2;
		}
		if(location.x >= 0 && location.x <= offset && location.y >= 0
				&& location.y <= textHeight + 4) {
			component.setDragging(true);
			return;
		}
	}
}
