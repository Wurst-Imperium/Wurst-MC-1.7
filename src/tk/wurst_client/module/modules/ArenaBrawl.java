package tk.wurst_client.module.modules;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.Label;
import org.darkstorm.minecraft.gui.component.Label.TextAlignment;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicLabel;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
import org.darkstorm.minecraft.gui.theme.wurst.WurstTheme;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BlockUtils;
import tk.wurst_client.utils.EntityUtils;
import tk.wurst_client.utils.MiscUtils;
import tk.wurst_client.utils.RenderUtils;

public class ArenaBrawl extends Module
{
	public ArenaBrawl()
	{
		super
		(
			"ArenaBrawl",
			"Makes ArenaBrawl on mc.hypixel.net a lot easier.\n"
			+ "This is a collection of mods that have been optimized\n"
			+ "for ArenaBrawl. It will bypass everything that Hypixel\n"
			+ "has to offer.",
			0,
			Category.MISC
		);
	}
	
	private EntityLivingBase friend;
	public static float range = 4.25F;
	private double distanceF = 2D;
	private double distanceE = 4D;
	public static ArrayList<String> scoreboard = new ArrayList<String>();
	private ArrayList<int[]> matchingBlocks = new ArrayList<int[]>();
	private ArrayList<int[]> enemyTotems = new ArrayList<int[]>();
	private ArrayList<int[]> friendTotems = new ArrayList<int[]>();
	private String friendsName;
	private Frame frame;
	private int target;
	private TargetType targetType;
	private EntityLivingBase entityTarget;
	private int[] blockTarget;
	private long lastAttack = 0L;
	public static int level = 40;
	
	public String getRenderName()
	{
		if(friendsName != null)
			return "ArenaBrawl with " + friendsName;
		else
			return "ArenaBrawl";
	}
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("ArenaBrawl level", level, 20, 100, 10, ValueDisplay.INTEGER));
	}
	
	public void updateSettings()
	{
		this.level = (int) this.moduleSliders.get(0).getValue();
	}
	
	public void onEnable()
	{
		reset();
	}

	public void onRender()
	{
		if(!this.getToggled())
			return;
		if(targetType == TargetType.BLOCK_E)
		{
			double x = blockTarget[0] - RenderManager.renderPosX;
			double y = blockTarget[1] - RenderManager.renderPosY;
			double z = blockTarget[2] - RenderManager.renderPosZ;
			RenderUtils.box(x, y, z, x + 1, y + 2, z + 1, new Color(255, 0, 0, 64));
		}else if(targetType == TargetType.BLOCK_F)
		{
			double x = blockTarget[0] - RenderManager.renderPosX;
			double y = blockTarget[1] - RenderManager.renderPosY;
			double z = blockTarget[2] - RenderManager.renderPosZ;
			RenderUtils.box(x, y, z, x + 1, y + 2, z + 1, new Color(0, 255, 0, 64));
		}else if(targetType == TargetType.ENTITY_E && entityTarget != null)
		{
			double x = entityTarget.posX - RenderManager.renderPosX;
			double y = entityTarget.posY - RenderManager.renderPosY;
			double z = entityTarget.posZ - RenderManager.renderPosZ;
			RenderUtils.box(x - 0.35, y, z - 0.35, x + 0.35, y + 1.9, z + 0.35, new Color(255, 0, 0, 64));
		}else if(targetType == TargetType.ENTITY_F && entityTarget != null)
		{
			double x = entityTarget.posX - RenderManager.renderPosX;
			double y = entityTarget.posY - RenderManager.renderPosY;
			double z = entityTarget.posZ - RenderManager.renderPosZ;
			RenderUtils.box(x - 0.35, y, z - 0.35, x + 0.35, y + 1.9, z + 0.35, new Color(0, 255, 0, 64));
		}
		if(EntityUtils.searchEntityByNameRaw(formatSBName(5)) != null)
		{
			RenderUtils.entityESPBox(EntityUtils.searchEntityByNameRaw(formatSBName(5)), RenderUtils.target);
			RenderUtils.tracerLine(EntityUtils.searchEntityByNameRaw(formatSBName(5)), RenderUtils.target);
		}
		if(EntityUtils.searchEntityByNameRaw(formatSBName(4)) != null)
		{
			RenderUtils.entityESPBox(EntityUtils.searchEntityByNameRaw(formatSBName(4)), RenderUtils.target);
			RenderUtils.tracerLine(EntityUtils.searchEntityByNameRaw(formatSBName(4)), RenderUtils.target);
		}
		if(friend != null)
		{
			RenderUtils.entityESPBox(friend, RenderUtils.team);
			RenderUtils.tracerLine(friend, RenderUtils.team);
		}
		if(!enemyTotems.isEmpty())
		{
			for(int[] totem : enemyTotems)
			{
				double x = totem[0] - RenderManager.renderPosX;
				double y = totem[1] - RenderManager.renderPosY;
				double z = totem[2] - RenderManager.renderPosZ;
				RenderUtils.frame(x, y, z, x + 1, y + 2, z + 1, new Color(255, 0, 0, 128));
				RenderUtils.tracerLine((int) x, (int) y, (int) z, new Color(255, 0, 0, 128));
			}
		}
		if(!friendTotems.isEmpty())
		{
			for(int[] totem : friendTotems)
			{
				double x = totem[0] - RenderManager.renderPosX;
				double y = totem[1] - RenderManager.renderPosY;
				double z = totem[2] - RenderManager.renderPosZ;
				RenderUtils.frame(x, y, z, x + 1, y + 2, z + 1, new Color(0, 255, 0, 128));
			}
		}
	}
	
	public void onReceivedMessage(String message)
	{
		if(!this.getToggled())
			return;
		if(message.startsWith("[Arena]: ") && message.endsWith(" has won the game!"))
		{
			Client.Wurst.chat.message(message.substring(9));
			this.setToggled(false);
		}
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(scoreboard != null && (scoreboard.size() == 13 || scoreboard.size() == 11))
		{//If you are in the lobby:
			Client.Wurst.chat.message("You need to be in a 2v2 arena.");
			this.setToggled(false);
			return;
		}
		if(scoreboard == null)
		{//If the scoreboard isn't there yet:
			return;
		}
		if(frame == null && scoreboard.size() == 8)
		{//When the scoreboard appears:
			try
			{
				setupFrame();
			}catch(Exception e)
			{
				e.printStackTrace();
				frame = null;
				return;
			}
		}
		if(friend == null || friend.isDead)
			friend = EntityUtils.searchEntityByName(friendsName);
		updateMS();
		try
		{
			scanTotems();
			getTarget();
			updateFrame();
			if
			(
				!Minecraft.getMinecraft().thePlayer.isCollidedHorizontally
				&& Minecraft.getMinecraft().thePlayer.moveForward > 0
				&& !Minecraft.getMinecraft().thePlayer.isSneaking()
			)
			{//Built-in AutoSprint and BunnyHop:
				Minecraft.getMinecraft().thePlayer.setSprinting(true);
				if(Minecraft.getMinecraft().thePlayer.onGround && Minecraft.getMinecraft().thePlayer.isSprinting())
					Minecraft.getMinecraft().thePlayer.jump();
			}
			if(targetType == TargetType.BLOCK_E)
			{
				float distX = (float) (blockTarget[0] - Minecraft.getMinecraft().thePlayer.posX);
				float distY = (float) (blockTarget[1] - Minecraft.getMinecraft().thePlayer.posY);
				float distZ = (float) (blockTarget[2] - Minecraft.getMinecraft().thePlayer.posZ);
				if(BlockUtils.getBlockDistance(distX, distY, distZ) <= 4.25)
				{//If the target is an enemy totem in range:
					faceTarget();
					attackTarget();
				}else
				{
					Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = false;
					Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
				}
			}else if(targetType == TargetType.ENTITY_E)
			{
				if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityTarget) <= 4.25)
				{//If the target is an enemy in range:
					faceTarget();
					attackTarget();
				}else
				{
					Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = false;
					Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
				}
			}else
			{
				Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = false;
				Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setupFrame()
	{
		friendsName = formatSBName(0);
		Client.Wurst.chat.message("Now playing ArenaBrawl with " + friendsName + ".");
		frame = new BasicFrame("ArenaBrawl");
		frame.setTheme(new WurstTheme());
		frame.setLayoutManager(new GridLayoutManager(2, 0));
		frame.setClosable(false);
		frame.setMinimized(false);
		frame.setMinimizable(false);
		frame.setPinnable(true);
		frame.setPinned(true);
		frame.setWidth(137);
		frame.add(new BasicLabel("NAME"), HorizontalGridConstraint.LEFT);
		frame.add(new BasicLabel("HEALTH"), HorizontalGridConstraint.RIGHT);
		frame.add(new BasicLabel(formatSBName(1)), HorizontalGridConstraint.LEFT);
		frame.add(new BasicLabel("???? / 2000"), HorizontalGridConstraint.RIGHT);
		frame.add(new BasicLabel(friendsName), HorizontalGridConstraint.LEFT);
		frame.add(new BasicLabel("???? / 2000"), HorizontalGridConstraint.RIGHT);
		frame.add(new BasicLabel(formatSBName(5)), HorizontalGridConstraint.LEFT);
		frame.add(new BasicLabel("???? / 2000"), HorizontalGridConstraint.RIGHT);
		frame.add(new BasicLabel(formatSBName(4)), HorizontalGridConstraint.LEFT);
		frame.add(new BasicLabel("???? / 2000"), HorizontalGridConstraint.RIGHT);
		frame.setHeight(frame.getTheme().getUIForComponent(frame).getDefaultSize(frame).height);
		frame.layoutChildren();
		Client.Wurst.guiManager.addFrame(frame);
		frame.setBackgroundColor(new Color(64, 64, 64, 224));
		((Label) frame.getChildren()[0]).setForegroundColor(Color.CYAN);
		((Label) frame.getChildren()[1]).setForegroundColor(Color.CYAN);
		((Label) frame.getChildren()[2]).setForegroundColor(Color.GREEN);
		((Label) frame.getChildren()[4]).setForegroundColor(Color.GREEN);
		((Label) frame.getChildren()[6]).setForegroundColor(Color.BLUE);
		((Label) frame.getChildren()[8]).setForegroundColor(Color.BLUE);
		frame.setVisible(true);
	}
	
	private void updateFrame()
	{
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
		frame.setX(width - frame.getWidth() - 1);
		frame.setY((height - frame.getHeight()) / 2 - 16);
		frame.setDragging(false);
		frame.setPinned(true);
		updateLabel(2, 1);
		updateLabel(4, 0);
		updateLabel(6, 5);
		updateLabel(8, 4);
		while(frame.getChildren().length > 10)
			frame.remove(frame.getChildren()[10]);
		for(int i = 0; i < friendTotems.size(); i++)
		{
			frame.add(new BasicLabel("Totem " + (i + 1)), HorizontalGridConstraint.LEFT);
			frame.add(new BasicLabel(""), HorizontalGridConstraint.RIGHT);
			((Label) frame.getChildren()[8 + (i + 1) * 2]).setForegroundColor(Color.GREEN);
		}
		for(int i = 0; i < enemyTotems.size(); i++)
		{
			frame.add(new BasicLabel("Totem " + (friendTotems.size() + i + 1)), HorizontalGridConstraint.LEFT);
			frame.add(new BasicLabel(""), HorizontalGridConstraint.RIGHT);
			((Label) frame.getChildren()[8 + (friendTotems.size() + i + 1) * 2]).setForegroundColor(Color.BLUE);
		}
	}

	private Color getColorForHealth(String health)
	{
		if(health.endsWith(" / 2000"))
			health = health.substring(0, health.length() - 7);
		if(!MiscUtils.isInteger(health) || Integer.valueOf(health) == 0)
			return Color.BLACK;
		else
			return new Color(0, Integer.valueOf(health) * 255 / 2200, 255 - Integer.valueOf(health) * 255 / 2200);
	}
	
	private String formatSBName(int index)
	{
		try
		{
			return scoreboard.get(index).split(" ")[0].substring(2, scoreboard.get(index).split(" ")[0].length() - 2);
		}catch(Exception e)
		{
			return null;
		}
	}

	private String formatSBHealth(int index)
	{
		try
		{
			String health = scoreboard.get(index).split(" ")[1];
			if(!MiscUtils.isInteger(health))
				return health;
			else
				return health + " / 2000";
		}catch(Exception e)
		{
			return "???? / 2000";
		}
	}

	private void updateLabel(int labelIndex, int sbIndex)
	{
		((Label) frame.getChildren()[labelIndex]).setText((target == labelIndex ? ">" : "") + formatSBName(sbIndex));
		((Label) frame.getChildren()[labelIndex]).setHorizontalAlignment(TextAlignment.LEFT);
		((Label) frame.getChildren()[labelIndex + 1]).setText(formatSBHealth(sbIndex));
		((Label) frame.getChildren()[labelIndex + 1]).setHorizontalAlignment(TextAlignment.RIGHT);
		((Label) frame.getChildren()[labelIndex + 1]).setForegroundColor(getColorForHealth(formatSBHealth(sbIndex)));
	}
	
	private void scanTotems()
	{
		matchingBlocks.clear();
		for(int y = 3; y >= -3; y--)
		{
			for(int x = 50; x >= -50; x--)
			{
				for(int z = 50; z >= -50; z--)
				{
					int posX = (int) (Minecraft.getMinecraft().thePlayer.posX + x);
					int posY = (int) (Minecraft.getMinecraft().thePlayer.posY + y);
					int posZ = (int) (Minecraft.getMinecraft().thePlayer.posZ + z);
					if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ)) == Block.getIdFromBlock(Block.getBlockFromName("wool")))
						matchingBlocks.add(new int[]{posX, posY, posZ});
				}
			}
		}
		enemyTotems.clear();
		for(int i = 0; i < matchingBlocks.size(); i++)
		{
			if
			(
				Minecraft.getMinecraft().theWorld.getBlockMetadata(matchingBlocks.get(i)[0], matchingBlocks.get(i)[1], matchingBlocks.get(i)[2]) == 14//red
				&& Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(matchingBlocks.get(i)[0], matchingBlocks.get(i)[1] + 1, matchingBlocks.get(i)[2])) != 0
			)
			{
				enemyTotems.add(new int[]{matchingBlocks.get(i)[0], matchingBlocks.get(i)[1] + 1, matchingBlocks.get(i)[2]});
			}
		}
		friendTotems.clear();
		for(int i = 0; i < matchingBlocks.size(); i++)
		{
			if
			(
				Minecraft.getMinecraft().theWorld.getBlockMetadata(matchingBlocks.get(i)[0], matchingBlocks.get(i)[1], matchingBlocks.get(i)[2]) == 5//lime
				&& Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(matchingBlocks.get(i)[0], matchingBlocks.get(i)[1] + 1, matchingBlocks.get(i)[2])) != 0
			)
			{
				friendTotems.add(new int[]{matchingBlocks.get(i)[0], matchingBlocks.get(i)[1] + 1, matchingBlocks.get(i)[2]});
			}
		}
	}
	
	private void getTarget()
	{
		blockTarget = null;
		entityTarget = null;
		target = -1;
		targetType = null;
		if(!enemyTotems.isEmpty())
		{//If there is an enemy totem:
			int[] closestTotem = null;
			float dist = 999999999;
			for(int[] totem : enemyTotems)
			{
				float distX = (float) (totem[0] - Minecraft.getMinecraft().thePlayer.posX);
				float distY = (float) (totem[1] - Minecraft.getMinecraft().thePlayer.posY);
				float distZ = (float) (totem[2] - Minecraft.getMinecraft().thePlayer.posZ);
				dist = BlockUtils.getBlockDistance(distX, distY, distZ);
				if(closestTotem == null)
					closestTotem = totem;
				else
				{
					float distXC = (float) (closestTotem[0] - Minecraft.getMinecraft().thePlayer.posX);
					float distYC = (float) (closestTotem[1] - Minecraft.getMinecraft().thePlayer.posY);
					float distZC = (float) (closestTotem[2] - Minecraft.getMinecraft().thePlayer.posZ);
					float distC = BlockUtils.getBlockDistance(distXC, distYC, distZC);
					if(dist < distC)
						closestTotem = totem;
				}
			}
			target = 8 + (friendTotems.size() + enemyTotems.indexOf(closestTotem) + 1) * 2;
			targetType = TargetType.BLOCK_E;
			blockTarget = closestTotem;
			if(dist <= 4.25)
			{//If the enemy totem is in range:
				return;
			}//Enemy totems in range have the highest priority.
		}
		if(EntityUtils.searchEntityByName(formatSBName(4)) != null || EntityUtils.searchEntityByName(formatSBName(5)) != null)
		{//If one of the enemies can be seen:
			EntityLivingBase enemy1 = EntityUtils.searchEntityByName(formatSBName(5));
			EntityLivingBase enemy2 = EntityUtils.searchEntityByName(formatSBName(4));
			if(enemy2 == null)
			{
				entityTarget = enemy1;
				target = 6;
			}else if(enemy1 == null)
			{
				entityTarget = enemy2;
				target = 8;
			}else if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(enemy1) <= Minecraft.getMinecraft().thePlayer.getDistanceToEntity(enemy2))
			{
				entityTarget = enemy1;
				target = 6;
			}else
			{
				entityTarget = enemy2;
				target = 8;
			}
			targetType = TargetType.ENTITY_E;
			if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityTarget) <= 4.25)
				return;
		}//Enemies have a lower priority than enemy totems.
		if(!friendTotems.isEmpty())
		{//If there is a friend totem:
			int[] closestTotem = null;
			float dist = 999999999;
			for(int[] totem : friendTotems)
			{
				float distX = (float) (totem[0] - Minecraft.getMinecraft().thePlayer.posX);
				float distY = (float) (totem[1] - Minecraft.getMinecraft().thePlayer.posY);
				float distZ = (float) (totem[2] - Minecraft.getMinecraft().thePlayer.posZ);
				dist = BlockUtils.getBlockDistance(distX, distY, distZ);
				if(closestTotem == null)
					closestTotem = totem;
				else
				{
					float distXC = (float) (closestTotem[0] - Minecraft.getMinecraft().thePlayer.posX);
					float distYC = (float) (closestTotem[1] - Minecraft.getMinecraft().thePlayer.posY);
					float distZC = (float) (closestTotem[2] - Minecraft.getMinecraft().thePlayer.posZ);
					float distC = BlockUtils.getBlockDistance(distXC, distYC, distZC);
					if(dist < distC)
						closestTotem = totem;
				}
			}
			target = 8 + (friendTotems.indexOf(closestTotem) + 1) * 2;
			targetType = TargetType.BLOCK_F;
			blockTarget = closestTotem;
			return;
		}//Friend totems have a lower priority than enemies in range, but a higher priority than enemies out of range.
		if(target == -1)
		{//If there is no other target:
			entityTarget = friend;
			target = 4;
			targetType = TargetType.ENTITY_F;
			return;
		}//The friend has the lowest priority.
	}
	
	private enum TargetType
	{
		BLOCK_E,
		BLOCK_F,
		ENTITY_E,
		ENTITY_F;
	}
	
	private void faceTarget()
	{
		if(targetType == TargetType.BLOCK_E)
		{
			BlockUtils.faceBlockClient(blockTarget[0], blockTarget[1], blockTarget[2]);
		}else if(targetType == TargetType.ENTITY_E || targetType == TargetType.ENTITY_F)
		{
			EntityUtils.faceEntityClient(entityTarget);
		}
	}
	
	private void attackTarget()
	{
		if(targetType == TargetType.BLOCK_E)
		{//Attacks the totem with the sword:
			if(System.currentTimeMillis() >= lastAttack + 50)
			{
				Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = !Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed;
				lastAttack = System.currentTimeMillis();
				Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
			}
		}else if(targetType == TargetType.ENTITY_E)
		{//Attacks the player with the sword and the offensive skill.
			if(System.currentTimeMillis() >= lastAttack + 100)
			{
				if(Minecraft.getMinecraft().thePlayer.experienceLevel >= level)
					Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = !Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed;
				else
				{
					Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
					Minecraft.getMinecraft().thePlayer.swingItem();
					Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, entityTarget);
				}
				lastAttack = System.currentTimeMillis();
			}
		}
	}
	
	private void reset()
	{
		Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
		matchingBlocks.clear();
		enemyTotems.clear();
		friendTotems.clear();
		Client.Wurst.guiManager.removeFrame(frame);
		frame = null;
		friend = null;
		entityTarget = null;
		blockTarget = null;
		targetType = null;
		friendsName = null;
	}

	public void onDeath()
	{
		if(!this.getToggled())
			return;
		Minecraft.getMinecraft().thePlayer.respawnPlayer();
        GuiGameOver.mc.displayGuiScreen((GuiScreen)null);
        Client.Wurst.chat.message("You died.");
        this.setToggled(false);
	}

	public void onDisable()
	{
		Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = false;
		if(friendsName != null)
			Client.Wurst.chat.message("No longer playing ArenaBrawl with " + friendsName + ".");
		reset();
	}
}
