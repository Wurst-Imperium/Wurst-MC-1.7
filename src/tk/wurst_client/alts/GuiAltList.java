package tk.wurst_client.alts;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import tk.wurst_client.gui.GuiWurstSlot;
import tk.wurst_client.utils.AltUtils;

public class GuiAltList extends GuiWurstSlot
{
	public GuiAltList(Minecraft par1Minecraft, GuiScreen prevMenu)
	{
		super(par1Minecraft, prevMenu.width, prevMenu.height, 36, prevMenu.height - 56, 30);
		this.mc = par1Minecraft;
	}

	private int selectedSlot;
	private Minecraft mc;
	public static ArrayList<Alt> alts = new ArrayList<Alt>();
	
	public static void sortAlts()
	{
		Collections.sort(alts, new Comparator<Alt>()
		{
			public int compare(Alt o1, Alt o2)
			{
				return o1.name.compareToIgnoreCase(o2.name);
			}
		});
		ArrayList<Alt> newAlts = new ArrayList<Alt>();
		for(int i = 0; i < alts.size(); i++)
		{
			if(!alts.get(i).cracked)
				newAlts.add(alts.get(i));
		}
		for(int i = 0; i < alts.size(); i++)
		{
			if(alts.get(i).cracked)
				newAlts.add(alts.get(i));
		}
		for(int i = 0; i < newAlts.size(); i++)
		{
			for(int i2 = 0; i2 < newAlts.size(); i2++)
			{
				if
				(
					i != i2
					&& newAlts.get(i).name.equals(newAlts.get(i2).name)
					&& newAlts.get(i).cracked == newAlts.get(i2).cracked
				)
					newAlts.remove(i2);
			}
		}
		alts = newAlts;
	}
	
	protected boolean isSelected(int id)
	{
		return this.selectedSlot == id;
	}
	
	protected int getSelectedSlot()
	{
		return this.selectedSlot;
	}
	
	protected int getSize()
	{
		return this.alts.size();
	}
	
	protected void elementClicked(int var1, boolean var2, int var3, int var4)
	{
		this.selectedSlot = var1;
	}
	
	protected void drawBackground(){}
	
	protected void drawSlot(int id, int x, int y, int var4, Tessellator var5, int var6, int var7)
	{
		Alt alt = this.alts.get(id);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_CULL_FACE);
		GL11.glEnable(GL_BLEND);
		if(Minecraft.getMinecraft().getSession().getUsername().equals(alt.name))
		{
	        float opacity = 0.3F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 10000L) / 10000.0F * (float)Math.PI * 2.0F) * 0.15F);
	        GL11.glColor4f(0.0F, 1.0F, 0.0F, opacity);
	        GL11.glBegin(GL11.GL_QUADS);
	        {
	        	GL11.glVertex2d(x - 2, y - 2);
	        	GL11.glVertex2d(x - 2 + 250, y - 2);
	        	GL11.glVertex2d(x - 2 + 250, y - 2 + 30);
	        	GL11.glVertex2d(x - 2, y - 2 + 30);
	        }
	        GL11.glEnd();
		}
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_CULL_FACE);
		GL11.glDisable(GL_BLEND);
		AltUtils.drawAltFace(alt.name, x + 1, y + 1, 24, 24, GuiAlts.altList.isSelected(GuiAlts.altList.alts.indexOf(alt)));
		this.mc.fontRenderer.drawString("Name: " + alt.name, x + 31, y + 3, 10526880);
		String stars = "";
		for(int i = 0; i < alt.password.length(); i++)
			stars = stars.concat("*");
		this.mc.fontRenderer.drawString((alt.cracked ? "cracked" : "Password: " + stars), x + 31, y + 15, 10526880);
	}
}
