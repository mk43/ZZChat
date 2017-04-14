package org.fitzeng.main;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

	private ChatManager(){};
	List<SocketMsg> socketList = new ArrayList<>();
	
	private static final ChatManager chatManager = new ChatManager();
	
	public static ChatManager getChatManager() {
		return chatManager;
	}
		
	public void add(SocketMsg cs) {
		socketList.add(cs);
	}
	
	public void remove(SocketMsg cs) {
		socketList.remove(cs);
	}
	
}
