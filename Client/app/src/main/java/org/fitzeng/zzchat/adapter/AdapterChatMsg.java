package org.fitzeng.zzchat.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.util.ChatMsg;
import org.fitzeng.zzchat.util.ImageManager;

import java.util.List;

public class AdapterChatMsg extends ArrayAdapter<ChatMsg> {

    private LayoutInflater inflater;
    private List<ChatMsg> chatMsgs;
    public AdapterChatMsg(@NonNull Context context, @LayoutRes int resource, List<ChatMsg> chatMsgs) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.chatMsgs = chatMsgs;
    }

    @Override
    public int getCount() {
        return chatMsgs.size();
    }

    @Nullable
    @Override
    public ChatMsg getItem(int position) {
        return chatMsgs.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChatMsg msg = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            assert msg != null;
            if (!msg.isMyInfo()) {
                view = inflater.inflate(R.layout.chat_other, parent, false);
            } else {
                view = inflater.inflate(R.layout.chat_me, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.content = (TextView) view.findViewById(R.id.content);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.icon.setImageResource(ImageManager.imagesAvatar[chatMsgs.get(position).getIconID()]);
        viewHolder.username.setText(msg.isMyInfo() ? chatMsgs.get(position).getUsername() : chatMsgs.get(position).getChatObj());
        viewHolder.content.setText(chatMsgs.get(position).getContent());
        return view;
    }

    private class ViewHolder {
        ImageView icon;
        TextView username;
        TextView content;
    }
}
