package com.feicui.TreasureMap.home.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feicui.TreasureMap.R;
import com.feicui.TreasureMap.home.TreasureRepo;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Administrator on 16-7-25.
 */
public class TreasureListFragment extends Fragment{

    private RecyclerView recyclerView;
    private TreasureListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setBackgroundResource(R.drawable.screen_bgs);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new TreasureListAdapter();
        recyclerView.setAdapter(listAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listAdapter.addItems(TreasureRepo.getInstance().getTreasure());
            }
        },50);
    }
}
