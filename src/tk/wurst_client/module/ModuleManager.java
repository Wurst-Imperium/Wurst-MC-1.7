package tk.wurst_client.module;

import java.util.ArrayList;

import tk.wurst_client.Client;
import tk.wurst_client.module.modules.*;

public class ModuleManager
{
	public ArrayList<Module> activeModules = new ArrayList<Module>();
	
	public Module getModuleFromClass(Class moduleClass)
	{
		for(int i = 0; i < Client.Wurst.moduleManager.activeModules.size(); i++)
		{
			if(Client.Wurst.moduleManager.activeModules.get(i).getClass().getName().equals(moduleClass.getName()))
				return Client.Wurst.moduleManager.activeModules.get(i);
		}
		throw(new IllegalArgumentException("There is no module called \"" + moduleClass.getName() + "\"."));
	}
	
	public Module getModuleByName(String name)
	{
		for(int i = 0; i < Client.Wurst.moduleManager.activeModules.size(); i++)
		{
			if(Client.Wurst.moduleManager.activeModules.get(i).getName().equals(name))
				return Client.Wurst.moduleManager.activeModules.get(i);
		}
		return null;
	}
	
	public ModuleManager()
	{
		this.activeModules.add(new AntiBlind());
		this.activeModules.add(new AntiKnockback());
		this.activeModules.add(new AntiSpam());
		this.activeModules.add(new ArenaBrawl());
		this.activeModules.add(new AutoMine());
		this.activeModules.add(new AutoRespawn());
		this.activeModules.add(new AutoSign());
		this.activeModules.add(new AutoSprint());
		this.activeModules.add(new AutoSwitch());
		this.activeModules.add(new AutoTool());
		this.activeModules.add(new AutoWalk());
		this.activeModules.add(new BaseFinder());
		this.activeModules.add(new Blink());
		this.activeModules.add(new BowAimbot());
		this.activeModules.add(new BuildRandom());
		this.activeModules.add(new BunnyHop());
		this.activeModules.add(new ChestESP());
		this.activeModules.add(new ClickGUI());
		this.activeModules.add(new Criticals());
		this.activeModules.add(new Derp());
		this.activeModules.add(new Dolphin());
		this.activeModules.add(new DotAnnoy());
		this.activeModules.add(new DotDrop());
		this.activeModules.add(new DotTaco());
		this.activeModules.add(new FastBreak());
		this.activeModules.add(new FastBow());
		this.activeModules.add(new FastEat());
		this.activeModules.add(new FastLadder());
		this.activeModules.add(new FastPlace());
		this.activeModules.add(new FightBot());
		this.activeModules.add(new Flight());
		this.activeModules.add(new Follow());
		this.activeModules.add(new ForceOP());
		this.activeModules.add(new Freecam());
		this.activeModules.add(new Fullbright());
		this.activeModules.add(new Glide());
		this.activeModules.add(new Headless());
		this.activeModules.add(new HealthTags());
		this.activeModules.add(new HighJump());
		this.activeModules.add(new Home());
		this.activeModules.add(new InstantBunker());
		this.activeModules.add(new Invisibility());
		this.activeModules.add(new ItemESP());
		this.activeModules.add(new Jesus());
		this.activeModules.add(new Jetpack());
		this.activeModules.add(new Kaboom());
		this.activeModules.add(new Killaura());
		this.activeModules.add(new KillauraLegit());
		this.activeModules.add(new Liquids());
		this.activeModules.add(new LSD());
		this.activeModules.add(new MassTPA());
		this.activeModules.add(new MileyCyrus());
		this.activeModules.add(new MobESP());
		this.activeModules.add(new MultiAura());
		this.activeModules.add(new NameProtect());
		this.activeModules.add(new NameTags());
		this.activeModules.add(new NoFall());
		this.activeModules.add(new NoHurtcam());
		this.activeModules.add(new NoWeb());
		this.activeModules.add(new Nuker());
		this.activeModules.add(new NukerLegit());
		this.activeModules.add(new Overlay());
		this.activeModules.add(new Panic());
		this.activeModules.add(new Phase());
		this.activeModules.add(new PlayerESP());
		this.activeModules.add(new Protect());
		this.activeModules.add(new Pwnage());
		this.activeModules.add(new Regen());
		this.activeModules.add(new RemoteView());
		this.activeModules.add(new Search());
		this.activeModules.add(new Sneak());
		this.activeModules.add(new Spider());
		this.activeModules.add(new Step());
		this.activeModules.add(new Throw());
		this.activeModules.add(new Timer());
		this.activeModules.add(new Tracers());
		this.activeModules.add(new TrueSight());
		this.activeModules.add(new XRay());
		this.activeModules.add(new YesCheat());
		this.activeModules.add(new AutoBuild());
	}
}