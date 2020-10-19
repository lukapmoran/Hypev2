package me.loogeh.Hype.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.loogeh.Hype.Main.Main;

public class MySQL {
	public Connection SQL;
	public String pass;
	public String address;
	public String user;
	
	public MySQL() {
		this.address = "jdbc:mysql://" + Main.config.getString("mysql.address");
		this.user = Main.config.getString("mysql.user");
		this.pass = Main.config.getString("mysql.pass");
	}
	
	public boolean connect() {
		if(address == null || user == null || pass == null) return false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			SQL = DriverManager.getConnection(address, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public void doUpdate(String statement){
		Statement st;
		try {
			st = SQL.createStatement();
			st.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Squads SQL > Failed to execute statement " + statement);
			return;
		}
	}

	public ResultSet doQuery(String query){
		Statement st;
		try {
			st = SQL.createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Squads SQL > Failed to execute query " + query);
			return null;
		}
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getPass() {
		return this.pass;
	}
	
	public Connection getConnection() {
		return this.SQL;
	}
	
	public List<String> getTables() {
		ResultSet rs = doQuery("SHOW TABLES");
		ArrayList<String> tables = new ArrayList<String>();
		try {
			while(rs.next()) {
				tables.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}
	
	public List<String> getColumns(String table) {
		ArrayList<String> columnList = new ArrayList<String>();
		ResultSet rs = doQuery("SELECT * FROM " + table + " LIMIT 0,0");
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 0; i < rsmd.getColumnCount(); i++) {
				columnList.add(rsmd.getColumnName(i + 1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnList;
		
	}
}