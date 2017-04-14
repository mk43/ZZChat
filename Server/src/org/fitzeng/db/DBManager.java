package org.fitzeng.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.fitzeng.view.MainWindow;

public class DBManager {

	private static final DBManager dbManager = new DBManager();
	private static Connection connection = null;
	private static final String url = "jdbc:mysql://localhost/zzchats?"
			+ "useUnicode=true&useJDBCCompliantTimezoneShift=true&"
			+ "useLegacyDatetimeCode=false&serverTimezone=UTC";
	private Statement statement;
	
	public static DBManager getDBManager() {
		return dbManager;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	private DBManager() { }

	public void addDBDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			MainWindow.getMainWindow().setShowMsg("load mysql driver success");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			MainWindow.getMainWindow().setShowMsg("load mysql driver failed");			
			e.printStackTrace();
		}
	}
	
	
	public void connectDB() {
		try {
			connection = DriverManager.getConnection(url, "root", "");
			MainWindow.getMainWindow().setShowMsg("connect zzchats mysql success");
			
		} catch (SQLException e) {
			MainWindow.getMainWindow().setShowMsg("connect zzchats mysql failed");
			e.printStackTrace();
		}
	}
	
	/**
	 * init database
	 * @throws Exception 
	 */
	public void initDB() throws Exception {
		
		statement = connection.createStatement();
		
		// Create table Users
		createTabUsers();
		
		// Create table UserInfo
		createTabUserInfo();
		
		// Create table Friends
		createTabFriends();
		
		// Create table Groups
		createTabGroups();
		
		// Create table GroupInfo
		createTabGroupInfo();
		
		statement.close();
	}
	
	/**
	 * Create table GroupInfo
	 * @throws Exception 
	 */
	private void createTabGroupInfo() throws Exception {
		statement.execute("DROP TABLE IF EXISTS GroupInfo;");
		statement.execute("CREATE TABLE GroupInfo ("
				+ "groupName VARCHAR(10),"
				+ "groupMemberName VARCHAR(10));");
		
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG1\", \"Tony\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG1\", \"Elon\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG1\", \"Musk1\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG1\", \"Musk3\");");
		
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG2\", \"Elon\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG2\", \"Musk2\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG2\", \"Musk3\");");
		
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG3\", \"Tony\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG3\", \"Stark\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"TonyG3\", \"Musk\");");
		
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG1\", \"Elon\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG1\", \"Tony\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG1\", \"Musk2\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG1\", \"Musk1\");");
		
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG2\", \"Elon\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG2\", \"Stark\");");
		statement.execute("INSERT INTO GroupInfo VALUES(\"ElonG2\", \"Musk2\");");
		
	}
	
	/**
	 * Create table Groups
	 * @throws Exception
	 */
	private void createTabGroups() throws Exception {
		statement.execute("DROP TABLE IF EXISTS Groups;");
		statement.execute("CREATE TABLE Groups ("
				+ "groupName VARCHAR(10),"
				+ "creater VARCHAR(20),"
				+ "PRIMARY KEY(groupName));");
		statement.execute("INSERT INTO Groups VALUES(\"TonyG1\", \"Tony\");");
		statement.execute("INSERT INTO Groups VALUES(\"TonyG2\", \"Tony\");");
		statement.execute("INSERT INTO Groups VALUES(\"TonyG3\", \"Tony\");");
		statement.execute("INSERT INTO Groups VALUES(\"ElonG1\", \"Elon\");");
		statement.execute("INSERT INTO Groups VALUES(\"ElonG2\", \"Elon\");");
	}
	
	/**
	 * Create table Friends
	 * @throws Exception
	 */
	private void createTabFriends() throws Exception {
		statement.execute("DROP TABLE IF EXISTS Friends;");
		statement.execute("CREATE TABLE Friends ("
				+ "username VARCHAR(10),"
				+ "friendsName VARCHAR(10));");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Stark\");");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Elon\");");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Musk\");");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Musk1\");");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Musk3\");");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Musk4\");");
		statement.execute("INSERT INTO Friends VALUES(\"Tony\", \"Musk6\");");
		statement.execute("INSERT INTO Friends VALUES(\"Stark\", \"Tony\");");
		statement.execute("INSERT INTO Friends VALUES(\"Elon\", \"Tony\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk\", \"Tony\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk1\", \"Tony\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk3\", \"Tony\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk4\", \"Tony\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk6\", \"Tony\");");

		statement.execute("INSERT INTO Friends VALUES(\"Elon\", \"Musk1\");");
		statement.execute("INSERT INTO Friends VALUES(\"Elon\", \"Musk2\");");
		statement.execute("INSERT INTO Friends VALUES(\"Elon\", \"Musk5\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk1\", \"Elon\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk2\", \"Elon\");");
		statement.execute("INSERT INTO Friends VALUES(\"Musk5\", \"Elon\");");


	}
	
	/**
	 * Create table UserInfo
	 * @throws Exception
	 */
	private void createTabUserInfo() throws Exception {
		statement.execute("DROP TABLE IF EXISTS UserInfo;");
		statement.execute("CREATE TABLE UserInfo ("
				+ "username VARCHAR(10),"
				+ "avatar VARCHAR(10),"
				+ "sign VARCHAR(40),"
				+ "background VARCHAR(10),"
				+ "state VARCHAR(1),"
				+ "PRIMARY KEY(username));");
		statement.execute("INSERT INTO UserInfo VALUES(\"Tony\", \"1\", \"I am Tony\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Stark\", \"3\", \"I am Stark\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Elon\", \"2\", \"I am Elon\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk\", \"1\", \"I am Musk\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk1\", \"1\", \"I am Musk1\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk2\", \"2\", \"I am Musk2\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk3\", \"0\", \"I am Musk3\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk4\", \"4\", \"I am Musk4\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk5\", \"3\", \"I am Musk5\", \"1\", \"0\");");
		statement.execute("INSERT INTO UserInfo VALUES(\"Musk6\", \"2\", \"I am Musk6\", \"1\", \"0\");");
	}
	
	/**
	 * Create table Users
	 * @throws Exception
	 */
	private void createTabUsers() throws Exception {
		statement.execute("DROP TABLE IF EXISTS Users;");
		statement.execute("CREATE TABLE Users ("
				+ "username VARCHAR(10),"
				+ "password VARCHAR(20),"
				+ "PRIMARY KEY(username))");
		statement.execute("INSERT INTO Users VALUES(\"Tony\", \"12345tony\");");
		statement.execute("INSERT INTO Users VALUES(\"Stark\", \"12345stark\");");
		statement.execute("INSERT INTO Users VALUES(\"Elon\", \"12345elon\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk\", \"12345musk\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk1\", \"12345musk1\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk2\", \"12345musk2\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk3\", \"12345musk3\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk4\", \"12345musk4\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk5\", \"12345musk5\");");
		statement.execute("INSERT INTO Users VALUES(\"Musk6\", \"12345musk6\");");
	}
}
