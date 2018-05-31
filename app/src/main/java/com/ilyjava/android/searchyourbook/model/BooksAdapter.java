package com.ilyjava.android.searchyourbook.model;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ilyjava.android.searchyourbook.R;
import com.ilyjava.android.searchyourbook.activity.BooksActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Никита on 25.05.2018.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private List<Item> items;
    private Context mContext;
    private LayoutInflater mInflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView thumbnail;
        public TextView titleOfBook;
        public TextView authorOfBook;


        public MyViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.thumbnail_card);
            thumbnail = view.findViewById(R.id.thumbnail);
            titleOfBook = view.findViewById(R.id.titleOfBook);
            authorOfBook = view.findViewById(R.id.authorOfBook);
        }
    }


    public BooksAdapter(Context context, List<Item> items) {
        mContext = context;
        this.items = items;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleOfBook.setText(item.getVolumeInfo().getTitle());
        holder.authorOfBook.setText(item.getVolumeInfo().getAuthors().get(0));

        try{
            Glide.with(mContext).load(item.getVolumeInfo().getImageLinks().getThumbnail())
                    .asBitmap()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumbnail);
        } catch (NullPointerException e){
           Glide.with(mContext).load(R.drawable.imagenotfound)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumbnail);

        }
    }


    public int getItemCount() {
        return items.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private BooksAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final BooksAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
