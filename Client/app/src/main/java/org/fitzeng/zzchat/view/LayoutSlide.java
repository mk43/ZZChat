package org.fitzeng.zzchat.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.aty.AtyDressUp;
import org.fitzeng.zzchat.aty.AtyProfile;
import org.fitzeng.zzchat.aty.AtySetting;
import org.fitzeng.zzchat.server.ParaseData;
import org.fitzeng.zzchat.server.ServerManager;
import org.fitzeng.zzchat.util.ImageManager;

public class LayoutSlide extends FrameLayout{

    private Context context;
    private PicAndTextBtn dressUp;
    private PicAndTextBtn profile;
    private PicAndTextBtn setting;
    private PicAndTextBtn night;
    private boolean nightMode = false;

    public LayoutSlide(@NonNull Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public LayoutSlide(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public LayoutSlide(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        this.addView(LayoutInflater.from(context).inflate(R.layout.layout_slide, null));

        dressUp = (PicAndTextBtn) findViewById(R.id.patb_dressup);
        profile = (PicAndTextBtn) findViewById(R.id.patb_profile);
        setting = (PicAndTextBtn) findViewById(R.id.patb_setting);
        night = (PicAndTextBtn) findViewById(R.id.patb_night);

        dressUp.setOnClickListener(new PicAndTextBtn.picAndTextBtnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AtyDressUp.class);
                context.startActivity(intent);
            }
        });

        profile.setOnClickListener(new PicAndTextBtn.picAndTextBtnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AtyProfile.class);
                context.startActivity(intent);
            }
        });

        setting.setOnClickListener(new PicAndTextBtn.picAndTextBtnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AtySetting.class);
                context.startActivity(intent);
            }
        });

        night.setOnClickListener(new PicAndTextBtn.picAndTextBtnClickListener() {
            @Override
            public void onClick(View view) {
                if (nightMode) {
                    findViewById(R.id.layout_slide).setBackgroundColor(0xff878787);
                    nightMode = false;
                } else {
                    findViewById(R.id.layout_slide).setBackgroundColor(0xffe9e9e9);
                    nightMode = true;
                }
            }
        });

        loadData();
    }

    private void loadData() {
        ServerManager serverManager = ServerManager.getServerManager();
        String username = serverManager.getUsername();

        // load dressup
        String[] dreStr = ParaseData.getDressUp(null, username);
        ((TextView) findViewById(R.id.tv_username)).setText(username);
        if (dreStr[0].length() > 0 && dreStr[1].length() > 0) {
            ((ImageView) this.findViewById(R.id.iv_avatar)).setImageResource(ImageManager.imagesAvatar[Integer.parseInt(dreStr[0])]);
            ServerManager.getServerManager().setIconID(Integer.parseInt(dreStr[0]));
            (this.findViewById(R.id.layout_slide_bg)).setBackgroundResource(ImageManager.imagesBackground[Integer.parseInt(dreStr[1])]);
        }

        // load profile
        String[] proStr = ParaseData.getProfile(null, username);
        if (proStr[0].length() > 0) {
            ((TextView) this.findViewById(R.id.tv_sign)).setText(proStr[0]);
        }
    }
}
