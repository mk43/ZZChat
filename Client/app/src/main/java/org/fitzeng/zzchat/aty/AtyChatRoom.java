package org.fitzeng.zzchat.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.adapter.AdapterChatMsg;
import org.fitzeng.zzchat.server.ParaseData;
import org.fitzeng.zzchat.server.ServerManager;
import org.fitzeng.zzchat.util.ChatMsg;
import org.fitzeng.zzchat.view.TitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtyChatRoom extends AppCompatActivity{

    private TitleBar titleBar;
    private ListView listView;
    private EditText myMsg;
    private Button btnSend;
    public static List<ChatMsg> chatMsgList = new ArrayList<>();
    public static AdapterChatMsg adapterChatMsgList;
    private String chatObj;
    private String group;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aty_chat_room);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        AndroidBug5497Workaround.assistActivity(this);
        initViews();
    }

    private void initViews() {

        titleBar = (TitleBar) findViewById(R.id.tb_chat_room);
        listView = (ListView) findViewById(R.id.lv_chat_room);
        myMsg = (EditText) findViewById(R.id.myMsg);
        btnSend = (Button) findViewById(R.id.btnSend);
        chatObj = getIntent().getStringExtra("username");
        titleBar.setTitleText(chatObj);
        group = ParaseData.getAllGroupList(this).contains(chatObj) ? "0" : "1";
        chatMsgList.clear();
        loadChatMsg();
        adapterChatMsgList = new AdapterChatMsg(AtyChatRoom.this, R.layout.chat_other, chatMsgList);
        listView.setAdapter(adapterChatMsgList);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = myMsg.getText().toString();
                if (!content.isEmpty()) {
                    ChatMsg msg = new ChatMsg();
                    msg.setContent(content);
                    msg.setUsername(ServerManager.getServerManager().getUsername());
                    msg.setIconID(ServerManager.getServerManager().getIconID());
                    msg.setMyInfo(true);
                    msg.setChatObj(chatObj);
                    msg.setGroup(group.equals("0") ? chatObj : " ");
                    if (sendToChatObj(msg.getContent())) {
                        ChatMsg.chatMsgList.add(msg);
                        chatMsgList.add(msg);
                        myMsg.setText("");
                    } else {
                        Toast.makeText(AtyChatRoom.this, "send failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        titleBar.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                finish();
            }
            @Override
            public void rightButtonClick() { }
        });
    }

    private boolean sendToChatObj(String content) {
        String msg = "[CHATMSG]:[" + chatObj + ", " + content + ", " + ServerManager.getServerManager().getIconID() +", Text]";
        ServerManager serverManager = ServerManager.getServerManager();
        serverManager.sendMessage(this, msg);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ack = serverManager.getMessage();
        if (ack == null) {
            return false;
        }
        String p = "\\[ACKCHATMSG\\]:\\[(.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        return matcher.find() && matcher.group(1).equals("1");
    }

    private void loadChatMsg() {
        if (group == "0") {
            for (ChatMsg chatMsg : ChatMsg.chatMsgList) {
                if (chatMsg.getGroup().equals(chatObj)) {
                    chatMsgList.add(chatMsg);
                }
            }
        } else {
            for (ChatMsg chatMsg : ChatMsg.chatMsgList) {
                if (chatMsg.getChatObj().equals(chatObj) && chatMsg.getGroup().equals(" ")) {
                    chatMsgList.add(chatMsg);
                }
            }
        }
    }
}
