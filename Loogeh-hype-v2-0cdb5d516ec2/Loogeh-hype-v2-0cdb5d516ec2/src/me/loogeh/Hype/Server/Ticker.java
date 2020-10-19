package me.loogeh.Hype.Server;

import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Main.Main;

public class Ticker implements Runnable {

	private long lastTenth = 0L;
	private long lastFifth = 0L;
	private long lastHalf = 0L;
	private long lastSecond = 0L;
	private long lastTwoSecond = 0L;
	private long lastFourSecond = 0L;
	private long lastTenSecond = 0L;
	private long lastFifteenSecond = 0L;
	private long lastMinute = 0L;
	private long lastTwoMinute = 0L;
	private long lastFiveMinute = 0L;
	private long lastTenMinute = 0L;
	private long lastHalfHour = 0L;
	
	public Ticker() {
		this.lastTenth = System.currentTimeMillis();
		this.lastFifth = System.currentTimeMillis();
		this.lastHalf = System.currentTimeMillis();
		this.lastSecond = System.currentTimeMillis();
		this.lastTwoSecond = System.currentTimeMillis();
		this.lastFourSecond = System.currentTimeMillis();
		this.lastTenSecond = System.currentTimeMillis();
		this.lastFifteenSecond = System.currentTimeMillis();
		this.lastMinute = System.currentTimeMillis();
		this.lastTwoMinute = System.currentTimeMillis();
		this.lastFiveMinute = System.currentTimeMillis();
		this.lastTenMinute = System.currentTimeMillis();
		this.lastHalfHour = System.currentTimeMillis();
	}
	
	@Override
	public void run() {
		Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TICK));
		
		if(System.currentTimeMillis() - this.lastTenth > TickType.TENTH.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TENTH));
			this.lastTenth = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastFifth > TickType.FIFTH.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.FIFTH));
			this.lastFifth = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastHalf > TickType.HALF.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.HALF));
			this.lastHalf = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastSecond > TickType.SECOND.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.SECOND));
			this.lastSecond = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastTwoSecond > TickType.TWO_SECOND.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TWO_SECOND));
			this.lastTwoSecond = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastFourSecond > TickType.FOUR_SECOND.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.FOUR_SECOND));
			this.lastFourSecond = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastTenSecond > TickType.TEN_SECOND.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TEN_SECOND));
			this.lastTenSecond = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastFifteenSecond > TickType.FIFTEEN_SECOND.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.FIFTEEN_SECOND));
			this.lastFifteenSecond = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastMinute > TickType.MINUTE.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.MINUTE));
			this.lastMinute = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastTwoMinute > TickType.TWO_MINUTE.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TWO_MINUTE));
			this.lastTwoMinute = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastFiveMinute > TickType.FIVE_MINUTE.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.FIVE_MINUTE));
			this.lastFiveMinute = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastTenMinute > TickType.TEN_MINUTE.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TEN_MINUTE));
			this.lastTenMinute = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.lastHalfHour > TickType.HALF_HOUR.getValue()) {
			Main.plugin.getServer().getPluginManager().callEvent(new TickEvent(TickType.TEN_MINUTE));
			this.lastHalfHour = System.currentTimeMillis();
		}
	}

}
