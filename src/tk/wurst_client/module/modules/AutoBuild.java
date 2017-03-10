package tk.wurst_client.module.modules;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MovingObjectPosition;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BuildUtils;
import tk.wurst_client.utils.RenderUtils;

public class AutoBuild extends Module
{
	public AutoBuild()
	{
		super
		(
			"AutoBuild",
			"Automatically builds the selected structure whenever\n"
			+ "you place a block. Use the combo box below to select a\n"
			+ "structure.\n"
			+ "This can bypass NoCheat+ while YesCheat+ is enabled.",
			0,
			Category.BLOCKS
		);
	}
	
	public static String[] modeNames = {"Bridge", "Floor", "Nazi", "Penis", "Pillar", "Wall", "Wurst", "Custom"};
	public static ArrayList<int[][]> buildings = new ArrayList<int[][]>();
	private float speed = 5;
	private int blockIndex;
	private boolean shouldBuild;
	private float playerYaw;
	private MovingObjectPosition MouseOver;
	
	public String getRenderName()
	{
		return this.getName() + " [" + modeNames[Client.Wurst.options.autobuildMode] + "]";
	}
	
	public void onRender()
	{
		if(!this.getToggled())
			return;
		if(buildings.get(Client.Wurst.options.autobuildMode)[0].length == 4)
			renderAdvanced();
		else
			renderSimple();
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(buildings.get(Client.Wurst.options.autobuildMode)[0].length == 4)
			buildAdvanced();
		else
			buildSimple();
	}

	public void onDisable()
	{
		shouldBuild = false;
	}
	
	private void renderAdvanced()
	{
		if (shouldBuild && blockIndex < buildings.get(Client.Wurst.options.autobuildMode).length && blockIndex >= 0)
		{
			if(playerYaw > -45 && playerYaw <= 45)
			{//F: 0 South
				double renderX = BuildUtils.convertPosNext(1, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
				double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
				double renderZ = BuildUtils.convertPosNext(3, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > 45 && playerYaw <= 135)
			{//F: 1 West
				double renderX = BuildUtils.convertPosNext(1, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
				double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
				double renderZ = BuildUtils.convertPosNext(3, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > 135 || playerYaw <= -135)
			{//F: 2 North
				double renderX = BuildUtils.convertPosNext(1, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
				double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
				double renderZ = BuildUtils.convertPosNext(3, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > -135 && playerYaw <= -45)
			{//F: 3 East
				double renderX = BuildUtils.convertPosNext(1, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
				double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
				double renderZ = BuildUtils.convertPosNext(3, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}
		}
		if (shouldBuild && MouseOver != null)
		{
			double renderX = BuildUtils.convertPosNext(1, MouseOver) - RenderManager.renderPosX;
			double renderY = BuildUtils.convertPosNext(2, MouseOver) + 1 - RenderManager.renderPosY;
			double renderZ = BuildUtils.convertPosNext(3, MouseOver) - RenderManager.renderPosZ;
			RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
		}
		for(int i = 0; i < buildings.get(Client.Wurst.options.autobuildMode).length; i++)
		{
			if (shouldBuild && MouseOver != null)
			{
				if(playerYaw > -45 && playerYaw <= 45)
				{//F: 0 South
					double renderX = BuildUtils.convertPosNext(1, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
					double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
					double renderZ = BuildUtils.convertPosNext(3, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > 45 && playerYaw <= 135)
				{//F: 1 West
					double renderX = BuildUtils.convertPosNext(1, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
					double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
					double renderZ = BuildUtils.convertPosNext(3, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > 135 || playerYaw <= -135)
				{//F: 2 North
					double renderX = BuildUtils.convertPosNext(1, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
					double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
					double renderZ = BuildUtils.convertPosNext(3, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > -135 && playerYaw <= -45)
				{//F: 3 East
					double renderX = BuildUtils.convertPosNext(1, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosX;
					double renderY = BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosY;
					double renderZ = BuildUtils.convertPosNext(3, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode)) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}
			}
		}
	}
	
	private void renderSimple()
	{
		if (shouldBuild && blockIndex < buildings.get(Client.Wurst.options.autobuildMode).length && blockIndex >= 0)
		{
			if(playerYaw > -45 && playerYaw <= 45)
			{//F: 0 South
				double renderX = MouseOver.blockX + BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
				double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
				double renderZ = MouseOver.blockZ + BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > 45 && playerYaw <= 135)
			{//F: 1 West
				double renderX = MouseOver.blockX - BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
				double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
				double renderZ = MouseOver.blockZ + BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > 135 || playerYaw <= -135)
			{//F: 2 North
				double renderX = MouseOver.blockX - BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
				double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
				double renderZ = MouseOver.blockZ - BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > -135 && playerYaw <= -45)
			{//F: 3 East
				double renderX = MouseOver.blockX + BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
				double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
				double renderZ = MouseOver.blockZ - BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}
		}
		if (shouldBuild && MouseOver != null)
		{
			double renderX = BuildUtils.convertPosNext(1, MouseOver) - RenderManager.renderPosX;
			double renderY = BuildUtils.convertPosNext(2, MouseOver) + 1 - RenderManager.renderPosY;
			double renderZ = BuildUtils.convertPosNext(3, MouseOver) - RenderManager.renderPosZ;
			RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
		}
		for(int i = 0; i < buildings.get(Client.Wurst.options.autobuildMode).length; i++)
		{
			if (shouldBuild && MouseOver != null)
			{
				if(playerYaw > -45 && playerYaw <= 45)
				{//F: 0 South
					double renderX = MouseOver.blockX + BuildUtils.convertPosInBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
					double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
					double renderZ = MouseOver.blockZ + BuildUtils.convertPosInBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > 45 && playerYaw <= 135)
				{//F: 1 West
					double renderX = MouseOver.blockX - BuildUtils.convertPosInBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
					double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
					double renderZ = MouseOver.blockZ + BuildUtils.convertPosInBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > 135 || playerYaw <= -135)
				{//F: 2 North
					double renderX = MouseOver.blockX - BuildUtils.convertPosInBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
					double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
					double renderZ = MouseOver.blockZ - BuildUtils.convertPosInBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > -135 && playerYaw <= -45)
				{//F: 3 East
					double renderX = MouseOver.blockX + BuildUtils.convertPosInBuiling(3, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosX;
					double renderY = MouseOver.blockY + BuildUtils.convertPosInBuiling(2, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosY;
					double renderZ = MouseOver.blockZ - BuildUtils.convertPosInBuiling(1, i, buildings.get(Client.Wurst.options.autobuildMode), MouseOver) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}
			}
		}
	}
	
	private void buildAdvanced()
	{
		updateMS();
		if(!shouldBuild && (Minecraft.getMinecraft().rightClickDelayTimer == 4 || Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled()) && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed == true && Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ).getMaterial() != Material.air)
		{
			if(Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled())
				speed = 1000000000;
			else
				speed = 5;
			if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
			{
				blockIndex = 0;
				shouldBuild = true;
				MouseOver = Minecraft.getMinecraft().objectMouseOver;
				playerYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
				while(playerYaw > 180)
					playerYaw -= 360;
				while(playerYaw < -180)
					playerYaw += 360;
			}else
				BuildUtils.advancedBuild(buildings.get(Client.Wurst.options.autobuildMode));
			updateLastMS();
			return;
		}
		if(shouldBuild)
		{
			if((hasTimePassedS(speed) || Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled()) && blockIndex < buildings.get(Client.Wurst.options.autobuildMode).length)
			{
				BuildUtils.advancedBuildNext(buildings.get(Client.Wurst.options.autobuildMode), MouseOver, playerYaw, blockIndex);
				if(playerYaw > -45 && playerYaw <= 45)
				{//F: 0 South
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							BuildUtils.convertPosNext(1, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(3, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode))
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > 45 && playerYaw <= 135)
				{//F: 1 West
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							BuildUtils.convertPosNext(1, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(3, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode))
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > 135 || playerYaw <= -135)
				{//F: 2 North
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							BuildUtils.convertPosNext(1, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(3, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode))
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > -135 && playerYaw <= -45)
				{//F: 3 East
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							BuildUtils.convertPosNext(1, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(2, MouseOver) + BuildUtils.convertPosInAdvancedBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode)),
							BuildUtils.convertPosNext(3, MouseOver) - BuildUtils.convertPosInAdvancedBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode))
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}
				updateLastMS();
			}else if(blockIndex == buildings.get(Client.Wurst.options.autobuildMode).length)
				shouldBuild = false;
		}
	}
	
	private void buildSimple()
	{
		updateMS();
		if(!shouldBuild && (Minecraft.getMinecraft().rightClickDelayTimer == 4 || Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled()) && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed == true && Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ).getMaterial() != Material.air)
		{
			if(Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled())
				speed = 1000000000;
			else
				speed = 5;
			if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
			{
				blockIndex = 0;
				shouldBuild = true;
				MouseOver = Minecraft.getMinecraft().objectMouseOver;
				playerYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
				while(playerYaw > 180)
					playerYaw -= 360;
				while(playerYaw < -180)
					playerYaw += 360;
			}else
				BuildUtils.build(buildings.get(Client.Wurst.options.autobuildMode));
			updateLastMS();
			return;
		}
		if(shouldBuild)
		{
			if((hasTimePassedS(speed) || Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled()) && blockIndex < buildings.get(Client.Wurst.options.autobuildMode).length)
			{
				BuildUtils.buildNext(buildings.get(Client.Wurst.options.autobuildMode), MouseOver, playerYaw, blockIndex);
				if(playerYaw > -45 && playerYaw <= 45)
				{//F: 0 South
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							MouseOver.blockX + BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockZ + BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver)
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > 45 && playerYaw <= 135)
				{//F: 1 West
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							MouseOver.blockX - BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockZ + BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver)
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > 135 || playerYaw <= -135)
				{//F: 2 North
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							MouseOver.blockX - BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockZ - BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver)
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > -135 && playerYaw <= -45)
				{//F: 3 East
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							MouseOver.blockX + BuildUtils.convertPosInBuiling(3, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockY + BuildUtils.convertPosInBuiling(2, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver),
							MouseOver.blockZ - BuildUtils.convertPosInBuiling(1, blockIndex, buildings.get(Client.Wurst.options.autobuildMode), MouseOver)
						)) != 0)
							blockIndex += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}
				updateLastMS();
			}else if(blockIndex == buildings.get(Client.Wurst.options.autobuildMode).length)
				shouldBuild = false;
		}
	}
}
