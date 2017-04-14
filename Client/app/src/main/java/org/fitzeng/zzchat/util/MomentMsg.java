package org.fitzeng.zzchat.util;


import java.util.ArrayList;
import java.util.List;

public class MomentMsg {

    private int iconID;
    private String username;
    private String moment;
    private int good;
    public static List<MomentMsg> momentMsgList = new ArrayList<>();

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

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }
}
