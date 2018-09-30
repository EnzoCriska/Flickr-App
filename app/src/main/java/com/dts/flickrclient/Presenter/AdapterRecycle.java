package com.dts.flickrclient.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dts.flickrclient.R;


public class AdapterRecycle extends RecyclerView.Adapter{
    private IData iData;
    private ShowFullitf showFullitf;
    private String TAG = "Adapter RecyclerView";

    public AdapterRecycle(ShowFullitf showFullitf) {
        this.showFullitf = showFullitf;
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setiData(IData iData) {
        this.iData = iData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            showFullitf.showfull(this.getPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the Action");
//            menu.add(Menu.NONE, R.id.deleteReport, Menu.NONE,"Delete report");
//            menu.add(Menu.NONE, R.id.changereports, Menu.NONE, "Change Report");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messView = inflater.inflate(R.layout.item_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(messView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
//        Photo photo = iData.getItem(position);
//        Bitmap bitmap = photo.getBitmap();
        Bitmap bitmap = iData.getItem(position);
        if (bitmap != null){
            ImageView imageView = holder.itemView.findViewById(R.id.imageItem);
            bitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*2), (int)(bitmap.getHeight()*2), true);
            imageView.setImageBitmap(bitmap);
            Log.i(TAG, "Binded View Holder");
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View view) {
//                setPosition(holder.getPosition());
//                return false;
//            }
//        });
        }

    }

    @Override
    public int getItemCount() {
        return iData.getCount();
    }


    public interface IData {
        int getCount();

        Bitmap getItem(int pos);
    }
}
