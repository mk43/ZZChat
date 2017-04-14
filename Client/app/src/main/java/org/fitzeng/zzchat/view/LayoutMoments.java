package org.fitzeng.zzchat.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.adapter.AdapterMomentItem;
import org.fitzeng.zzchat.server.ServerManager;
import org.fitzeng.zzchat.util.MomentMsg;

import java.util.ArrayList;
import java.util.List;


public class LayoutMoments extends Fragment {

    private View rootView;
    private List<MomentMsg> momentMsgList;
    private RecyclerView momentRecyclerView;
    private AdapterMomentItem adapterMomentItem;
    private TextView tvNewMoment;
    private Button btnSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_moments, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        momentRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list_moments);
        tvNewMoment = (TextView) rootView.findViewById(R.id.tv_moment_new);
        btnSend = (Button) rootView.findViewById(R.id.btn_moment_send);

        momentMsgList = new ArrayList<>();
        momentMsgList.clear();
        loadData();

        adapterMomentItem = new AdapterMomentItem(getContext(), momentMsgList);
        momentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        momentRecyclerView.setAdapter(adapterMomentItem);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentMsg momentMsg = new MomentMsg();
                momentMsg.setUsername(ServerManager.getServerManager().getUsername());
                momentMsg.setIconID(ServerManager.getServerManager().getIconID());
                momentMsg.setMoment(tvNewMoment.getText().toString());
                momentMsg.setGood(R.drawable.ungood);
                tvNewMoment.setText("");
                momentMsgList.add(momentMsg);
                MomentMsg.momentMsgList.add(momentMsg);
            }
        });
    }

    private void loadData() {
        for (MomentMsg momentMsg : MomentMsg.momentMsgList) {
            momentMsgList.add(momentMsg);
        }
    }
}
