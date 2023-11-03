package me.loogeh.Hype.Miscellaneous;

public class DelayedTask implements Runnable {

	private IDHashMap<DelayedTask> delayedTasks = new IDHashMap<DelayedTask>();
	
	private int id;
	private long start = System.currentTimeMillis();
	private long delay;
	private Runnable runnable;
	private boolean cancel = false;
	
	public DelayedTask(long delay, Runnable runnable) {
		this.id = delayedTasks.put(this);
		this.delay = delay;
		this.runnable = runnable;
	}
	
	public int getID() {
		return this.id;
	}
	
	public long getStart() {
		return this.start;
	}
	
	public long getDelay() {
		return this.delay;
	}
	
	public boolean getCancelled() {
		return this.cancel;
	}
	
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	public void remove() {
		delayedTasks.remove(getID());
	}
	
	public void run() {
		while(true) {
			if(cancel) {
				remove();
				break;
			} else {
				if(System.currentTimeMillis() - getStart() >= getDelay()) {
					runnable.run();
					break;
				}
			}
		}
	}

}
