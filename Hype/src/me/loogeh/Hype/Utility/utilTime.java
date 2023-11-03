package me.loogeh.Hype.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class utilTime {
	
	public static String timeStr() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}
	
	public static double convert(long time, TimeUnit unit, int decPoint) {
		if(unit == TimeUnit.BEST) {
			if(time < 60000L) unit = TimeUnit.SECONDS;
			else if(time < 3600000L) unit = TimeUnit.MINUTES;
			else if(time < 86400000L) unit = TimeUnit.HOURS;
			else unit = TimeUnit.DAYS;
		}
		return utilMath.trim(time / unit.getValue(), decPoint);
	}
	
	public static String convertString(long time, TimeUnit unit, int decPoint) {
		if(unit == TimeUnit.BEST) {
			if(time < 60000L) unit = TimeUnit.SECONDS;
			else if(time < 3600000L) unit = TimeUnit.MINUTES;
			else if(time < 86400000L) unit = TimeUnit.HOURS;
			else unit = TimeUnit.DAYS;
		}
		return utilMath.trim(time / unit.getValue(), decPoint) + " " + unit.getName(); //test
	}
	
	public static String getTextFromMillis(long millis, boolean abbreviate) {
		int x = (int) (millis / 1000);
		int seconds = x % 60;
		x /= 60;
		int minutes = x % 60;
		x /= 60;
		int hours = x % 24;
		x /= 24;
		long days = x % 7;
		x /= 7;
		long weeks = x;
		if(!abbreviate) return weeks + " weeks " + days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds";
		else return weeks + "w-" + days + "d-" + hours + "h-" + minutes + "m-" + seconds + "s";
	}
	
	public static enum TimeUnit {
		BEST(),
		DAYS(86400000, "Days"),
		HOURS(3600000, "Hours"),
		MINUTES(60000, "Minutes"),
		SECONDS(1000, "Seconds"),
		MILLISECONDS(1, "Milliseconds");
		
		private long value;
		private String name;
		
		TimeUnit(long value, String name) {
			this.value = value;
			this.name = name;
		}
		
		TimeUnit() {}
		
		public double getValue() {
			return value;
		}
		
		public String getName() {
			return this.name;
		}
		
		public double toMillis(int amount) {
			return amount * value;
		}
	}

}
