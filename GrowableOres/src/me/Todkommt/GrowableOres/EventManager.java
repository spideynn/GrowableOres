package net.spideynn.bukkit.growableores;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventManager implements Listener {

	public GrowableOres plugin;
	
	public EventManager(GrowableOres plugin)
	{
		this.plugin = plugin;
	}
	
	private float parseFloat(String value)
	{
		float f = Float.parseFloat(value);
		return f;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		//plugin.log.info("interact");
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		Player player = event.getPlayer();
		if(!player.hasPermission("growableores.plant"))
		{
			return;
		}
		if(!event.getBlockFace().equals(BlockFace.UP))
			return;
		if(!player.isSneaking())
			return;
		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();
		if(item == null)
			return;
		List<String> disabledOres = plugin.getConfig().getStringList("config.disabledOres");
		boolean[] disabled = new boolean[] { false, false, false, false, false, false, false };
		for(String ore : disabledOres)
		{
			if(ore.equalsIgnoreCase("coal"))
			{
				disabled[0] = true;
			}
			else if(ore.equalsIgnoreCase("iron"))
			{
				disabled[1] = true;
			}
			else if(ore.equalsIgnoreCase("diamond"))
			{
				disabled[2] = true;
			}
			else if(ore.equalsIgnoreCase("gold"))
			{
				disabled[3] = true;
			}
			else if(ore.equalsIgnoreCase("redstone"))
			{
				disabled[4] = true;
			}
			else if(ore.equalsIgnoreCase("emerald"))
			{
				disabled[5] = true;
			}
			else if(ore.equalsIgnoreCase("lapis"))
			{
				disabled[6] = true;
			}
		}
        if (!block.getType().equals(Material.SOIL)) {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "GrowableOres" + ChatColor.GRAY + "]" + ChatColor.RED + " You need to plant this on farmland.");
            //event.setCancelled(true);
            return;
        }
		//plugin.log.info("checking for item");
		//plugin.log.info("item in hand is " + item.getType().name() + " and amount is " + item.getAmount() + " (price:" + plugin.getConfig().getInt("prices.coal") + ")");
		if(item.getType().equals(Material.COAL) && item.getAmount() >= plugin.getConfig().getInt("prices.coal"))
		{
			//plugin.log.info("is coal");
			if(disabled[0])
				return;
			//plugin.log.info("isnt disabled");
			if(item.getAmount() == plugin.getConfig().getInt("prices.coal"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.coal"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.COAL_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.coal"), parseFloat(plugin.getConfig().getString("growtimes.coal")), player.getName()));
			plugin.savePlants();
		}
		else if(item.getType().equals(Material.IRON_INGOT) && item.getAmount() >= plugin.getConfig().getInt("prices.iron"))
		{
			if(disabled[1])
				return;
			if(item.getAmount() == plugin.getConfig().getInt("prices.iron"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.iron"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.IRON_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.iron"), parseFloat(plugin.getConfig().getString("growtimes.iron")), player.getName()));
			plugin.savePlants();
		}
		else if(item.getType().equals(Material.DIAMOND) && item.getAmount() >= plugin.getConfig().getInt("prices.diamond"))
		{
			if(disabled[2])
				return;
			if(item.getAmount() == plugin.getConfig().getInt("prices.diamond"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.diamond"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.DIAMOND_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.diamond"), parseFloat(plugin.getConfig().getString("growtimes.diamond")), player.getName()));
			plugin.savePlants();
		}
		else if(item.getType().equals(Material.GOLD_INGOT) && item.getAmount() >= plugin.getConfig().getInt("prices.gold"))
		{
			if(disabled[3])
				return;
			if(item.getAmount() == plugin.getConfig().getInt("prices.gold"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.gold"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.GOLD_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.gold"), parseFloat(plugin.getConfig().getString("growtimes.gold")), player.getName()));
			plugin.savePlants();
		}
		else if(item.getType().equals(Material.REDSTONE) && item.getAmount() >= plugin.getConfig().getInt("prices.redstone"))
		{
			if(disabled[4])
				return;
			if(item.getAmount() == plugin.getConfig().getInt("prices.redstone"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.redstone"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.REDSTONE_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.redstone"), parseFloat(plugin.getConfig().getString("growtimes.redstone")), player.getName()));
			plugin.savePlants();
		}
		else if(item.getType().equals(Material.EMERALD) && item.getAmount() >= plugin.getConfig().getInt("prices.emerald"))
		{
			if(disabled[5])
				return;
			if(item.getAmount() == plugin.getConfig().getInt("prices.emerald"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.emerald"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.EMERALD_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.emerald"), parseFloat(plugin.getConfig().getString("growtimes.emerald")), player.getName()));
			plugin.savePlants();
		}
		else if(item.getType().equals(Material.INK_SACK) && item.getDurability() == 4 && item.getAmount() >= plugin.getConfig().getInt("prices.lapis"))
		{
			if(disabled[6])
				return;
			if(item.getAmount() == plugin.getConfig().getInt("prices.lapis"))
				player.getInventory().remove(item);
			else
				item.setAmount(item.getAmount()-plugin.getConfig().getInt("prices.lapis"));
			Location loc = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
			if(!loc.getBlock().getType().equals(Material.AIR))
			{
				return;
			}
			loc.getBlock().setTypeIdAndData(Material.LAPIS_ORE.getId(), (byte)0, true);
			plugin.plants.add(new OrePlant(loc.getBlock(), plugin.getConfig().getInt("growheights.lapis"), parseFloat(plugin.getConfig().getString("growtimes.lapis")), player.getName()));
			plugin.savePlants();
		} else if ((item.getType().equals(Material.INK_SACK) && item.getDurability() == 4)
                || item.getType().equals(Material.EMERALD)
                || item.getType().equals(Material.REDSTONE)
                || item.getType().equals(Material.GOLD_INGOT)
                || item.getType().equals(Material.IRON_INGOT)
                || item.getType().equals(Material.COAL)) {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "GrowableOres" + ChatColor.GRAY + "]" + ChatColor.RED + " You don't have enough " + item.getType().toString());
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block b = event.getBlock();
		if(!(b.getType() == Material.COAL_ORE
				|| b.getType() == Material.IRON_ORE
				|| b.getType() == Material.DIAMOND_ORE
				|| b.getType() == Material.GOLD_ORE
				|| b.getType() == Material.REDSTONE_ORE
				|| b.getType() == Material.EMERALD_ORE
				|| b.getType() == Material.LAPIS_ORE))
		{
			return;
		}
		
		for(int i1=0; i1<plugin.plants.size(); i1++)
		{
			OrePlant plant = plugin.plants.get(i1);
			if(plant.base.x == b.getLocation().getX() && plant.base.z == b.getLocation().getZ())
			{
				if(plant.base.y == b.getLocation().getY())
				{
					if(!event.getPlayer().getName().equals(plant.planter) && plugin.getConfig().getBoolean("config.perPlayer") && !event.getPlayer().hasPermission("growableores.harvestall"))
					{
						event.getPlayer().sendMessage("You are not the owner of this plant!");
						return;
					}
					int type = plant.base.type;
					
					Player player = event.getPlayer();
					if(!player.hasPermission("growableores.harvest"))
					{
						player.sendMessage(ChatColor.YELLOW + "You don't have permission to do that.");
						event.setCancelled(true);
						return;
					}

					int harvestAmount = 1;
					
					for(SerializableBlock block : plant.grownBlocks)
					{
						if(block == null)
							continue;
						Block plantBlock = block.getBlock(plugin);
						plantBlock.breakNaturally();
						harvestAmount++;
					}
					event.getBlock().breakNaturally();
					//event.setCancelled(true);
					//plugin.log.info("type is " + type);
					//player.getInventory().addItem(new ItemStack(type, harvestAmount));
					plant.timer.running = false;
					plugin.plants.remove(plant);
					break;
				}
				else
				{
					int counter = 0;
					for(int i2=0; i2<plant.grownBlocks.length; i2++)
					{
						if(plant.grownBlocks[i2] == null)
							continue;
						
						if(plant.grownBlocks[i2].y == b.getLocation().getY())
						{
							if(!event.getPlayer().getName().equals(plant.planter) && plugin.getConfig().getBoolean("config.perPlayer") && !event.getPlayer().hasPermission("growableores.harvestall"))
							{
								event.getPlayer().sendMessage("You are not the owner of this plant!");
								return;
							}
							int type = plant.base.type;
							
							Player player = event.getPlayer();
							if(!player.hasPermission("growableores.harvest"))
							{
								player.sendMessage(ChatColor.YELLOW + "You don't have permission to do that.");
								event.setCancelled(true);
								return;
							}
							
							int harvestAmount = 0;
							
							for(int i=0; i<plant.grownBlocks.length-counter; i++)
							{
								SerializableBlock serBlock = plant.grownBlocks[i+counter];
								if(serBlock == null)
									continue;
								Block plantBlock = serBlock.getBlock(plugin);
								//plantBlock.setTypeIdAndData(Material.AIR.getId(), (byte)0, true);
                                plantBlock.breakNaturally();
								harvestAmount++;
								plant.grownBlocks[i+counter] = null;
							}
							event.getBlock().breakNaturally();
							//event.getBlock().setType(Material.AIR);
							//event.setCancelled(true);
							//player.getInventory().addItem(new ItemStack(type, harvestAmount));
							if(!plant.timer.running)
							{
								plant.timer.running = true;
								Thread thread = new Thread(plant.timer);
								thread.start();
							}
						}
						else
							counter++;
					}
				}
			}
		}
	}
}
