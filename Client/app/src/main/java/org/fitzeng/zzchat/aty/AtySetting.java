package org.fitzeng.zzchat.aty;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.view.TitleBar;

public class AtySetting extends AppCompatActivity {

    private TitleBar titleBar;

    private ImageView guide;
    private boolean guideMode = true;
    private ImageView password;
    private boolean passwordMode = true;
    private ImageView offline;
    private boolean offlineMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aty_setting);
        
        initViews();
    }

    private void initViews() {
        titleBar = (TitleBar) findViewById(R.id.tb_setting);
        guide = (ImageView) findViewById(R.id.iv_setting_guide);
        password = (ImageView) findViewById(R.id.iv_setting_password);
        offline = (ImageView) findViewById(R.id.iv_setting_offline);

        guideMode = getSharedPreferences("zzchat", MODE_PRIVATE).getBoolean("guide", true);
        guide.setImageResource(guideMode ? R.drawable.btnselected : R.drawable.btnunselected);
        password.setImageResource(passwordMode ? R.drawable.btnselected : R.drawable.btnunselected);
        offline.setImageResource(offlineMode ? R.drawable.btnselected : R.drawable.btnunselected);

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guideMode) {
                    guide.setImageResource(R.drawable.btnunselected);
                    guideMode = false;
                } else {
                    guide.setImageResource(R.drawable.btnselected);
                    guideMode = true;
                }
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordMode) {
                    password.setImageResource(R.drawable.btnunselected);
                    passwordMode = false;
                } else {
                    password.setImageResource(R.drawable.btnselected);
                    passwordMode = true;
                }
            }
        });

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offlineMode) {
                    offline.setImageResource(R.drawable.btnunselected);
                    offlineMode = false;
                } else {
                    offline.setImageResource(R.drawable.btnselected);
                    offlineMode = true;
                }
            }
        });

        titleBar.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                SharedPreferences sharedPreferences = getSharedPreferences("zzchat", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("guide", guideMode);
                editor.apply();
                finish();
            }

            @Override
            public void rightButtonClick() {

            }
        });
    }
}
