package org.fitzeng.zzchat.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.fitzeng.zzchat.R;
import org.fitzeng.zzchat.util.ImageMsg;

import java.util.ArrayList;
import java.util.List;

public class AdapterAvatar extends RecyclerView.Adapter<AdapterAvatar.BaseViewHoder>{


    private List<ImageMsg> imageViews;
    private Context context;
    private LayoutInflater inflater;
    private static int selectedImageAvatar = 0;
    private List<RelativeLayout> imageContainer = new ArrayList<>();
    private Drawable bgImageDrawable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AdapterAvatar(Context context, List<ImageMsg> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
        this.inflater = LayoutInflater.from(context);
        selectedImageAvatar = 0;
        bgImageDrawable = context.getResources().getDrawable(R.drawable.bgimage, null);
    }

    @Override
    public BaseViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.choose_image, parent, false);
        return new BaseViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHoder holder, final int position) {
        holder.imageView.setImageResource(imageViews.get(position).getImageID());
        imageContainer.get(selectedImageAvatar).setBackground(bgImageDrawable);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != selectedImageAvatar) {
                    imageContainer.get(position).setBackground(bgImageDrawable);
                    imageContainer.get(selectedImageAvatar).setBackgroundColor(0);
                    selectedImageAvatar = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    class BaseViewHoder extends RecyclerView.ViewHolder {
        ImageView imageView;

        BaseViewHoder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            RelativeLayout layout =  (RelativeLayout) itemView.findViewById(R.id.imageContainer);
            imageContainer.add(layout);
        }
    }
}
