package org.fitzeng.zzchat.util;


import java.util.ArrayList;
import java.util.List;

public class ChatMsg {

    private boolean myInfo;
    private int iconID;
    private String username;
    private String content;
    private String chatObj;
    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public static List<ChatMsg> chatMsgList = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public boolean isMyInfo() {
        return myInfo;
    }

    public void setMyInfo(boolean myInfo) {
        this.myInfo = myInfo;
    }

    public String getChatObj() {
        return chatObj;
    }

    public void setChatObj(String chatObj) {
        this.chatObj = chatObj;
    }
}
