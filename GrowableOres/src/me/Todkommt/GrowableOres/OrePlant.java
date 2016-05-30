package net.spideynn.bukkit.growableores;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class OrePlant implements Serializable {

	private static final long serialVersionUID = -2231010396344601094L;
	SerializableBlock base;
	SerializableBlock[] grownBlocks;
	long timeElapsed;
    Thread thread;
	String planter;
	private float growTime;
	GrowTimer timer;
	
	public OrePlant(Block base, int growHeight, float growTime, String planter)
	{
		this.base = new SerializableBlock(base);
		this.grownBlocks = new SerializableBlock[growHeight-1];
		this.growTime = growTime;
		this.planter = planter;
		startGrowing();
	}
	
	void startGrowing()
	{
		//GrowableOres.instance.log.info("starting to grow");
		timer = new GrowTimer(growTime, this, timeElapsed);
		thread = new Thread(timer);
		thread.start();
	}
	void grow(GrowTimer timer)
	{
		//GrowableOres.instance.log.info("growing ore plant");
		timer.timeElapsed = 0;
		boolean isMaxSize = true;
		SerializableBlock currentBlock = base;

		for (SerializableBlock block : grownBlocks) {
			if (block == null)
				isMaxSize = false;
			else
				currentBlock = block;
		}

		if(isMaxSize)
		{
			timer.running = false;
			timer.timeElapsed = 0;
			return;
		}
		
		World world = GrowableOres.instance.getServer().getWorld(currentBlock.world);
		
		SerializableBlock oldOreBlock = base;
		
		for(int i1=0; i1<grownBlocks.length; i1++)
		{			
			if(grownBlocks[i1] == null)
			{
				Location newLoc = new Location(world, oldOreBlock.x, oldOreBlock.y+1, oldOreBlock.z);
				//GrowableOres.instance.log.info("block material: " + world.getBlockAt(newLoc).getType());
				if(!world.getBlockAt(newLoc).getType().equals(Material.AIR))
				{
					timer.running = false;
					int newArraySize = 0;
					for(SerializableBlock bloc : grownBlocks)
					{
						if(bloc != null)
							newArraySize++;
					}
					SerializableBlock[] newArray = new SerializableBlock[newArraySize];
					System.arraycopy(grownBlocks, 0, newArray, 0, newArraySize);
					grownBlocks = newArray;
					return;
				}
				
				grownBlocks[i1] = new SerializableBlock(newLoc, base.type, base.data);
				world.getBlockAt(newLoc).setTypeId(currentBlock.type);
				break;
			}
			else oldOreBlock = grownBlocks[i1];
		}
		
		isMaxSize = true;

		for (SerializableBlock block : grownBlocks) {
			if (block == null)
				isMaxSize = false;
		}
		
		if(isMaxSize)
		{
			timer.running = false;
		}
	}
}
