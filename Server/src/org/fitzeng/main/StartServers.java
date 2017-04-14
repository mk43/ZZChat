package org.fitzeng.main;

import java.awt.EventQueue;
import java.sql.SQLException;

import org.fitzeng.db.DBManager;
import org.fitzeng.view.MainWindow;
import org.fitzeng.view.MainWindow.OnStartServersListener;

public class StartServers {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = MainWindow.getMainWindow();
					DBManager dbManager = DBManager.getDBManager();
					
					ServerListener listener = new ServerListener();
					
					frame.setOnStartServersListener(new OnStartServersListener() {						
						// Do something when servers stop
						@Override
						public void stop() {
							try {
								dbManager.getConnection().close();
								MainWindow.getMainWindow().setShowMsg("DB connection is closed");
							} catch (SQLException e) {
								MainWindow.getMainWindow().setShowMsg("DB connection close failed");
								e.printStackTrace();
							}
						}
						// Open servers
						@Override
						public void start() {
							dbManager.addDBDriver();
							dbManager.connectDB();
							try {
								dbManager.initDB();
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!listener.isAlive()) {
								listener.start();
							}
						}
					});
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
