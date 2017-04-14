package org.fitzeng.zzchat.server;

import org.fitzeng.zzchat.aty.AtyChatRoom;
import org.fitzeng.zzchat.util.ChatMsg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ReceiveChatMsg {

    void delChatMsg(String msg) {

        String sendName = null;
        String content = null;
        String avatarID = null;
        String fileType = null;
        String group = null;

        ServerManager.getServerManager().setMessage(null);
        String p = "\\[GETCHATMSG\\]:\\[(.*), (.*), (.*), (.*), (.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(msg);
        if (matcher.find()) {
            sendName = matcher.group(1);
            content = matcher.group(2);
            avatarID = matcher.group(3);
            fileType = matcher.group(4);
            group = matcher.group(5);

            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setMyInfo(false);
            chatMsg.setContent(content);
            chatMsg.setChatObj(sendName);
            chatMsg.setUsername(ServerManager.getServerManager().getUsername());
            chatMsg.setGroup(group);

            chatMsg.setIconID(Integer.parseInt(avatarID));

            AtyChatRoom.chatMsgList.add(chatMsg);
            ChatMsg.chatMsgList.add(chatMsg);
        }
    }
}
