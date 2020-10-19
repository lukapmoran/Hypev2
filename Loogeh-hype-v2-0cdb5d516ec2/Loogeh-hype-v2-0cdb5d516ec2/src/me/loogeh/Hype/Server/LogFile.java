package me.loogeh.Hype.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilTime;

public class LogFile {
	
	private String name;
	private File file;
	private PrintWriter writer;
	
	public LogFile(String name) {
		this.name = name;
		this.file = new File(Main.plugin.getDataFolder().getAbsolutePath() + "/" + name + ".txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				Main.logger.log(Level.INFO, "LogFile > Generated new log file " + name + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
				Main.logger.log(Level.WARNING, "LogFile > Failed to create new log file " + name);
			}
		}
		try {
			writer = new PrintWriter(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public PrintWriter getWriter() {
		return this.writer;
	}
	
	public void println(String line) {
		if(writer == null) return;
		writer.println("[" + utilTime.timeStr() + "] > " + line);
	}
}
