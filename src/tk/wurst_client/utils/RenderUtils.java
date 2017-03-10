package tk.wurst_client.utils;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.opengl.GL11;

public class RenderUtils
{
	/**
	 * Renders a box with any size and any color.
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public static void box(double x, double y, double z, double x2, double y2, double z2, Color color)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(2.0F);
        RenderUtil.setColor(color);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        drawColorBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x2,
        	y2,
        	z2
        ));
        GL11.glColor4d(0, 0, 0, 0.5F);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x2,
        	y2,
        	z2
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	/**
	 * Renders a frame with any size and any color.
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        RenderUtil.setColor(color);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x2,
        	y2,
        	z2
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	/**
	 * Renders an ESP box with the size of a normal block at the specified coordinates.
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void blockESPBox(double x, double y, double z)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(1.0F);
        GL11.glColor4d(0, 1, 0, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        drawColorBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x + 1.0,
        	y + 1.0,
        	z + 1.0
        ));
        GL11.glColor4d(0, 0, 0, 0.5F);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x + 1.0,
        	y + 1.0,
        	z + 1.0
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	public static void framelessBlockESP(double x, double y, double z, Color color)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glColor4d
        (
        	color.getRed() / 255,
        	color.getGreen() / 255,
        	color.getBlue() / 255,
        	0.15
        );
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        drawColorBox(new AxisAlignedBB
        (
        	x,
            y,
            z,
            x + 1.0,
            y + 1.0,
            z + 1.0
        ));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	public static void emptyBlockESPBox(double x, double y, double z)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4d(0, 0, 0, 0.5F);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x + 1.0,
        	y + 1.0,
        	z + 1.0
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	public static int enemy = 0;
	public static int friend = 1;
	public static int other = 2;
	public static int target = 3;
	public static int team = 4;
	
	public static void entityESPBox(Entity entity, int mode)
	{
		double x = entity.boundingBox.minX - 0.05 - RenderManager.renderPosX;
		double y = entity.boundingBox.minY - RenderManager.renderPosY;
		double z = entity.boundingBox.minZ - 0.05 - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
		if(mode == 0)//Enemy
        	GL11.glColor4d(1 - (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40), (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40), 0, 0.5F);
        else if(mode == 1)//Friend
        	GL11.glColor4d(0, 0, 1, 0.5F);
        else if(mode == 2)//Other
        	GL11.glColor4d(1, 1, 0, 0.5F);
        else if(mode == 3)//Target
        	GL11.glColor4d(1, 0, 0, 0.5F);
        else if(mode == 4)//Team
        	GL11.glColor4d(0, 1, 0, 0.5F);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x + entity.width + 0.1,
        	y + entity.height + 0.1,
        	z + entity.width + 0.1
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	public static void nukerBox(double x, double y, double z, float damage)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(1.0F);
        GL11.glColor4d(damage, 1 - damage, 0, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        drawColorBox(new AxisAlignedBB
        (
        	x + 0.5 - damage / 2,
        	y + 0.5 - damage / 2,
        	z + 0.5 - damage / 2,
        	x + 0.5 + damage / 2,
        	y + 0.5 + damage / 2, 
        	z + 0.5 + damage / 2
        ));
        GL11.glColor4d(0, 0, 0, 0.5F);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x + 0.5 - damage / 2,
        	y + 0.5 - damage / 2,
        	z + 0.5 - damage / 2,
        	x + 0.5 + damage / 2,
        	y + 0.5 + damage / 2, 
        	z + 0.5 + damage / 2
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	public static void searchBox(double x, double y, double z)
	{
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(1.0F);
        float opacity = 0.3F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 1.0F) * 0.15F);
        GL11.glColor4d(0, 1, 0, opacity);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        drawColorBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x + 1.0,
        	y + 1.0,
        	z + 1.0
        ));
        GL11.glColor4d(0, 0, 0, 0.5);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB
        (
        	x,
        	y,
        	z,
        	x + 1.0,
        	y + 1.0,
        	z + 1.0
        ), -1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
    }
	
	public static void drawColorBox(AxisAlignedBB axisalignedbb)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();//Starts X.
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.draw();//Ends X.
        tessellator.startDrawingQuads();//Starts Y.      
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);        
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();      
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);        
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.draw();//Ends Y.
        tessellator.startDrawingQuads();//Starts Z.
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.draw();//Ends Z.
    }
	
	public static void tracerLine(Entity entity, int mode)
	{
		double x = entity.posX - RenderManager.renderPosX;
		double y = entity.posY + entity.height / 2 - RenderManager.renderPosY;
		double z = entity.posZ - RenderManager.renderPosZ;
        glBlendFunc(770, 771);
        glEnable(GL_BLEND);
        glLineWidth(2.0F);
        glDisable(GL11.GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
		if(mode == 0)//Enemy
        	GL11.glColor4d(1 - (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40), (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40), 0, 0.5F);
        else if(mode == 1)//Friend
        	GL11.glColor4d(0, 0, 1, 0.5F);
        else if(mode == 2)//Other
        	GL11.glColor4d(1, 1, 0, 0.5F);
        else if(mode == 3)//Target
        	GL11.glColor4d(1, 0, 0, 0.5F);
        else if(mode == 4)//Team
        	GL11.glColor4d(0, 1, 0, 0.5F);
		glBegin(GL_LINES);
		{
			glVertex3d(0, 0, 0);
			glVertex3d(x, y, z);
		}
		glEnd();
        glEnable(GL11.GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
	}
	
	public static void tracerLine(Entity entity, Color color)
	{
		double x = entity.posX - RenderManager.renderPosX;
		double y = entity.posY + entity.height / 2 - RenderManager.renderPosY;
		double z = entity.posZ - RenderManager.renderPosZ;
        glBlendFunc(770, 771);
        glEnable(GL_BLEND);
        glLineWidth(2.0F);
        glDisable(GL11.GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
       	RenderUtil.setColor(color);
		glBegin(GL_LINES);
		{
			glVertex3d(0, 0, 0);
			glVertex3d(x, y, z);
		}
		glEnd();
        glEnable(GL11.GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
	}
	
	public static void tracerLine(int x, int y, int z, Color color)
	{
		x += 0.5 - RenderManager.renderPosX;
		y += 0.5 - RenderManager.renderPosY;
		z += 0.5 - RenderManager.renderPosZ;
        glBlendFunc(770, 771);
        glEnable(GL_BLEND);
        glLineWidth(2.0F);
        glDisable(GL11.GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
       	RenderUtil.setColor(color);
		glBegin(GL_LINES);
		{
			glVertex3d(0, 0, 0);
			glVertex3d(x, y, z);
		}
		glEnd();
        glEnable(GL11.GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
	}
}
