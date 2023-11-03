package me.loogeh.Hype.Utility;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class utilWorld {


	public static String chunkToStr(Chunk chunk) {
		if(chunk == null) {
			return "";
		} else {
			return chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();
		}

	}

	public static String chunkToStrClean(Chunk chunk) {
		if(chunk == null) {
			return "";
		} else {
			return "(" + chunk.getX() + "," + chunk.getZ() + ")";
		}
	}

	public static Chunk strToChunk(String string) {

		try {
			String[] parts = string.split(",");
			return Bukkit.getWorld(parts[0]).getChunkAt(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

		} catch (Exception e) {}
		return null;
	}
	
	public static World strToWorld(String string) {
		for(World worlds : Bukkit.getWorlds()) {
			if(worlds.getName().equals(string)) {
				return Bukkit.getWorld(string);
			}
		}
		return null;
	}

//	@SuppressWarnings("deprecation")
//	public static void blockParticleEffect(int blockID, World world, int x, int y, int z) {
//		Packet61WorldEvent packet = new Packet61WorldEvent(2001, x, y, z, blockID, false);
//
//		Location loc = new Location(world, x, y, z);
//
//		for(Player players : Bukkit.getServer().getOnlinePlayers()) {
//			((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
//		}
//
//		loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.getMaterial(blockID));
//	}

	@SuppressWarnings("deprecation")
	public static void blockParticleEffect(int blockID, Location location) {
		location.getWorld().playEffect(location, Effect.STEP_SOUND, Material.getMaterial(blockID));
	}

	
	public static String locToStr(Location loc) {
		if (loc == null) return "";
		return loc.getWorld().getName() + "," + utilMath.trim(loc.getX(), 1) + "," + utilMath.trim(loc.getY(), 1) + "," + utilMath.trim(loc.getZ(), 1);
	}
	
	public static Location strToLoc(String location) {
		if(location == null) return null;
		String[] split = location.split(",");
		World world = Bukkit.getWorld(split[0]);
		if(world == null) return null;
		double x;
		double y;
		double z;
		try {
			x = Double.parseDouble(split[1]);
			y = Double.parseDouble(split[2]);
			z = Double.parseDouble(split[3]);
		} catch(NumberFormatException e) {
			return null;
		}
		return new Location(world, utilMath.trim(x, 1), utilMath.trim(y, 1), utilMath.trim(z, 1));
	}
	
	public static void shootAngledArrow(Player player, Vector start, double angle) {
		Arrow arrow = (Arrow) player.getWorld().spawnArrow(player.getLocation(), player.getLocation().getDirection(), 1.0F, 1.0F);
		arrow.setShooter(player);
		arrow.setVelocity(start.add(new Vector(Math.sin(angle), 0.0D, Math.cos(angle))));
	}
}
