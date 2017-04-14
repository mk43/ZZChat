package org.fitzeng.zzchat.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.server.ServerManager;
import org.fitzeng.zzchat.util.ImageManager;
import org.fitzeng.zzchat.util.MomentMsg;

import java.util.List;

public class AdapterMomentItem extends RecyclerView.Adapter<AdapterMomentItem.BaseViewHolder>{

    private Context context;
    private List<MomentMsg> momentMsgList;

    public AdapterMomentItem(Context context, List<MomentMsg> momentMsgList) {
        this.context = context;
        this.momentMsgList = momentMsgList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_moment, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.imageView.setImageResource(ImageManager.imagesAvatar[momentMsgList.get(position).getIconID()]);
        holder.username.setText(momentMsgList.get(position).getUsername());
        holder.moment.setText(momentMsgList.get(position).getMoment());
        holder.good.setImageResource(momentMsgList.get(position).getGood());
    }

    @Override
    public int getItemCount() {
        return (momentMsgList == null ? 0 : momentMsgList.size());
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView username;
        private TextView moment;
        private ImageView good;
        private boolean isgood = false;

        BaseViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_moment_content_avatar);
            username = (TextView) itemView.findViewById(R.id.tv_moment_content_username);
            moment = (TextView) itemView.findViewById(R.id.tv_moment_content);
            good = (ImageView) itemView.findViewById(R.id.iv_good);

            good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    good.setImageResource(isgood ? R.drawable.ungood : R.drawable.good);
                    for (MomentMsg momentMsg : MomentMsg.momentMsgList) {
                        if (momentMsg.getMoment().equals(moment.getText().toString())) {
                            momentMsg.setGood(isgood ? R.drawable.ungood : R.drawable.good);
                        }
                    }
                    isgood = !isgood;
                }
            });
        }
    }
}
