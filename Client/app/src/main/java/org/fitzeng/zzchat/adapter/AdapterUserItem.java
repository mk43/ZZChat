package org.fitzeng.zzchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.aty.AtyChatRoom;
import org.fitzeng.zzchat.util.ImageManager;
import org.fitzeng.zzchat.util.UserItemMsg;
import org.fitzeng.zzchat.view.LayoutChats;

import java.util.List;

public class AdapterUserItem extends RecyclerView.Adapter<AdapterUserItem.BaseViewHolder> {

    private Context context;
    private List<UserItemMsg> userItemMsgList;

    public AdapterUserItem(Context context, List<UserItemMsg> userItemMsgList) {
        this.context = context;
        this.userItemMsgList = userItemMsgList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.ivAvatar.setImageResource(ImageManager.imagesAvatar[userItemMsgList.get(position).getIconID()]);
        holder.tvUsername.setText(userItemMsgList.get(position).getUsername());
        holder.tvSign.setText(userItemMsgList.get(position).getSign());
        holder.ivAvatar.setTag(userItemMsgList.get(position).getIconID());
    }

    @Override
    public int getItemCount() {
        return (userItemMsgList == null ? 0 : userItemMsgList.size());
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivAvatar;
        private TextView tvUsername;
        private TextView tvSign;

        BaseViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_item_avatar);
            tvUsername = (TextView) itemView.findViewById(R.id.tv_item_username);
            tvSign = (TextView) itemView.findViewById(R.id.tv_item_sign);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AtyChatRoom.class);
                    intent.putExtra("username", tvUsername.getText().toString());
                    context.startActivity(intent);

                    UserItemMsg userItemMsg = new UserItemMsg();
                    userItemMsg.setSign(tvSign.getText().toString());
                    userItemMsg.setIconID((Integer) ivAvatar.getTag());
                    userItemMsg.setUsername(tvUsername.getText().toString());

                    for (UserItemMsg item : LayoutChats.userItemMsgList) {
                        if (item.getUsername().equals(userItemMsg.getUsername())) {
                            return;
                        }
                    }
                    LayoutChats.userItemMsgList.add(userItemMsg);
                    UserItemMsg.userItemMsgList.add(userItemMsg);
                }
            });
        }
    }
}
