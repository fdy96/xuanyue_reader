package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.other.DownloadServer;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.ImageLoader;

import java.util.List;

/**
 * Created by fengdeyu on 2016/11/23.
 */

public class BookcaseAdapter extends RecyclerView.Adapter<BookcaseAdapter.bookcaseViewHolder> {

    private Context mContext;
    private List<BookItemBean> mList;
    private int mScreenWidth;
    private boolean isClose=true;


    public BookcaseAdapter(Context context, List<BookItemBean> list,int screenWidth) {
        this.mContext = context;
        this.mList = list;
        mScreenWidth=screenWidth;


    }

    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    private onItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(onItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }


    @Override
    public BookcaseAdapter.bookcaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_book_item_card,parent,false);


        return new bookcaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookcaseAdapter.bookcaseViewHolder holder, final int position) {
        //holder.book_icon.setImageResource(R.mipmap.book_icon);
        if(!mList.get(position).bookIconUrl.equals("")) {
            ImageLoader.getInstance().showImageByThread(holder.book_icon, mList.get(position).bookIconUrl);
        }
        holder.book_title.setText(mList.get(position).bookTitle);
        holder.book_author.setText(mList.get(position).bookAuthor);
        holder.book_content.setText(mList.get(position).bookContent);

        Log.i("notifydatechanged",GetBookCase.getInstance().mList.get(position).currentChapter+"");

        if(GetBookCase.getInstance().mList.get(position).currentChapter!=0){
            int i=(int)((GetBookCase.getInstance().mList.get(position).currentChapter/(float)GetBookCase.getInstance().mList.get(position).mChapterList.size())*100);




            holder.tv_proceed.setText("已阅读"+i+"%");
        }


        // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
        ViewGroup.LayoutParams lp = holder.ll_item_view.getLayoutParams();
        lp.width = mScreenWidth;


            holder.ll_item_view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(mOnItemClickListener!=null&&isClose) {

                        mOnItemClickListener.onItemClick(holder.itemView, position);
                        Log.i("info", "item_view_click");
                    }
                    else{
                        holder.hsv.smoothScrollTo(0,0);
                        isClose=true;
                    }
                }
            });



        holder.hsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){

                    int scrollX=holder.hsv.getScrollX();
                    int managerWidth=holder.ll_bookcase_manager.getWidth();
                    int itemWidth=holder.ll_item_view.getWidth();

                    if (isClose) {
                        if (scrollX < (managerWidth / 5))
                        {
                            isClose = true;

                            holder.hsv.smoothScrollTo(0, 0);
                        }
                        else// 否则的话显示操作区域
                        {
                            isClose = false;
                            holder.hsv.smoothScrollTo(itemWidth, 0);
                        }

                    }else{

                        if (scrollX < (managerWidth*4/5))
                        {
                            isClose = true;
                            holder.hsv.smoothScrollTo(0, 0);
                        }
                        else// 否则的话显示操作区域
                        {
                            isClose = false;
                            holder.hsv.smoothScrollTo(itemWidth, 0);
                        }
                    }
                    return true;
                }

                return false;
            }
        });

        // 这里防止删除一条item后,ListView处于操作状态,直接还原
        if (holder.hsv.getScrollX() != 0) {
            holder.hsv.scrollTo(0, 0);
        }

        holder.ll_item_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        new DownloadServer(position,0,20,mContext).download();
                    }
                }.start();

                holder.hsv.smoothScrollTo(0,0);

            }
        });

        holder.ll_item_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.add(0,mList.remove(position));
                notifyDataSetChanged();

            }
        });

        holder.ll_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class bookcaseViewHolder extends RecyclerView.ViewHolder{

        public ImageView book_icon;
        public TextView book_title;
        public TextView book_author;
        public TextView book_content;
        public TextView tv_proceed;
        public LinearLayout ll_item_view;
        public HorizontalScrollView hsv;
        public LinearLayout ll_bookcase_manager;

        public LinearLayout ll_item_download;
        public LinearLayout ll_item_top;
        public LinearLayout ll_item_delete;


        public bookcaseViewHolder(View itemView) {
            super(itemView);
            book_icon= (ImageView) itemView.findViewById(R.id.iv_book_icon);
            book_title= (TextView) itemView.findViewById(R.id.tv_book_title);
            book_author= (TextView) itemView.findViewById(R.id.tv_book_author);
            book_content= (TextView) itemView.findViewById(R.id.tv_book_content);
            tv_proceed= (TextView) itemView.findViewById(R.id.tv_proceed);
            ll_item_view= (LinearLayout) itemView.findViewById(R.id.ll_item_view);
            hsv= (HorizontalScrollView) itemView.findViewById(R.id.hsv);
            ll_bookcase_manager= (LinearLayout) itemView.findViewById(R.id.ll_bookcase_manager);

            ll_item_download= (LinearLayout) itemView.findViewById(R.id.ll_item_download);
            ll_item_top= (LinearLayout) itemView.findViewById(R.id.ll_item_top);
            ll_item_delete= (LinearLayout) itemView.findViewById(R.id.ll_item_delete);


        }
    }
}
