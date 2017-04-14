package org.fitzeng.zzchat.util;

import java.util.ArrayList;
import java.util.List;

public class UserItemMsg {

    private int iconID;
    private String username;
    private String sign;
    private String state;
    public static List<UserItemMsg> userItemMsgList = new ArrayList<>();

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
