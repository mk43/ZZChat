package org.fitzeng.zzchat.aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.fitzeng.zzchat.R;

public class AtyWelcome extends AppCompatActivity {

    private static final int DELAY = 2000;
    private static final int GO_GUIDE = 0;
    private static final int GO_HOME = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aty_welcome);

        initLoad();
    }

    private void initLoad() {
        SharedPreferences sharedPreferences = getSharedPreferences("zzchat", MODE_PRIVATE);
        boolean guide = sharedPreferences.getBoolean("guide", true);
        if (!guide) {
            handler.sendEmptyMessageDelayed(GO_HOME, DELAY);

        } else {
            handler.sendEmptyMessageDelayed(GO_GUIDE, DELAY);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.apply();
        }
    }

    private void goHome() {
        Intent intent = new Intent(this, AtyLoginOrRegister.class);
        startActivity(intent);
        finish();
    }

    private void goGuide() {
        Intent intent = new Intent(this, AtyGuide.class);
        startActivity(intent);
        finish();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_GUIDE: {
                    goGuide();
                    break;
                }
                case GO_HOME: {
                    goHome();
                    break;
                }
                default:
                    break;
            }
        }
    };
}
