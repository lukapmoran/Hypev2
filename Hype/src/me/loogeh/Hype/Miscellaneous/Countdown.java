package me.loogeh.Hype.Miscellaneous;

import me.loogeh.Hype.Main.Main;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Countdown implements Runnable {

	private String name;
	public int seconds;
	private int start_broadcast;
	private boolean can_start = false;
	private boolean mark_end = false;

	public Countdown(String name, int seconds, int start_broadcast) {
		this.name = name;
		this.seconds = seconds;
		this.start_broadcast = start_broadcast;
		run();
	}

	public Countdown(String name, int seconds, int start_broadcast, boolean can_start) {
		this.name = name;
		this.seconds = seconds;
		this.start_broadcast = start_broadcast;
		this.can_start = can_start;
		run();
	}

	public String getName() {
		return this.name;
	}

	public boolean getCanStart() {
		return this.can_start;
	}

	public int getSeconds() {
		return this.seconds;
	}

	public int getStartBroadcast() {
		return this.start_broadcast;
	}
	
	public boolean getMarkEnd() {
		return this.mark_end;
	}

	public void setStart(int seconds) {
		this.seconds = seconds;
	}

	public void setStartBroadcast(int start_broadcast) {
		this.start_broadcast = start_broadcast;
	}

	public void setCanStart(boolean can_start) {
		this.can_start = can_start;
	}
	
	public void setMarked(boolean mark_end) {
		this.mark_end = mark_end;
	}

	public abstract void doAction();

	public void run() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(getMarkEnd() || getSeconds() < 0) this.cancel();
				else doAction();
			}
		}.runTaskTimer(Main.plugin, 20L, 20L);
	}

}
