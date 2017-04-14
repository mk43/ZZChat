package org.fitzeng.zzchat.aty;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.adapter.AdapterGuideViewPager;

import java.util.ArrayList;
import java.util.List;

public class AtyGuide extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private AdapterGuideViewPager adapterGuideViewPager;
    private List<View> viewList;
    private ImageView imageViews[] = new ImageView[3];
    private int[] indicatorDotIds = {R.id.iv_indicator_dot1, R.id.iv_indicator_dot2, R.id.iv_indicator_dot3};
    private Button btnToMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aty_guide);

        initViews();
    }

    @SuppressLint("InflateParams")
    private void initViews() {
        // load view
        final LayoutInflater inflater = LayoutInflater.from(this);

        viewList = new ArrayList<>();
        viewList.add(inflater.inflate(R.layout.guide_page1, null));
        viewList.add(inflater.inflate(R.layout.guide_page2, null));
        viewList.add(inflater.inflate(R.layout.guide_page3, null));

        // bind Id with imageView
        for (int i = 0; i < indicatorDotIds.length; i++) {
            imageViews[i] = (ImageView) findViewById(indicatorDotIds[i]);
        }

        adapterGuideViewPager = new AdapterGuideViewPager(this, viewList);

        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        viewPager.setAdapter(adapterGuideViewPager);
        viewPager.addOnPageChangeListener(this);

        btnToMain = (Button) (viewList.get(2)).findViewById(R.id.btn_to_main);
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AtyGuide.this, AtyLoginOrRegister.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * change the indicator when page changed
     * @param position current page ID
     */
    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < indicatorDotIds.length; i++) {
            if (i != position) {
                imageViews[i].setImageResource(R.drawable.unselected);
            } else {
                imageViews[i].setImageResource(R.drawable.selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
