package me.Todkommt.GrowableOres;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SerializableBlock implements Serializable {

	private static final long serialVersionUID = -1474574579144672823L;
	public double x;
	public double y;
	public double z;
	public String world;
	public byte data;
	public int type;
	
	public SerializableBlock(Block block)
	{
		Location loc = block.getLocation();
		x = loc.getX();
		y = loc.getY();
		z = loc.getZ();
		world = loc.getWorld().getName();
		data = block.getData();
		//GrowableOres.instance.log.info("type id is " + block.getType().getId());
		type = block.getTypeId();
		//GrowableOres.instance.log.info("parsed type id is " + type);
	}
	
	public SerializableBlock(Location loc, int type, byte data)
	{
		x = loc.getX();
		y = loc.getY();
		z = loc.getZ();
		world = loc.getWorld().getName();
		this.data = data;
		this.type = type;
	}
	
	public Block getBlock(GrowableOres plugin)
	{
		World world = plugin.getServer().getWorld(this.world);
		Block block = world.getBlockAt(new Location(world, x, y, z));
		block.setTypeIdAndData(type, data, true);
		return block;
	}
}
