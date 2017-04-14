package org.fitzeng.main;

public class SocketMsg {

	private ChatSocket chatSocket;
	private String username;
	
	public SocketMsg(ChatSocket chatSocket, String username) {
		this.chatSocket = chatSocket;
		this.username = username;
	}
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public ChatSocket getChatSocket() {
		return chatSocket;
	}
	public void setChatSocket(ChatSocket chatSocket) {
		this.chatSocket = chatSocket;
	}
	
}
