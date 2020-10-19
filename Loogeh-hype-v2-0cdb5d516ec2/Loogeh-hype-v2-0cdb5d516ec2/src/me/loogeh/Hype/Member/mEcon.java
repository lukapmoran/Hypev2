package me.loogeh.Hype.Member;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Sector.Sector;

public class mEcon extends Sector {

	private String uuid;
	
	private int balance = 0;
	
	public mEcon(Member member) {
		super("Economy");
		this.uuid = member.getUUID();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
		Main.mysql.doUpdate("UPDATE player_balances SET balance=" + balance + " WHERE uuid='" + getUUID() + "'");
	}
	
	public void alterBalance(int amount) {
		this.balance += amount;
		Main.mysql.doUpdate("UPDATE player_balances SET balance=" + this.balance + " WHERE uuid='" + getUUID() + "'");
	}

	public void load() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS player_balances (uuid VARCHAR(50), balance INTEGER(11))");
		
		ResultSet rs = Main.mysql.doQuery("SELECT balance FROM player_balances WHERE uuid='" + getName() + "'");
		try {
			if(rs.next()) balance = rs.getInt(2);
			else Main.mysql.doUpdate("INSERT INTO `player_balances`(`uuid`, `balance`) VALUES ('" + getUUID() + "', 0)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
