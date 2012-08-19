package me.Todkommt.GrowableOres;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class GrowableOres extends JavaPlugin {

	public static GrowableOres instance;
	public EventManager eventListener;
	public List<OrePlant> plants = new ArrayList<OrePlant>();
	public Logger log;
	
	public void onEnable()
	{
		instance = this;
		log = getLogger();
		eventListener = new EventManager(this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		loadPlants();
		for(OrePlant plant : plants)
		{
			plant.startGrowing();
		}
		getServer().getPluginManager().registerEvents(eventListener, this);
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    log.info("Failed to submit plugin stats");
		}
		log.info(" v" + getDescription().getVersion() + " enabled.");
	}
	
	public void onDisable()
	{
		savePlants();
		log.info(" disabled.");
	}
	
	public void savePlants()
	{
		File file = new File("plugins/GrowableOres/plants.dat");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(plants);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadPlants()
	{
		File file = new File("plugins/GrowableOres/plants.dat");
		if(file.exists())
		{
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				plants = (List<OrePlant>) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
