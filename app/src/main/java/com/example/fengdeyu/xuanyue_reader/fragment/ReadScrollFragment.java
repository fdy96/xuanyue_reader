package com.example.fengdeyu.xuanyue_reader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;
import com.example.fengdeyu.xuanyue_reader.other.MyScrollView;
import com.example.fengdeyu.xuanyue_reader.view.MyTextView;

/**
 * Created by fengdeyu on 2017/1/14.
 */

public class ReadScrollFragment extends Fragment {
    private View mView;

    private ScrollView scrollView;
    private TextView tv_contents;

    private ReadActivity readActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_read_scroll,container,false);
        return mView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scrollView= (ScrollView) mView.findViewById(R.id.scroll_view);
        tv_contents= (TextView) mView.findViewById(R.id.tv_contents);
        readActivity= (ReadActivity) getActivity();


        tv_contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    readActivity.toggleReadBar();
            }
        });

        tv_contents.setText(readActivity.mContents);

    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.scrollTo(0,0);
    }

}
