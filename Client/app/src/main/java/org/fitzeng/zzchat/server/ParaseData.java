package org.fitzeng.zzchat.server;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParaseData {

    private static ServerManager serverManager = ServerManager.getServerManager();

    static String getAction(String msg) {
        String p = "\\[(.*)\\]:";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(msg);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "error";
        }
    }

    public static String[] getDressUp(Context context, String username) {
        String[] strings = {"", ""};
        String msg = "[GETDRESSUP]:[" + username +"]";
        serverManager.sendMessage(context, msg);
        String ack = serverManager.getMessage();
        if (ack == null) {
            Toast.makeText(context, "load dress up failed", Toast.LENGTH_SHORT).show();
            return strings;
        }
        serverManager.setMessage(null);
        String p = "\\[ACKGETDRESSUP\\]:\\[(.*), (.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        if (matcher.find()) {
            strings[0] = matcher.group(1);
            strings[1] = matcher.group(2);
        }
        return strings;
    }

    public static String[] getProfile(Context context, String username) {
        String[] strings = {""};
        String msg = "[GETPROFILE]:[" + username + "]";
        serverManager.sendMessage(context, msg);
        String ack = serverManager.getMessage();
        if (ack == null) {
            Toast.makeText(context, "load profile failed", Toast.LENGTH_SHORT).show();
            return strings;
        }
        serverManager.setMessage("");
        String p = "\\[ACKGETPROFILE\\]:\\[(.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        if (matcher.find()) {
            strings[0] = matcher.group(1);
        }
        return strings;
    }

    public static List<String> getGroupList(Context context, String username) {
        List<String> strings = new ArrayList<>();
        String msg = "[GETGROUPLIST]:[" + username + "]";
        serverManager.sendMessage(context, msg);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ack = serverManager.getMessage();
        if (ack == null) {
            Toast.makeText(context, "load group list failed", Toast.LENGTH_SHORT).show();
            return strings;
        }
        serverManager.setMessage("");
        String p = "\\[ACKGETGROUPLIST\\]:\\[(.*?)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        while (matcher.find()) {
            strings.add(matcher.group(1));
        }
        return strings;
    }

    public static List<String> getFriendList(Context context, String username) {
        List<String> strings = new ArrayList<>();
        String msg = "[GETFRIENDLIST]:[" + username + "]";
        serverManager.sendMessage(context, msg);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ack = serverManager.getMessage();
        if (ack == null) {
            Toast.makeText(context, "load friend list failed", Toast.LENGTH_SHORT).show();
            return strings;
        }
        serverManager.setMessage("");
        String p = "\\[ACKGETFRIENDLIST\\]:\\[(.*?)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        while (matcher.find()) {
            strings.add(matcher.group(1));
        }
        return strings;
    }

    public static String[] getFriendProfile(Context context, String username) {
        String[] strings = {"", "", "", ""};
        String msg = "[GETFRIENDPROFILE]:[" + username + "]";
        serverManager.sendMessage(context, msg);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ack = serverManager.getMessage();
        if (ack == null) {
            Toast.makeText(context, "load friend profile failed", Toast.LENGTH_SHORT).show();
            return strings;
        }
        serverManager.setMessage(null);
        String p = "\\[ACKGETFRIENDPROFILE\\]:\\[(.*), (.*), (.*), (.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        if (matcher.find()) {
            strings[0] = matcher.group(1);
            strings[1] = matcher.group(2);
            strings[2] = matcher.group(3);
            strings[3] = matcher.group(4);
        }
        return strings;
    }

    public static List<String> getAllGroupList(Context context) {
        List<String> strings = new ArrayList<>();
        String msg = "[GETALLGROUPLIST]:[]";
        serverManager.sendMessage(context, msg);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ack = serverManager.getMessage();
        if (ack == null) {
            Toast.makeText(context, "load all group list failed", Toast.LENGTH_SHORT).show();
            return strings;
        }
        serverManager.setMessage("");
        String p = "\\[ACKGETALLGROUPLIST\\]:\\[(.*?)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        while (matcher.find()) {
            strings.add(matcher.group(1));
        }
        return strings;
    }
}