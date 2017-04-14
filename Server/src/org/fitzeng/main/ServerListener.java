package org.fitzeng.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread{

	private ServerSocket serverSocket;

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(27777, 27);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("haha");
				ChatSocket chatSocket = new ChatSocket(socket);
				chatSocket.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
