package org.fitzeng.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fitzeng.db.DBManager;
import org.fitzeng.main.ChatManager;
import org.fitzeng.view.MainWindow;

public class ChatSocket extends Thread{

	private String username;
	private Socket socket;
	private String message = null;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
    private Connection connection = DBManager.getDBManager().getConnection();
    private SocketMsg socketMsg;
	
	public ChatSocket(Socket s) {
		this.socket = s;
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void run() {
		try {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.equals("-1")) {	
					message += line;
				} else {
					delMessage(message);
	            	System.out.println("receive : " + message);
					line = null;
					message = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				MainWindow.getMainWindow().setShowMsg(this.username + " login out !");
				MainWindow.getMainWindow().removeOfflineUsers(this.username);
				ChatManager.getChatManager().remove(socketMsg);
				bufferedWriter.close();
				bufferedReader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void delMessage(String msg) {
		if (msg != null) {
			String action = getAction(msg);
			switch(action) {
				case "LOGIN": { dealLogin(msg); break; }
				case "REGISTER": { dealRegister(msg); break; }
				case "DRESSUP": { dealDressUp(msg); break; }
				case "GETDRESSUP": { dealGetDressUp(msg); break; }
				case "PROFILE": { dealProfile(msg); break; }
				case "GETPROFILE": { dealGetProfile(msg); break; }
				case "GETFRIENDLIST": { dealGetFriendList(msg); break; }
				case "GETGROUPLIST": { dealGetGroupList(msg); break; }
				case "GETFRIENDPROFILE": { dealGetFriendProfile(msg); break; }
				case "STATE": { dealState(msg); break; }
				case "CHATMSG": { dealChatMsg(msg); break; }
				case "USERLIST": { dealUserList(msg); break; }
				case "ADDFRIEND": { dealAddFriend(msg); break; }
				case "GROUPMEMBERLIST": { dealGroupMemberList(msg); break; }
				case "ADDGROUP": { dealAddGroup(msg); break; }
				case "GETALLGROUPLIST": { dealGetAllGroupList(msg); break;}
				default : dealError(); break;
			}
		}
	}
	
    public void sendMsg(String msg) {
    	try {
    		while (socket == null) ;
            if (bufferedWriter != null) {
            	System.out.println("send :" + msg);
                bufferedWriter.write(msg + "\n");
                bufferedWriter.flush();
                bufferedWriter.write("-1\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getMsg() {
    	return message;
    }
    
    	
    private void dealError() {
    }
    
    private void dealGetAllGroupList(String msg) {
    	String out = null;
    	String sql = "SELECT groupName FROM groups;";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				out += "[ACKGETALLGROUPLIST]:[" + resultSet.getString(1) + "] ";
			}
			sendMsg(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void dealAddGroup(String msg) {
    }

    private void dealGroupMemberList(String msg) {
    }

    private void dealAddFriend(String msg) {
    }

    private void dealUserList(String msg) {
    }

    private void dealChatMsg(String msg) {
    	String chatObj = null;
    	String content = null;
    	String avatarID = null;
    	String msgType = null;
    	String p = "\\[CHATMSG\\]:\\[(.*), (.*), (.*), (.*)\\]";
    	Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		chatObj = matcher.group(1);
    		content = matcher.group(2);
    		avatarID = matcher.group(3);
    		msgType = matcher.group(4);
    	} else {
    		return;
    	}
    	String out = null;
    	String sqlGroup = "SELECT * FROM GROUPS WHERE groupName = '" + chatObj+ "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlGroup);
			// gruop chat
			if (resultSet.next()) {
				// find all group members to send msg
				String sql = "SELECT groupMemberName FROM GROUPINFO WHERE groupName = '" + chatObj + "';";
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {					
					// if user is online , then send.
					for (SocketMsg SocketMsg : ChatManager.getChatManager().socketList) {
						if (SocketMsg.getUsername().equals(resultSet.getString(1)) && !SocketMsg.getUsername().equals(username)) {
							out = "[GETCHATMSG]:[" + username + ", " + content + ", " + avatarID + ", Text, " + chatObj + "]";
							SocketMsg.getChatSocket().sendMsg(out);
						}
					}
				}
			// private chat
			} else {
				for (SocketMsg socketManager : ChatManager.getChatManager().socketList) {
					if (socketManager.getUsername().equals(chatObj)) {
						out = "[GETCHATMSG]:[" + username + ", " + content + ", " + avatarID + ", Text,  ]";
						socketManager.getChatSocket().sendMsg(out);
					}
				}
			}
			out = "[ACKCHATMSG]:[1]";
			sendMsg(out);
		} catch (SQLException e) {
			out = "[ACKCHATMSG]:[0]";
			sendMsg(out);
			e.printStackTrace();
		}
    }

    private void dealState(String msg) {
    }

    private void dealGetFriendProfile(String msg) {
    	String friendName = null;
    	String p = "\\[GETFRIENDPROFILE\\]:\\[(.*)\\]";
    	Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		friendName = matcher.group(1);
    	} else {
			return ;
		}
    	String out = null;
    	String sql = "SELECT avatar, sign, background, state FROM userinfo WHERE username = '" + friendName + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				out = "[ACKGETFRIENDPROFILE]:[" + resultSet.getString(1) + ", " + resultSet.getString(2) + ", "
						+ "" + resultSet.getString(3) + ", " + resultSet.getString(4) + "]";
				sendMsg(out);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void dealGetGroupList(String msg) {
    	String out = null;
    	String sql = "SELECT groupName FROM groupinfo WHERE groupMemberName = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				out += "[ACKGETGROUPLIST]:[" + resultSet.getString(1) + "] ";
			}
			sendMsg(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void dealGetFriendList(String msg) {
    	String out = null;
    	String sql = "SELECT friendsName FROM friends WHERE username = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				out += "[ACKGETFRIENDLIST]:[" + resultSet.getString(1) + "] ";
			}
			sendMsg(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void dealGetProfile(String msg) {
    	String out = null;
    	String sql = "SELECT sign FROM USERINFO WHERE username = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				out = "[ACKGETPROFILE]:[" + resultSet.getString(1) + "]";
				sendMsg(out);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void dealProfile(String msg) {
    }

    private void dealGetDressUp(String msg) {
    	String out = null;
    	String sql = "SELECT avatar, background FROM USERINFO WHERE username = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				out = "[ACKGETDRESSUP]:[" + resultSet.getString(1) + ", " + resultSet.getString(2) + "]";
				sendMsg(out);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void dealDressUp(String msg) {
    	String acatarID = null;
    	String backgroundID = null;
    	String p = "\\[DRESSUP\\]:\\[(.*), (.*)\\]";
    	Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		acatarID = matcher.group(1);
    		backgroundID = matcher.group(2);
    	}
    	System.out.println(acatarID + "   " + backgroundID);
    	String sql = "UPDATE USERINFO SET avatar =  " + acatarID + ", background = " + backgroundID +" WHERE username = '" + username + "'";
    	try {
			Statement statement = connection.createStatement();
			if (statement.executeUpdate(sql) > 0) {
				sendMsg("[ACKDRESSUP]:[1]");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	sendMsg("[ACKDRESSUP]:[0]");
    }

    private void dealRegister(String msg) {
    }

    private void dealLogin(String msg) {
    	String iusername = null;
    	String iPassword = null;
    	
    	String p = "\\[LOGIN\\]:\\[(.*), (.*)\\]";
    	Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		iusername = matcher.group(1);
    		iPassword = matcher.group(2);
    	}
    	String sql = "SELECT password FROM USERS WHERE username = '" + iusername + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next() && iPassword.equals(resultSet.getString(1)) ) {
				sendMsg("[ACKLOGIN]:[1]");
				this.username = iusername;
				MainWindow.getMainWindow().setShowMsg(this.username + " login in!");
				MainWindow.getMainWindow().addOnlineUsers(this.username);
				socketMsg = new SocketMsg(this,  this.username);
				ChatManager.getChatManager().add(socketMsg);
				return ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	sendMsg("[ACKLOGIN]:[0]");
    }
    
    public String getAction(String msg) {	
        String p = "\\[(.*)\\]:";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(msg);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "error";
        }
	}
}
