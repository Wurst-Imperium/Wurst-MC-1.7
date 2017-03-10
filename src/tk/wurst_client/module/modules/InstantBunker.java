package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MovingObjectPosition;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BuildUtils;
import tk.wurst_client.utils.RenderUtils;

public class InstantBunker extends Module
{
	public InstantBunker()
	{
		super
		(
			"InstantBunker",
			"Instantly builds a small bunker around you.",
			0,
			Category.BLOCKS
		);
	}
	
	private float speed = 5;
	private int i;
	private boolean shouldBuild;
	private float playerYaw;
	private MovingObjectPosition MouseOver;
	private double posX;
	private double posY;
	private double posZ;
	
	//Bottom = 0, Top = 1, Front = 2, Back = 3, Right = 4, Left = 5.
	private int[][] building =
	{
			{0, 1, 2, 1},
			{1, 1, 2, 1},
			{-1, 1, 2, 1},
			{2, 1, 2, 1},
			{-2, 1, 2, 1},
			{2, 1, 1, 1},
			{-2, 1, 1, 1},
			{2, 1, 0, 1},
			{-2, 1, 0, 1},
			{2, 1, -1, 1},
			{-2, 1, -1, 1},
			{0, 1, -2, 1},
			{1, 1, -2, 1},
			{-1, 1, -2, 1},
			{2, 1, -2, 1},
			{-2, 1, -2, 1},
			{0, 2, 2, 1},
			{1, 2, 2, 1},
			{-1, 2, 2, 1},
			{2, 2, 2, 1},
			{-2, 2, 2, 1},
			{2, 2, 1, 1},
			{-2, 2, 1, 1},
			{2, 2, 0, 1},
			{-2, 2, 0, 1},
			{2, 2, -1, 1},
			{-2, 2, -1, 1},
			{0, 2, -2, 1},
			{1, 2, -2, 1},
			{-1, 2, -2, 1},
			{2, 2, -2, 1},
			{-2, 2, -2, 1},
			{0, 3, 2, 1},
			{1, 3, 2, 1},
			{-1, 3, 2, 1},
			{2, 3, 2, 1},
			{-2, 3, 2, 1},
			{2, 3, 1, 1},
			{-2, 3, 1, 1},
			{2, 3, 0, 1},
			{-2, 3, 0, 1},
			{2, 3, -1, 1},
			{-2, 3, -1, 1},
			{0, 3, -2, 1},
			{1, 3, -2, 1},
			{-1, 3, -2, 1},
			{2, 3, -2, 1},
			{-2, 3, -2, 1},
			{0, 4, 2, 2},
			{1, 4, 2, 2},
			{-1, 4, 2, 2},
			{0, 4, -2, 3},
			{1, 4, -2, 3},
			{-1, 4, -2, 3},
			{2, 4, 0, 4},
			{-2, 4, 0, 5},
			{0, 4, 1, 2},
	};
	
	public void onEnable()
	{
		if(Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled())
			speed = 1000000000;
		else
			speed = 5;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			i = 0;
			shouldBuild = true;
			MouseOver = Minecraft.getMinecraft().objectMouseOver;
			posX = Minecraft.getMinecraft().thePlayer.posX;
			posY = Minecraft.getMinecraft().thePlayer.posY;
			posZ = Minecraft.getMinecraft().thePlayer.posZ;
			playerYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
			while(playerYaw > 180)
				playerYaw -= 360;
			while(playerYaw < -180)
				playerYaw += 360;
		}
	}
	
	public void onRender()
	{
		if (shouldBuild && i < building.length && i >= 0)
		{
			if(playerYaw > -45 && playerYaw <= 45)
			{//F: 0 South
				double renderX = (int) posX + BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosX;
				double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
				double renderZ = (int) posZ + BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > 45 && playerYaw <= 135)
			{//F: 1 West
				double renderX = (int) posX - BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosX;
				double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
				double renderZ = (int) posZ + BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > 135 || playerYaw <= -135)
			{//F: 2 North
				double renderX = (int) posX - BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosX;
				double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
				double renderZ = (int) posZ - BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(playerYaw > -135 && playerYaw <= -45)
			{//F: 3 East
				double renderX = (int) posX + BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosX;
				double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
				double renderZ = (int) posZ - BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}
		}
		for(int i = 0; i < building.length; i++)
		{
			if (shouldBuild && MouseOver != null)
			{
				if(playerYaw > -45 && playerYaw <= 45)
				{//F: 0 South
					double renderX = (int) posX + BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosX;
					double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
					double renderZ = (int) posZ + BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > 45 && playerYaw <= 135)
				{//F: 1 West
					double renderX = (int) posX - BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosX;
					double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
					double renderZ = (int) posZ + BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > 135 || playerYaw <= -135)
				{//F: 2 North
					double renderX = (int) posX - BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosX;
					double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
					double renderZ = (int) posZ - BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}else if(playerYaw > -135 && playerYaw <= -45)
				{//F: 3 East
					double renderX = (int) posX + BuildUtils.convertPosInAdvancedBuiling(3, i, building) - RenderManager.renderPosX;
					double renderY = (int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building) - RenderManager.renderPosY;
					double renderZ = (int) posZ - BuildUtils.convertPosInAdvancedBuiling(1, i, building) - RenderManager.renderPosZ;
					RenderUtils.emptyBlockESPBox(renderX, renderY, renderZ);
				}
			}
		}
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		updateMS();
		if(shouldBuild)
		{
			if((hasTimePassedS(speed) || Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled()) && i < building.length)
			{
				BuildUtils.advancedInstantBuildNext(building, MouseOver, playerYaw, posX + 1, posY, posZ, i);
				if(playerYaw > -45 && playerYaw <= 45)
				{//F: 0 South
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							(int) posX + BuildUtils.convertPosInAdvancedBuiling(1, i, building),
							(int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building),
							(int) posZ + BuildUtils.convertPosInAdvancedBuiling(3, i, building)
						)) != 0)
							i += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > 45 && playerYaw <= 135)
				{//F: 1 West
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							(int) posX - BuildUtils.convertPosInAdvancedBuiling(3, i, building),
							(int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building),
							(int) posZ + BuildUtils.convertPosInAdvancedBuiling(1, i, building)
						)) != 0)
							i += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > 135 || playerYaw <= -135)
				{//F: 2 North
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							(int) posX - BuildUtils.convertPosInAdvancedBuiling(1, i, building),
							(int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building),
							(int) posZ - BuildUtils.convertPosInAdvancedBuiling(3, i, building)
						)) != 0)
							i += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}else if(playerYaw > -135 && playerYaw <= -45)
				{//F: 3 East
					try
					{
						if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock
						(
							(int) posX + BuildUtils.convertPosInAdvancedBuiling(3, i, building),
							(int) posY - 3 + BuildUtils.convertPosInAdvancedBuiling(2, i, building),
							(int) posZ - BuildUtils.convertPosInAdvancedBuiling(1, i, building)
						)) != 0)
							i += 1;
					}catch(NullPointerException e){}//If the current item is null.
				}
				updateLastMS();
			}else if(i == building.length)
			{
				shouldBuild = false;
				this.setToggled(false);
			}
		}else
		{
			BuildUtils.advancedInstantBuild(building);
			this.setToggled(false);
		}
	}
	
	public void onDisable()
	{
		shouldBuild = false;
	}
}
	
	
