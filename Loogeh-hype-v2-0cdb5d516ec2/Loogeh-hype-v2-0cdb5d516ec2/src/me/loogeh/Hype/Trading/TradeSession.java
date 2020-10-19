package me.loogeh.Hype.Trading;

import org.bukkit.inventory.ItemStack;


public class TradeSession {

	private String player;
	private String target;
	private long start_time;
	
	private ItemStack p_item;
	private ItemStack t_item;

	private boolean p_accept;
	private boolean t_accept;
	
	public TradeSession(String player, String target, long start_time) {
		this.player = player;
		this.target = target;
		this.start_time = start_time;
	}
	
	public String getPlayer() {
		return this.player;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public long getStartTime() {
		return this.start_time;
	}
	
	public ItemStack getPlayerItem() {
		return this.p_item;
	}
	
	public ItemStack getTargetItem() {
		return this.t_item;
	}
	
	public boolean getPlayerAccept() {
		return this.p_accept;
	}
	
	public boolean getTargetAccept() {
		return this.t_accept;
	}
	
	public void setPlayerItem(ItemStack itemstack) {
		this.p_item = itemstack;
	}
	
	public void setTargetItem(ItemStack itemstack) {
		this.t_item = itemstack;
	}
	
	public void setPlayerAccept(boolean accept) {
		this.p_accept = accept;
	}
	
	public void setTargetAccept(boolean accept) {
		this.t_accept = accept;
	}
}
