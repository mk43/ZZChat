package org.fitzeng.zzchat.aty;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.server.ServerManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtyLoginOrRegister extends AppCompatActivity implements View.OnClickListener {

    private TabHost tabHost;

    private Button btnLogin;
    private EditText etLoginUsername;
    private EditText etLoginPassword;

    private Button btnRegister;
    private EditText etRegisterUsername;
    private EditText etRegisterPassword;
    private EditText etInsurePassword;

    private ServerManager serverManager = ServerManager.getServerManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aty_login_or_register);

        initViews();
    }

    private void initViews() {
        tabHost = (TabHost) findViewById(R.id.tabHost);

        btnLogin = (Button) findViewById(R.id.btn_login);
        etLoginUsername = (EditText) findViewById(R.id.et_login_username);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);

        btnRegister = (Button) findViewById(R.id.btn_register);
        etRegisterUsername = (EditText) findViewById(R.id.et_register_username);
        etRegisterPassword = (EditText) findViewById(R.id.et_register_password);
        etInsurePassword = (EditText) findViewById(R.id.et_insure_password);

        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Login").setIndicator("Login").setContent(R.id.layout_login));
        tabHost.addTab(tabHost.newTabSpec("Register").setIndicator("Register").setContent(R.id.layout_register));

        for (int i = 0; i < 2; i++) {
            TextView tv = ((TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title));
            tv.setAllCaps(false);
            tv.setTextSize(16);
        }

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        serverManager.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                if (login(username, password)) {
                    serverManager.setUsername(username);
                    Intent intent = new Intent(this, AtyMain.class);
                    startActivity(intent);
                    finish();
                } else {
                    etLoginUsername.setText("");
                    etLoginPassword.setText("");
                }
                break;
            }
            case R.id.btn_register: {
                Intent intent = new Intent(this, AtyMain.class);
                startActivity(intent);
                finish();
                break;
            }
            default:
                break;
        }
    }

    private boolean login(String username, String password) {
        // check username and password whether legal
        if (username == null || username.length() > 10 || password.length() > 20) {
            return false;
        }
        // send msg to servers
        String msg = "[LOGIN]:[" + username + ", " + password + "]";
        serverManager.sendMessage(this, msg);
        // get msg from servers return
        String ack = serverManager.getMessage();
        // deal msg
        if (ack == null) {
            return false;
        }
        serverManager.setMessage(null);
        String p = "\\[ACKLOGIN\\]:\\[(.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        return matcher.find() && matcher.group(1).equals("1");
    }
}
