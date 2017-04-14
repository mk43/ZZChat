package org.fitzeng.zzchat.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.adapter.AdapterUserItem;
import org.fitzeng.zzchat.util.UserItemMsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LayoutChats extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    public static List<UserItemMsg> userItemMsgList;
    private Context context;
    private AdapterUserItem adapterUserItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_chats, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        context = getContext();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.chatsRecycleView);
        userItemMsgList = new ArrayList<>();
        loadData();
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(userItemMsgList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(userItemMsgList, i, i - 1);
                    }
                }
                adapterUserItem.notifyItemMoved(fromPosition, toPosition);
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                userItemMsgList.remove(position);
                adapterUserItem.notifyItemRemoved(position);
            }
        };
        adapterUserItem = new AdapterUserItem(context, userItemMsgList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterUserItem);
    }

    private void loadData() {
        for (UserItemMsg item : UserItemMsg.userItemMsgList) {
            userItemMsgList.add(item);
        }
    }


}
